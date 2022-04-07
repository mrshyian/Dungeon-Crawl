package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public class Door implements Drawable {

    private Cell cell;
    private boolean isOpen;


    public Door(Cell cell) {
        this.cell = cell;
        this.isOpen = false;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean isOpen() { return isOpen; }

    public void setOpen(boolean open) { isOpen = open;  }

    @Override
    public String getTileName() {
        if (isOpen){
            return "dooropen";
        } else {
            return "doorclose";
        }
//        return "door";
    }

    public void openDoor(){
        isOpen = true;
    }
}
