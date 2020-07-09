package me.rarstman.rarstapi.database.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import me.rarstman.rarstapi.database.DatabaseData;
import me.rarstman.rarstapi.database.DatabaseProvider;
import me.rarstman.rarstapi.database.exception.DatabaseInitializeException;
import org.bukkit.Material;

import java.sql.SQLException;

public class MySQL extends DatabaseProvider {

    private final DatabaseData databaseData;

    public MySQL(final DatabaseData databaseData) {
        this.databaseData = databaseData;
    }

    @Override
    public DatabaseProvider initialize() throws DatabaseInitializeException {
        if(this.databaseData.getHost() == null
                || this.databaseData.getPort() == null
                || this.databaseData.getUser() == null
                || this.databaseData.getBase() == null) {
            throw new DatabaseInitializeException("Cannot initialize database due to lacks in configuration ('" + this.databaseData.getHost() + "', '" + this.databaseData.getPort() + "', '" + this.databaseData.getUser() + "', " + this.databaseData.getBase() + "').");
        }
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + this.databaseData.getHost() + ":" + this.databaseData.getPort() + "/" + this.databaseData.getBase());
        hikariConfig.setUsername(this.databaseData.getUser());
        if(this.databaseData.getPassword() != null) {
            hikariConfig.setPassword(this.databaseData.getPassword());
        }
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("useLocalTransactionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");

        try {
            this.hikariDataSource = new HikariDataSource(hikariConfig);
        } catch (final HikariPool.PoolInitializationException exception) {
            this.logger.exception(exception, "Error while trying to initialize HikariPool ('" + this.databaseData.getHost() + "', '" + this.databaseData.getPort() + "', '" + this.databaseData.getUser() + "', " + this.databaseData.getBase() + "'");
            throw new DatabaseInitializeException("Error while trying to initialize HikariPool ('" + this.databaseData.getHost() + "', '" + this.databaseData.getPort() + "', '" + this.databaseData.getUser() + "', " + this.databaseData.getBase() + "'");
        }

        try {
            this.hikariDataSource.getConnection();
        } catch (final SQLException exception) {
            this.logger.exception(exception, "Error while trying to test first database connection ('" + this.databaseData.getHost() + "', '" + this.databaseData.getPort() + "', '" + this.databaseData.getUser() + "', " + this.databaseData.getBase() + "'");
            throw new DatabaseInitializeException("Error while trying to test first database connection ('" + this.databaseData.getHost() + "', '" + this.databaseData.getPort() + "', '" + this.databaseData.getUser() + "', " + this.databaseData.getBase() + "'");
        };
        this.tables
                .stream()
                .forEach(table -> this.query(table, true));
        this.logger.info("Database ('" + this.databaseData.getHost() + "', '" + this.databaseData.getPort() + "', '" + this.databaseData.getUser() + "', " + this.databaseData.getBase() + "') has been initialized.");
        return this;
    }

}
