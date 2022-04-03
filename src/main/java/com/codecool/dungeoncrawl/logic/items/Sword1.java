package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Sword1 extends Item{

    public Sword1(Cell cell, int price) {
        super(cell, price);
        this.setAttackUpper(1);
        this.setDescription("Never give a sword to a man who can't dance! So... Your bad.");
    }

    public String getTileName(){ return "sword1"; }
}
