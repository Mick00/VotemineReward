package com.votemine.votemineReward.exceptions;

public class PlayerNotFoundException extends Exception {
    private String playername;
    public PlayerNotFoundException(String name){
        super("Player "+name+" does not exists");
        this.playername = name;
    }

    public String getPlayername() {
        return playername;
    }
}
