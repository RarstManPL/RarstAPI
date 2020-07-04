package me.rarstman.rarstapi.database;

public class DatabaseData {

    private final String host;
    private final Integer port;
    private final String user;
    private final String password;
    private final String base;

    public DatabaseData(final String host, final Integer port, final String user, final String password, final String base) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.base = base;
    }

    public String getHost() {
        return this.host;
    }

    public Integer getPort() {
        return this.port;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public String getBase() {
        return this.base;
    }

}
