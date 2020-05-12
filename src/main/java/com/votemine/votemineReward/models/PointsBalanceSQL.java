package com.votemine.votemineReward.models;

import com.votemine.votemineReward.storage.sql.SQLStore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class PointsBalanceSQL implements PointsBalance {

    private String uuid;
    private SQLStore storage;

    public PointsBalanceSQL(String uuid, SQLStore storage){
        this.uuid = uuid;
        this.storage = storage;
    }

    @Override
    public int get() {
        AtomicInteger points = new AtomicInteger(0);
        try {
            String getPlayerPointsQuery = "SELECT points FROM players WHERE uuid = ?";
            storage.execPreparedStatement(getPlayerPointsQuery, preparedStatement -> {
                preparedStatement.setString(1, uuid);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()){
                    points.set(result.getInt("points"));
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points.get();
    }

    @Override
    public void add(int nPoints) {
        add(nPoints, "unspecified");
    }

    public void add(int nPoints, String reason){
        if (nPoints == 0) return;
        try {
            AtomicInteger rowsModified = new AtomicInteger(0);
            String addPointQuery = "UPDATE players SET points = points + ? WHERE uuid = ?";
            storage.execPreparedStatement(addPointQuery, preparedStatement ->{
                preparedStatement.setString(2, uuid);
                preparedStatement.setInt(1, nPoints);
                rowsModified.set(preparedStatement.executeUpdate());
            });
            if (rowsModified.get() == 0){
                insertPlayer(nPoints);
            }
            storage.insert(new TransactionSQL(uuid, nPoints,reason));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertPlayer(int points){
        try {
            String insertPlayerQuery = "INSERT INTO players (uuid, points) VALUES (?, ?)";
            storage.execPreparedStatement(insertPlayerQuery, preparedStatement ->{
                preparedStatement.setString(1, uuid);
                preparedStatement.setInt(2, points);
                preparedStatement.execute();
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int points) {
        add(points*-1);
    }

    @Override
    public void remove(int points, String reason) {
        add(points*-1, reason);
    }
}
