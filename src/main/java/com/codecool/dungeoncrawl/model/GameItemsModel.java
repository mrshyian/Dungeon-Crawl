package com.codecool.dungeoncrawl.model;

public class GameItemsModel extends BaseModel{

    private int gameId;
    private int itemId;

    public GameItemsModel(int gameId, int itemId) {
        this.gameId = gameId;
        this.itemId = itemId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
