package com.votemine.votemineReward.storage.sql;

import com.votemine.votemineReward.models.PointsBalance;
import com.votemine.votemineReward.models.PointsBalanceSQL;
import com.votemine.votemineReward.models.Transaction;
import com.votemine.votemineReward.storage.Store;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SQLStore implements Store {

    public abstract Connection open() throws SQLException;
    public abstract void close();

    private final String logPointsTransactionQuery = "INSERT INTO transactions (uuid, points, reason, time) VALUES (?, ?, ?, ?)";

    protected void initDatabase() throws SQLException {
        Connection connection = open();
        connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS players ("
                        + " uuid varchar(30) PRIMARY KEY,"
                        + " points text NOT NULL"
                        + ")");
        connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS transactions ("
                        + " uuid varchar(30),"
                        + " points int NOT NULL,"
                        + " reason varchar(100),"
                        + " time TIMESTAMP NOT NULL,"
                        + "FOREIGN KEY(uuid) REFERENCES players(uuid))"
        );
        connection.close();
    }

    public void execPreparedStatement(String query, QueryExecution execution) throws SQLException {
        new SQLQuery(this).exec(query,execution);
    }

    @Override
    public PointsBalance getBalance(String uuid){
        return new PointsBalanceSQL(uuid, this);
    }

    @Override
    public List<Transaction> getTransactions(String uuid){
        return new ArrayList<>();
    }

    @Override
    public void insert(Transaction transaction){
        try {
            execPreparedStatement(logPointsTransactionQuery, preparedStatement -> {
                preparedStatement.setString(1, transaction.of());
                preparedStatement.setInt(2, transaction.getPoints());
                preparedStatement.setString(3, transaction.reason());
                preparedStatement.setTimestamp(4, transaction.at());
                preparedStatement.execute();
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
