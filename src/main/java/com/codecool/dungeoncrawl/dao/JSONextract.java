package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

public class JSONextract {
    private static ArrayList<Object> allObjects = new ArrayList<>();

    public void printTest(){
        for (Object object: allObjects) {
            System.out.println(object.toString());
        }
    }

    public static ArrayList<Object> getAllObjects() { return allObjects; }

    public void extractObjectsFromJSON(String fileName){
        JSONArray jsonFileContent = getJSONFileContent(fileName);
        jsonFileContent.forEach(record -> {
            try {
                parseJSONtoObject(record);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }

    private JSONArray getJSONFileContent(String fileName){
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonFileContent = new JSONArray();

        try (FileReader reader = new FileReader(fileName)) {
            jsonFileContent = (JSONArray) jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonFileContent;
    }

    private static void parseJSONtoObject(Object object) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (object instanceof JSONObject){
            Set keys = ((JSONObject) object).keySet();
            if (keys.contains("Player")){
                Player player = createPlayer((JSONObject) object);
                addToAllObjectsList(player);
            }

            if (keys.contains("Map")){
                //ToDo
            }
        }

        if (object instanceof JSONArray){
            for (Object drawableInstance : (JSONArray) object) {
                Set keys = ((JSONObject) drawableInstance).keySet();
                if (keys.contains("Actor Type")){
                    Actor actor = createActor((JSONObject) drawableInstance);
                    addToAllObjectsList(actor);
                }

                if (keys.contains("Item Type")){
                    Item item = createItem((JSONObject) drawableInstance);
                    addToAllObjectsList(item);
                }
            }
        }

    }

    private static Player createPlayer(JSONObject object){
        String playerTileName = (String) object.get("Player");

        int playerPositionX = (int) (long) object.get("X");
        int playerPositionY = (int) (long) object.get("Y");
        int playerHP = (int) (long) object.get("HP");
        int playerAttack = (int) (long) object.get("Attack");
        int playerShield = (int) (long) object.get("Shield");


        GameMap playerGameMap = new GameMap(20, 20, CellType.FLOOR); // ToDo: ????
        Cell playerCell = new Cell(playerGameMap, playerPositionX, playerPositionY, CellType.FLOOR);
        Player player = new Player(playerCell, playerTileName);

        player.setHealth(playerHP);
        player.setAttackPower(playerAttack);
        player.setShield(playerShield);

        return player;
    }

    private static void addToAllObjectsList(Object object){
        getAllObjects().add(object);
    }

    private static Item createItem(JSONObject object) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String itemType = (String) object.get("Item Type");
        int itemPositionX = (int) (long) object.get("X");
        int itemPositionY = (int) (long) object.get("Y");

        Class<?> itemClass = Class.forName(itemType);
        Constructor<?> itemClassConstructor = itemClass.getConstructor(String.class);

        GameMap itemGameMap = new GameMap(20, 20, CellType.FLOOR); // ToDo: ????
        Cell itemCell = new Cell(itemGameMap, itemPositionX, itemPositionY, CellType.FLOOR);

        Object item = itemClassConstructor.newInstance(itemCell);

        return (Item) item;
    }

    private static Actor createActor(JSONObject object) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String actorType = (String) object.get("Actor Type");
        int actorPositionX = (int) (long) object.get("X");
        int actorPositionY = (int) (long) object.get("Y");
        int actorHP = (int) (long) object.get("HP");

        Class<?> actorClass = Class.forName(actorType);
        Constructor<?> actorClassConstructor = actorClass.getConstructor(String.class);

        GameMap actorGameMap = new GameMap(20, 20, CellType.FLOOR); // ToDo: ????
        Cell actorCell = new Cell(actorGameMap, actorPositionX, actorPositionY, CellType.FLOOR);

        Object actor = actorClassConstructor.newInstance(actorCell);
        ((Actor) actor).setCellIntoEmptyInstance(actorCell);
        ((Actor) actor).setHealth(actorHP);

        return (Actor) actor;
    }


}
