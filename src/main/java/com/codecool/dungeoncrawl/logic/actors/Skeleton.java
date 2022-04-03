//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(1);
        this.setAttackPower(1);
        this.setShield(0);
    }

    public String getTileName() {
        return "skeleton";
    }
}
