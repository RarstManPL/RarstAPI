package me.rarstman.rarstapi.database;

import com.zaxxer.hikari.HikariDataSource;
import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.database.exception.DatabaseInitializeException;
import me.rarstman.rarstapi.logger.Logger;
import me.rarstman.rarstapi.task.impl.AsynchronouslyTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class DatabaseProvider extends AsynchronouslyTask {

    protected final Logger logger;
    protected HikariDataSource hikariDataSource;

    private Connection connection;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);

    public DatabaseProvider() {
        this.logger = RarstAPIPlugin.getAPI().getAPILogger();
        this.register();
    }

    public void disableDatabase() {
        this.disableTask();
        this.hikariDataSource.close();
        this.logger.info("Disabled database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "'.");
    }

    public Connection getConnection() {
        if(this.hikariDataSource == null) {
            this.logger.error("Cannot create connection for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "' due to hikariDataSource's lack - database isn't initialized.");
            return null;
        }

        try {
            if (this.connection.isClosed() || this.connection == null) {
                this.connection = hikariDataSource.getConnection();
            }
        } catch (final SQLException exception) {
            this.logger.exception(exception, "Error while trying to create connection for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "'.");
            return null;
        }
        return this.connection;
    }

    public void query(String string, boolean now) {
        if(this.getConnection() == null) {
            this.logger.error("Cannot execute query '" + string + "' for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "' due to connection's null.");
            return;
        }
        if(!now) {
            this.queue.add(string);
            return;
        }

        try {
            final Statement statement = this.getConnection().createStatement();
            statement.executeUpdate(string);
            statement.close();
        } catch (final SQLException exception) {
            this.logger.exception(exception, "Error while trying to execute query '" + string + "' for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "'.");
        }
    }

    public ResultSet select(String string) {
        if(this.getConnection() == null) {
            this.logger.error("Cannot execute select '" + string + "' for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "' due to connection's null.");
            return null;
        }
        ResultSet resultSet = null;

        try {
            final Statement statement = this.getConnection().createStatement();
            resultSet = statement.executeQuery(string);
        } catch (final SQLException exception) {
            this.logger.exception(exception, "Error while trying to execute select '" + string + "' for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "'.");
        }
        return resultSet;
    }

    public DatabaseProvider createTable(final String table) {
        this.query(table, true);
        return this;
    }

    public DatabaseProvider createTables(final String... tables) {
        Arrays.stream(tables)
                .forEach(this::createTable);
        return this;
    }

    public HikariDataSource getHikariDataSource() {
        return this.hikariDataSource;
    }

    public BlockingQueue<String> getQueue() {
        return this.queue;
    }

    @Override
    public void onExecute() {
        try {
            final String stringQueue = this.queue.take();

            if(this.getConnection() == null) {
                this.logger.error("Cannot execute query '" + stringQueue + "' for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "' due to connection's null.");
                return;
            }

            try {
                final Statement statement = this.getConnection().createStatement();
                statement.executeUpdate(stringQueue);
                statement.close();
            } catch (final SQLException exception) {
                this.logger.exception(exception, "Error while trying to execute query '" + stringQueue + "' for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "'.");
            }
        } catch (final InterruptedException ignored) {}
    }

    @Override
    public void onDisable() {
        try {
            final Connection connection = this.hikariDataSource.getConnection();

            if(connection == null || connection.isClosed()) {
                this.logger.error("Cannot disable database queue for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "' due to connection's null.");
                return;
            }
            final Statement statement = connection.createStatement();

            this.queue.forEach(stringQueue -> {
                try {
                    statement.addBatch(stringQueue);
                } catch (final SQLException exception) {
                    this.logger.exception(exception, "Error while trying to add batch '" + stringQueue + "' for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "'.");
                }
            });
            statement.executeBatch();
            statement.close();
            connection.setAutoCommit(true);
            connection.close();
        } catch (final SQLException exception) {
            this.logger.exception(exception, "Error while trying to execute batch for database with jdbc url '" + this.hikariDataSource.getJdbcUrl() + "'.");
        }
    }

    public abstract DatabaseProvider initialize() throws DatabaseInitializeException;

}
