package com.votemine.votemineReward;

import com.votemine.votemineReward.storage.Storage;
import com.votemine.votemineReward.storage.StorageException;
import com.votemine.votemineReward.storage.StorageFactory;
import com.votemine.votemineReward.storage.StorageType;

public class Main {
    public static void main(String[] args){
        try {
            Storage storage = StorageFactory.getStorage(StorageType.SQLite);
            storage.getBalance("dsadsad").add(10);
            storage.getBalance("dsadsad").remove(6);
            System.out.println(storage.getBalance("dsadsad").get());
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }
}
