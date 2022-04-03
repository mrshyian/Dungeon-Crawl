//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.codecool.dungeoncrawl.logic;

public enum CellType {
    BRIDGE("bridge"),
    BRIDGESTART("bridgestart"),
    DOORCLOSE("doorclose"),
    DOOROPEN("dooropen"),
    EMPTY("empty"),
    FIRE("fire"),
    FLOOR("floor"),
    OAKS("oaks"),
    PINES("pines"),
    RIVERBODY("riverbody"),
    STAIRS("stairs"),
    BEAR("bear"),
    WALL("wall"),
    SKULL("skull"),
    ARROW("<>"),
    CASTLE1("1"),
    CASTLE2("2"),
    CASTLE3("3"),
    CASTLE4("4"),
    CASTLE5("5"),
    CASTLE6("6"),
    CASTLE7("7"),
    CASTLE8("8"),
    GHOST("ghost");

    private final String tileName;

    private CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return this.tileName;
    }
}
