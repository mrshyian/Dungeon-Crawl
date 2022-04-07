package com.codecool.dungeoncrawl.NoSQLDatabase;

import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class JSONsave {
    private static JSONArray savedGame;

    public static void saveToJSON(ArrayList<Object> allObjects){
        savedGame = getJSONifiedGameData(allObjects);
        writeToFile("savedGame.json");
    }

    private static JSONArray getJSONifiedGameData(ArrayList<Object> allObjects){
        JSONArray gameSave = new JSONArray();

        JSONArray actorsList = new JSONArray();
        JSONArray itemsList = new JSONArray();
        JSONArray doorsList = new JSONArray();
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

            if (object instanceof Door){
                JSONObject door = getDoorJSONObject((Door) object);
                doorsList.add(door);
            }
        }

        gameSave.add(player);
        if (!actorsList.isEmpty()){
            gameSave.add(actorsList);
        }
        if (!itemsList.isEmpty()){
            gameSave.add(itemsList);
        }
        if (!doorsList.isEmpty()){
            gameSave.add(doorsList);
        }
        gameSave.add(mapLink);

        return gameSave;
    }

    private static JSONObject getActorJSONObject(Actor instanceOfActor){
        JSONObject actor = new JSONObject();

        actor.put("Actor Type", instanceOfActor.getClass().getName());
        actor.put("X", instanceOfActor.getCell().getX());
        actor.put("Y", instanceOfActor.getCell().getY());
        actor.put("HP", instanceOfActor.getHealth());

        return actor;
    }

    private static JSONObject getItemJSONObject(Item instanceOfItem){
        JSONObject item = new JSONObject();

        item.put("X", instanceOfItem.getCell().getX());
        item.put("Y", instanceOfItem.getCell().getY());
        item.put("Item Type", instanceOfItem.getClass().getName());

        return item;
    }


    private static JSONObject getDoorJSONObject(Door instanceOfDoor){
        JSONObject door = new JSONObject();

        door.put("X", instanceOfDoor.getCell().getX());
        door.put("Y", instanceOfDoor.getCell().getY());
        door.put("isOpen", instanceOfDoor.isOpen());
        door.put("Door Type", instanceOfDoor.getClass().getName());

        return door;
    }


    private static JSONObject getPlayerJSONObject(Player instanceOfPlayer){
        JSONObject player = new JSONObject();
        JSONArray backPack = new JSONArray();

        player.put("X", instanceOfPlayer.getCell().getX());
        player.put("Y", instanceOfPlayer.getCell().getY());
        player.put("HP", instanceOfPlayer.getHealth());
        player.put("Player", instanceOfPlayer.getTileName());
        player.put("Name", instanceOfPlayer.getName());

        for (Item item : instanceOfPlayer.backpack.getBackpackContent()){
            backPack.add(item.getClass().getName());
        }

        player.put("BackPack", backPack);

        return player;
    }

    private static JSONObject getMapLinkJSONObject(){
        JSONObject mapLink = new JSONObject();

        mapLink.put("Map", MapLoader.flag);

        return mapLink;
    }

    private static void writeToFile(String fileName){
        try (FileWriter file = new FileWriter(fileName,false)) {
            file.write(savedGame.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}