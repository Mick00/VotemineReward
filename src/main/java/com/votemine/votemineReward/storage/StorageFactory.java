package com.votemine.votemineReward.storage;

import com.votemine.votemineReward.config.StorageConfig;
import com.votemine.votemineReward.storage.sql.SQLiteStore;

public class StorageFactory {

    public static Store getStorage(StorageConfig config) throws StorageException {
        //TODO: implement storage system for MySQL
        switch (config.getType()){
            case SQLite:
                return new SQLiteStore();
            default:
                throw new StorageException();
        }
    }

    public static Store getStorage() throws StorageException {
        return StorageFactory.getStorage(new StorageConfig());
    }

}
