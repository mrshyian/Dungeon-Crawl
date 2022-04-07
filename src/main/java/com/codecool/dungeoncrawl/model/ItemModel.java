package com.codecool.dungeoncrawl.model;


import com.codecool.dungeoncrawl.logic.items.Item;

public class ItemModel extends BaseModel{
    private int x;
    private int y;
    private String itemName;
    private int hp;
    private int power;
    private int shield;
    private int id;

    public ItemModel(Item item){
        this.itemName = item.getTileName();
        this.x = item.getX();
        this.y = item.getY();
        this.hp = item.getHealthUpper();
        this.power = item.getAttackUpper();
        this.shield = item.getShieldUpper();
        this.id = item.getId();
    }

    public ItemModel(int id, int x, int y, String name, int hp, int power, int shield){
        this.id = id;
        this.x = x;
        this.y = y;
        this.itemName = name;
        this.hp = hp;
        this.power = power;
        this.shield = shield;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
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
