package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class JSONsave {


    public static void main( String[] args )
    {

        //--------------------TEST---------------------------
        ArrayList<Object> allObjects = new ArrayList<>();
        Cell cell = new Cell(3,4);

        Item crown = new Crown(cell, 100);
        Item cheese = new Cheese(cell, 100);
        Item sword = new Sword(cell, 100);
        Item sword1 = new Sword1(cell, 100);
        Item key = new Key(cell, 100);
        Item helmet = new Helmet(cell, 100);



        Player władek = new Player(cell, "Władek");
        władek.backpack.addItemToBackPackDirecly(crown);
        władek.backpack.addItemToBackPackDirecly(cheese);
        władek.backpack.addItemToBackPackDirecly(cheese);
        władek.backpack.addItemToBackPackDirecly(sword);
        władek.backpack.addItemToBackPackDirecly(sword1);
        władek.backpack.addItemToBackPackDirecly(key);
        władek.backpack.addItemToBackPackDirecly(helmet);


        allObjects.add(new Ghost(cell));
        allObjects.add(new Ghost(cell));
        allObjects.add(new Goblin(cell));
        allObjects.add(new Goblin(cell));
        allObjects.add(new Monster(cell));
        allObjects.add(new Skeleton(cell));
        allObjects.add(new Skeleton(cell));
        allObjects.add(new Skeleton(cell));
        allObjects.add(new Cheese(cell, 100));
        allObjects.add(new Cheese(cell, 100));
        allObjects.add(new Helmet(cell, 100));
        allObjects.add(new Sword(cell, 100));
        allObjects.add(new Sword1(cell, 100));
        allObjects.add(new Crown(cell, 100));
        allObjects.add(new Key(cell, 100));
        allObjects.add(władek);
        //-------------------------KONIEC-TESTU-----------------------



        JSONObject actors = new JSONObject();

        JSONObject gosts = new JSONObject();
        JSONObject goblins = new JSONObject();
        JSONObject monsters = new JSONObject();
        JSONObject skeletons = new JSONObject();


        JSONObject items = new JSONObject();

        JSONObject cheeses = new JSONObject();
        JSONObject crowns = new JSONObject();
        JSONObject swords = new JSONObject();
        JSONObject swords1 = new JSONObject();
        JSONObject helmets = new JSONObject();
        JSONObject keys = new JSONObject();

        JSONObject savedGame = new JSONObject();

        int countOfGhosts = 0;
        int countOfGoblins = 0;
        int countOfMonster = 0;
        int countOfSkeletons = 0;
        int cheeseCountOnMap = 0;
        int keyCountOnMap = 0;
        for (Object object : allObjects){
            if (object instanceof Ghost){
                countOfGhosts ++;
                JSONObject enemy = new JSONObject();
                enemy.put("X", ((Ghost) object).getX());
                enemy.put("Y", ((Ghost) object).getY());
                enemy.put("HP", ((Ghost) object).getHealth());
                gosts.put("Ghost" + countOfGhosts, enemy);
            } else if (object instanceof Goblin){
                countOfGoblins ++;
                JSONObject enemy = new JSONObject();
                enemy.put("X", ((Goblin) object).getX());
                enemy.put("Y", ((Goblin) object).getY());
                enemy.put("HP", ((Goblin) object).getHealth());
                goblins.put("Goblin" + countOfGoblins, enemy);
            } else if (object instanceof Monster) {
                countOfMonster ++;
                JSONObject enemy = new JSONObject();
                enemy.put("X", ((Monster) object).getX());
                enemy.put("Y", ((Monster) object).getY());
                enemy.put("HP", ((Monster) object).getHealth());
                monsters.put("Monster" + countOfMonster, enemy);
            } else if (object instanceof Skeleton) {
                countOfSkeletons ++;
                JSONObject enemy = new JSONObject();
                enemy.put("X", ((Skeleton) object).getX());
                enemy.put("Y", ((Skeleton) object).getY());
                enemy.put("HP", ((Skeleton) object).getHealth());
                skeletons.put("Skeleton" + countOfSkeletons, enemy);
            } else if (object instanceof Player) {
                JSONObject enemy = new JSONObject();
                enemy.put("X", ((Player) object).getX());
                enemy.put("Y", ((Player) object).getY());
                enemy.put("HP", ((Player) object).getHealth());
                enemy.put("Attack", ((Player) object).getAttackPower());
                enemy.put("Shield", ((Player) object).getShield());
                JSONObject backPack = new JSONObject();
                int cheeseCountForBackpack = 0;
                for (Item item : ((Player) object).backpack.getBackpackContent()){
                    if (item instanceof Cheese){
                        cheeseCountForBackpack++;
                    } else if (item instanceof Sword){
                        backPack.put("Sword", true);
                    } else if (item instanceof Sword1){
                        backPack.put("Sword1", true);
                    } else if (item instanceof Helmet){
                        backPack.put("Helmet", true);
                    } else if (item instanceof Crown){
                        backPack.put("Crown", true);
                    } else if (item instanceof Key){
                        backPack.put("Key", true);
                    }
                }
                backPack.put("Cheese", cheeseCountForBackpack);
                enemy.put("backPack", backPack);
                actors.put("Player", enemy);
            } else if (object instanceof Cheese){
                cheeseCountOnMap ++;
                JSONObject item = new JSONObject();
                item.put("X", ((Cheese) object).getCell().getX());
                item.put("Y", ((Cheese) object).getCell().getY());
                cheeses.put("Cheese" + cheeseCountOnMap, item);
                items.put("Cheeses", cheeses);
            } else if (object instanceof Key){
                keyCountOnMap ++;
                JSONObject item = new JSONObject();
                item.put("X", ((Key) object).getCell().getX());
                item.put("Y", ((Key) object).getCell().getY());
                keys.put("Key" + keyCountOnMap, item);
                items.put("Keys", keys);
            } else if (object instanceof Helmet){
                JSONObject item = new JSONObject();
                item.put("X", ((Helmet) object).getCell().getX());
                item.put("Y", ((Helmet) object).getCell().getY());
                helmets.put("Helmet", item);
                items.put("Helmets", helmets);
            } else if (object instanceof Crown){
                JSONObject item = new JSONObject();
                item.put("X", ((Crown) object).getCell().getX());
                item.put("Y", ((Crown) object).getCell().getY());
                crowns.put("Crown", item);
                items.put("Crowns", crowns);
            } else if (object instanceof Sword){
                JSONObject item = new JSONObject();
                item.put("X", ((Sword) object).getCell().getX());
                item.put("Y", ((Sword) object).getCell().getY());
                swords.put("Sword", item);
                items.put("Swords", swords);
            } else if (object instanceof Sword1){
                JSONObject item = new JSONObject();
                item.put("X", ((Sword1) object).getCell().getX());
                item.put("Y", ((Sword1) object).getCell().getY());
                swords1.put("Sword1", item);
                items.put("Swords1", swords1);
            }
        }

        if (!gosts.isEmpty()){
            actors.put("gosts", gosts);
        }
        if (!goblins.isEmpty()){
            actors.put("goblins", goblins);
        }
        if (!monsters.isEmpty()){
            actors.put("monsters", monsters);
        }
        if (!skeletons.isEmpty()){
            actors.put("skeletons", skeletons);
        }

        savedGame.put("Actors", actors);
        savedGame.put("Items", items);
        savedGame.put("Map", "mapLink");



        //Write JSON file
        try (FileWriter file = new FileWriter("saved_game.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(savedGame.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
