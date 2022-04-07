package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Door;

public class DoorModel extends BaseModel {

    private int x;
    private int y;
    private boolean isOpen;

    public DoorModel(Door door) {
        this.x = door.getCell().getX();
        this.y = door.getCell().getY();
        this.isOpen = door.isOpen();
    }

    public DoorModel(int x, int y, boolean isOpen) {
        this.x = x;
        this.y = y;
        this.isOpen = isOpen;
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

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getId(){return this.id;}

    public void setId(int id){this.id = id;}
}
