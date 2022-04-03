package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Crown extends Item{

    public Crown(Cell cell, int price) {
        super(cell, price);
        this.setDescription("What happens to the hole when the crown is gone?");
    }

    public String getTileName(){ return "crown"; }
}
