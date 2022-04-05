package com.codecool.dungeoncrawl.model;

public class GameDoorsModel extends BaseModel {

    private int gameId;
    private int doorId;

    public GameDoorsModel(int gameId, int doorId) {
        this.gameId = gameId;
        this.doorId = doorId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getDoorId() {
        return doorId;
    }

    public void setDoorId(int doorId) {
        this.doorId = doorId;
    }
}
