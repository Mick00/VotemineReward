package com.votemine.votemineReward.models;

import com.votemine.votemineReward.storage.sql.SQLStore;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CachedUUIDSQL implements CachedPlayer {

    private UUID uuid = null;
    private String playername = null;
    private SQLStore store;
    private boolean exists = false;

    public CachedUUIDSQL(String playername, UUID uuid, SQLStore store){
        this.uuid = uuid;
        this.playername = playername;
        this.store = store;
    }

    public CachedUUIDSQL(String playername, SQLStore store){
        this.playername = playername;
        this.store = store;
        fetchUUID();
    }

    public void fetchUUID() {
        String statement = "SELECT * FROM uuids WHERE player_name = ?";
        try {
            store.execPreparedStatement(statement, preparedStatement->{
                preparedStatement.setString(1, this.playername);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()){
                    this.uuid = UUID.fromString(result.getString("uuid"));
                    this.exists = true;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CachedUUIDSQL(UUID uuid, SQLStore store){
        this.uuid = uuid;
        this.store = store;
        fetchPlayername();
    }

    public void fetchPlayername() {
        String statement = "SELECT * FROM uuids WHERE uuid = ?";
        try {
            store.execPreparedStatement(statement, preparedStatement->{
                preparedStatement.setString(1, this.uuid.toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    this.playername = resultSet.getString("player_name");
                    this.exists = true;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPlayername() {
        return playername;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public void updatePlayername(String newPlayername) {
        if (exists()){
            if (!this.playername.equals(newPlayername)){
                this.playername = newPlayername;
                String statement = "UPDATE uuids SET player_name LIKE ? WHERE uuid = ?";
                try {
                    store.execPreparedStatement(statement, preparedStatement->{
                        preparedStatement.setString(1, newPlayername);
                        preparedStatement.setString(2, this.uuid.toString());
                        preparedStatement.executeUpdate();
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            this.playername = newPlayername;
            save();
        }
    }

    @Override
    public boolean exists(){
        return exists;
    }

    @Override
    public void save(){
        try {
            String statement = "INSERT INTO uuids (uuid, player_name) VALUES (?, ?)";
            store.execPreparedStatement(statement, preparedStatement -> {
                preparedStatement.setString(1, this.getUUID().toString());
                preparedStatement.setString(2, this.getPlayername());
                preparedStatement.execute();
            });
            this.exists = true;
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

}
