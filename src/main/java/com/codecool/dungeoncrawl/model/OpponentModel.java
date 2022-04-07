package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Ghost;

public class OpponentModel extends BaseModel{
    private String opponentName;
    private int x;
    private int y;
    private int hp;
    private int power;
    private int shield;
    private int id;

    public OpponentModel(Actor opponent){
        this.opponentName = opponent.getTileName();
        this.x = opponent.getX();
        this.y = opponent.getY();
        this.hp = opponent.getHealth();
        this.power = opponent.getAttackPower();
        this.shield = opponent.getShield();
    }

    public OpponentModel(String opponentName, int id, int x, int y, int hp, int power, int shield){
        this.id = id;
        this.opponentName = opponentName;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.power = power;
        this.shield = shield;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String playerName) {
        this.opponentName = playerName;
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

    public void setId(int id){this.id = id;}

    public int getId(){return this.id;}
}


