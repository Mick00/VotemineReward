package com.votemine.votemineReward.models;

import java.util.UUID;

public interface CachedUUID extends Model {
    String getPlayername();
    UUID getUUID();
    void updatePlayername(String newPlayername);
}
