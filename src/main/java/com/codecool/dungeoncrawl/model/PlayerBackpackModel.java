package com.codecool.dungeoncrawl.model;

public class PlayerBackpackModel extends BaseModel {

    private String playerName;
    private int itemId;

    public PlayerBackpackModel(String playerName, int itemId) {
        this.playerName = playerName;
        this.itemId = itemId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
