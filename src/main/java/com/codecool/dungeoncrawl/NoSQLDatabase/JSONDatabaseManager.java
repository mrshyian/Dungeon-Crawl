package com.codecool.dungeoncrawl.NoSQLDatabase;

import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.ArrayList;

public class JSONDatabaseManager {
    private static ArrayList<Object> allObjects = new ArrayList<>();

    public static void saveGame(){
        fillAllObjectsFromGameMap();
        JSONsave.saveToJSON(allObjects);
        System.out.println("Game is saved now");
    }

    public static ArrayList<Object> getSave(){
        allObjects = JSONextract.getAllObjects();
        return allObjects;
    }

    private static void fillAllObjectsFromGameMap(){
        allObjects = new ArrayList<>();
        allObjects.add(GameMap.getPlayer());
        allObjects.addAll(GameMap.getGoblins());
        allObjects.addAll(GameMap.getGhosts());
        allObjects.addAll(GameMap.getMonsters());
        allObjects.addAll(GameMap.getItems());
        allObjects.addAll(GameMap.getSkeletons());
        allObjects.addAll(GameMap.getDoors());
    }
}
