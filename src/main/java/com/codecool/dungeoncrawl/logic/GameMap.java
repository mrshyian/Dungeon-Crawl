//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Goblin;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private static Player player;
    private ArrayList<Goblin> goblins = new ArrayList<>();
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private ArrayList<Monster> monsters = new ArrayList<>();

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                this.cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return this.cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public static boolean nextMap() {
        Cell cell = player.getCell();
        if (cell.getType() == CellType.STAIRS) {
            return true;
        } else return false;
    }


    public void setGoblinInitial(Goblin goblin){
        goblins.add(goblin);
    }

    public void setGhostInitial(Ghost ghost){
        ghosts.add(ghost);
    }

    public void setMonsterInitial(Monster monster){
        monsters.add(monster);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public ArrayList<Goblin> getGoblins() {
        return goblins;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void removeNPC(Drawable npc) {
        if (npc instanceof Goblin){
            goblins.remove(npc);
        }

        if (npc instanceof Ghost){
            ghosts.remove(npc);
        }

        if (npc instanceof Monster){
            monsters.remove(npc);
        }
    }
}
