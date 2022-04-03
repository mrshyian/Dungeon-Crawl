package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Helmet extends Item{

    public Helmet(Cell cell, int price) {
        super(cell, price);
        this.setShieldUpper(1);
        this.setDescription("Rule the road, but first wear the crown!");
    }

    public String getTileName(){ return "helmet"; }
}
