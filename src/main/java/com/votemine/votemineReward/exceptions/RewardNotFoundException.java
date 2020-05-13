package com.votemine.votemineReward.exceptions;

public class RewardNotFoundException extends Exception {
    private String rewardName;

    public RewardNotFoundException(String rewardName){
        super(rewardName+" was not found.");
        this.rewardName = rewardName;
    }

    public String getRewardName() {
        return rewardName;
    }
}
