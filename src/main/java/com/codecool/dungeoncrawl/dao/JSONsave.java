package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class JSONsave {
    private JSONArray savedGame;

    public JSONArray getSavedGame() { return savedGame; }

    public void saveToJSON(){

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

        this.savedGame = getJSONifiedGameData(allObjects);
        writeToFile("savedGame.json");
    }

    private JSONArray getJSONifiedGameData(ArrayList<Object> allObjects){
        JSONArray gameSave = new JSONArray();

        JSONArray actorsList = new JSONArray();
        JSONArray itemsList = new JSONArray();
        JSONObject player = new JSONObject();
        JSONObject mapLink = getMapLinkJSONObject();


        for (Object object : allObjects){
            if (object instanceof Player){
                player = getPlayerJSONObject((Player) object);
            }

            if ((object instanceof Actor) && !(object instanceof Player)){
                JSONObject actor = getActorJSONObject((Actor) object);
                actorsList.add(actor);
            }

            if (object instanceof Item){
                JSONObject item = getItemJSONObject((Item) object);
                itemsList.add(item);
            }
        }

        gameSave.add(player);
        if (!actorsList.isEmpty()){
            gameSave.add(actorsList);
        }
        if (!itemsList.isEmpty()){
            gameSave.add(itemsList);
        }
        gameSave.add(mapLink);

        return gameSave;
    }

    private JSONObject getActorJSONObject(Actor instanceOfActor){
        JSONObject actor = new JSONObject();

        actor.put("X", instanceOfActor.getCell().getX());
        actor.put("Y", instanceOfActor.getCell().getY());
        actor.put("HP", instanceOfActor.getHealth());
        actor.put("Actor Type", instanceOfActor.getClass().getSimpleName());

        return actor;
    }

    private JSONObject getItemJSONObject(Item instanceOfItem){
        JSONObject item = new JSONObject();

        item.put("X", instanceOfItem.getCell().getX());
        item.put("Y", instanceOfItem.getCell().getY());
        item.put("Item Type", instanceOfItem.getClass().getSimpleName());

        return item;
    }


    private JSONObject getPlayerJSONObject(Player instanceOfPlayer){
        JSONObject player = new JSONObject();
        JSONArray backPack = new JSONArray();

        player.put("X", instanceOfPlayer.getCell().getX());
        player.put("Y", instanceOfPlayer.getCell().getY());
        player.put("HP", instanceOfPlayer.getHealth());
        player.put("Attack", instanceOfPlayer.getAttackPower());
        player.put("Shield", instanceOfPlayer.getShield());
        player.put("Player", instanceOfPlayer.getTileName());

        for (Item item : instanceOfPlayer.backpack.getBackpackContent()){
            backPack.add(item.getClass().getSimpleName());
        }

        player.put("BackPack", backPack);

        return player;
    }

    private JSONObject getMapLinkJSONObject(){
        JSONObject mapLink = new JSONObject();

        mapLink.put("Map", "some Map Link");

        return mapLink;
    }

    private void writeToFile(String fileName){
        try (FileWriter file = new FileWriter(fileName)) {

            file.write(this.getSavedGame().toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
