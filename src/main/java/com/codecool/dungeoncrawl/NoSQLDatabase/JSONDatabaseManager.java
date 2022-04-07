package com.codecool.dungeoncrawl.NoSQLDatabase;

import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.ArrayList;

public class JSONDatabaseManager {
    private static ArrayList<Object> allObjects = new ArrayList<>();

    public void saveGame(){
        fillAllObjectsFromGameMap();
        JSONsave.saveToJSON(allObjects);
    }

    public static ArrayList<Object> getSave(){
        allObjects = JSONextract.getAllObjects();
        return allObjects;
    }

    private void fillAllObjectsFromGameMap(){
        allObjects.add(GameMap.getPlayer());
        allObjects.addAll(GameMap.getGoblins());
        allObjects.addAll(GameMap.getGhosts());
        allObjects.addAll(GameMap.getMonsters());
        allObjects.addAll(GameMap.getItems());
    }
}
