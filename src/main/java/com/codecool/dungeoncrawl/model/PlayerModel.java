package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private String playerName;
    private String playerView;
    private int hp;
    private int x;
    private int y;
    private int shield;
    private int power;


    public PlayerModel(String playerName, int x, int y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.playerView = player.getTileName();
        this.x = player.getX();
        this.y = player.getY();
        this.hp = player.getHealth();
        this.power = player.getAttackPower();
        this.shield = player.getShield();
    }

    public PlayerModel(String playerName, String playerView, int x, int y, int hp, int power, int shield) {
        this.playerName = playerName;
        this.playerView = playerView;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.power = power;
        this.shield = shield;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getPlayerView(){
        return this.playerView;
    }

    public void setPlayerView(String playerView){
        this.playerView = playerView;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }
}
