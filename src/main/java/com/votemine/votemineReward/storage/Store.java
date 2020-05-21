package com.votemine.votemineReward.storage;

import com.votemine.votemineReward.models.CachedUUID;
import com.votemine.votemineReward.models.PointsBalance;
import com.votemine.votemineReward.models.Transaction;

import java.util.List;
import java.util.UUID;

public interface Store {

    PointsBalance getBalance(String uuid);
    List<Transaction> getTransactions(String uuid);
    CachedUUID getCachedUUID(String playername);
    CachedUUID getCachedUUID(UUID uuid);
    void insert(Transaction transaction);
}
