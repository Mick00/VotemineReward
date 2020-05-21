package com.votemine.votemineReward.storage.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteStore extends SQLStore {

    public SQLiteStore(){
        try {
            Connection connection = open();
            DatabaseMetaData meta = connection.getMetaData();
            initDatabase();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection open() throws SQLException {
        String url = "jdbc:sqlite:database.sqlite";
        return DriverManager.getConnection(url);
    }

}
