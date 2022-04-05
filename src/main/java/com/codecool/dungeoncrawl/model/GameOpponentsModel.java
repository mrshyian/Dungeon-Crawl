package com.codecool.dungeoncrawl.model;

public class GameOpponentsModel extends BaseModel {

    private int gameId;
    private int opponentId;

    public GameOpponentsModel(int gameId, int opponentId) {
        this.gameId = gameId;
        this.opponentId = opponentId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(int opponentId) {
        this.opponentId = opponentId;
    }
}
