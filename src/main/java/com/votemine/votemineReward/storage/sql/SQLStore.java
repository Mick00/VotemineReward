package com.votemine.votemineReward.storage.sql;

import com.votemine.votemineReward.models.*;
import com.votemine.votemineReward.storage.Store;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class SQLStore implements Store {

    public abstract Connection open() throws SQLException;

    private final String logPointsTransactionQuery = "INSERT INTO transactions (uuid, points, reason, time) VALUES (?, ?, ?, ?)";

    protected void initDatabase() throws SQLException {
        Connection connection = open();
        connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS players ("
                        + " uuid varchar(36),"
                        + " points text NOT NULL,"
                        + " PRIMARY KEY (uuid),"
                        + " FOREIGN KEY(uuid) REFERENCES uuids(uuid))");
        connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS transactions ("
                        + " uuid varchar(36),"
                        + " points int NOT NULL,"
                        + " reason varchar(100),"
                        + " time TIMESTAMP NOT NULL,"
                        + "FOREIGN KEY(uuid) REFERENCES players(uuid))"
        );
        connection.createStatement().execute(
                "CREATE TABLE IF NOT EXISTS uuids (" +
                        "uuid varchar(36) UNIQUE NOT NULL," +
                        "player_name varchar(16) NOT NULL" +
                        ")"
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
    public CachedPlayer getCachedUUID(String playername){
        return new CachedUUIDSQL(playername, this);
    }

    @Override
    public CachedPlayer getCachedUUID(UUID uuid){
        return new CachedUUIDSQL(uuid, this);
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
