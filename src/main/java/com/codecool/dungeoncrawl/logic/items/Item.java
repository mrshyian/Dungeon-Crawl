package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;
    private int price = 10;
    private int healthUpper = 0;
    private int attackUpper = 0;
    private int shieldUpper = 0;
    private String description = "Item";
    private int id;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setCellContent(this);
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) { this.cell = cell; }

    public int getX() {
        return this.cell.getX();
    }

    public int getY() { return this.cell.getY(); }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getHealthUpper() { return healthUpper; }

    public void setHealthUpper(int healthUpper) {  this.healthUpper = healthUpper; }

    public int getAttackUpper() { return attackUpper; }

    public void setAttackUpper(int attackUpper) { this.attackUpper = attackUpper; }

    public int getShieldUpper() { return shieldUpper; }

    public void setShieldUpper(int shieldUpper) { this.shieldUpper = shieldUpper;}

    public int getId(){return this.id;}

    public void setId(int id){this.id = id;}
}
