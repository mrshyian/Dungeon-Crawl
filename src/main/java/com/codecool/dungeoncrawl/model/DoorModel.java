package com.codecool.dungeoncrawl.model;

public class DoorModel extends BaseModel {

    private int x;
    private int y;
    public boolean isOpen;

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
}
