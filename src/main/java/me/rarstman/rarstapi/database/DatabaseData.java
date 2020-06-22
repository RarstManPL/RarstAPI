package me.rarstman.rarstapi.database;

public class DatabaseData {

    private final String host;
    private final Integer port;
    private final String user;
    private final String password;
    private final String base;

    private final String file;

    public DatabaseData(final String host, final Integer port, final String user, final String password, final String base, final String file) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.base = base;
        this.file = file;
    }

    public DatabaseData(final String host, final int port, final String user, final String password, final String base) {
        this(host, port, user, password, base, null);
    }

    public DatabaseData(final String file) {
        this(null, null, null, null, null, file);
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

    public String getFile() {
        return this.file;
    }

}
