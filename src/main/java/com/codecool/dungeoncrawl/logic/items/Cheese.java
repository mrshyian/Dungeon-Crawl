package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Cheese extends Item{

    public Cheese(Cell cell, int price) {
        super(cell, price);
        this.setHealthUpper(5);
        this.setDescription("What happens to the hole when the cheese is gone?");
    }

    public String getTileName(){ return "cheese"; }
}
