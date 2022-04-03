package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item{

    public Key(Cell cell, int price) {
        super(cell, price);
        this.setDescription("Hm... Maybe I should keep it for a moment...");
    }

    public String getTileName(){ return "key"; }
}
