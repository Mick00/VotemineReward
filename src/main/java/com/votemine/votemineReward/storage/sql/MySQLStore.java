package com.votemine.votemineReward.storage.sql;

import com.votemine.votemineReward.config.StorageConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLStore extends SQLStore {

    private int port;
    private String host, user, password, database;

    public MySQLStore(String host, int port, String database, String user, String password){
        this.port = port;
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            initDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection open() throws SQLException {
        String url = "jdbc:mysql://"+host+":"+port+"/"+database;
        return DriverManager.getConnection(url , user, password);
    }

}
