package com.votemine.votemineReward.storage;

import com.votemine.votemineReward.storage.sql.SQLiteStorage;

public class StorageFactory {

    public static Storage getStorage(StorageType type) throws StorageException {
        switch (type){
            case SQLite:
                return new SQLiteStorage();
            default:
                throw new StorageException();
        }
    }

}
