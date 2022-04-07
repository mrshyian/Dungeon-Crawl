package com.codecool.dungeoncrawl.NoSQLDatabase;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Door;
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
    private static Player currentPlayer;
    private static int currentFlagMap;

    public static ArrayList<Object> getAllObjects() {
        extractObjectsFromJSON("savedGame.json");
        return allObjects;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static int getCurrentFlagMap() {
        return currentFlagMap;
    }

    public static void extractObjectsFromJSON(String fileName){
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

    private static JSONArray getJSONFileContent(String fileName){
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
                currentPlayer = player;
            }

            if (keys.contains("Map")){
                Integer flagMap = ((int)(long) ((JSONObject) object).get("Map"));
                addToAllObjectsList(flagMap);
                currentFlagMap = flagMap;
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

                if (keys.contains("Door Type")){
                    Door door = createDoor((JSONObject) drawableInstance);
                    addToAllObjectsList(door);
                }
            }
        }

    }

    private static Player createPlayer(JSONObject object) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String playerTileName = (String) object.get("Player");

        int playerPositionX = (int) (long) object.get("X");
        int playerPositionY = (int) (long) object.get("Y");
        int playerHP = (int) (long) object.get("HP");
        String playerName = (String) object.get("Name");

        GameMap playerGameMap = new GameMap(20, 20, CellType.FLOOR);
        Cell playerCell = new Cell(playerGameMap, playerPositionX, playerPositionY, CellType.FLOOR);
        Player player = new Player(playerCell, playerTileName);

        player.setHealth(playerHP);
        player.setName(playerName);

        BackPack playerBackPack = createPlayerBackPack(player, object);
        player.backpack = playerBackPack;

        return player;
    }

    private static BackPack createPlayerBackPack(Player owner, JSONObject backPackContent) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BackPack playerBackPack = new BackPack(owner);
        for (Object itemType : (JSONArray) backPackContent.get("BackPack")){

            GameMap itemGameMap = new GameMap(20, 20, CellType.FLOOR);
            Cell itemCell = new Cell(itemGameMap, 0, 0, CellType.FLOOR);

            Item item = (Item) createInstance((String) itemType);
            item.setCell(itemCell);

            playerBackPack.addItemToBackPackDirecly(item);
        }
        return playerBackPack;
    }

    private static void addToAllObjectsList(Object object){
        allObjects.add(object);
    }

    private static Item createItem(JSONObject object) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String itemType = (String) object.get("Item Type");
        int itemPositionX = (int) (long) object.get("X");
        int itemPositionY = (int) (long) object.get("Y");

        GameMap itemGameMap = new GameMap(20, 20, CellType.FLOOR);
        Cell itemCell = new Cell(itemGameMap, itemPositionX, itemPositionY, CellType.FLOOR);

        Item item = (Item) createInstance(itemType);
        item.setCell(itemCell);

        return item;
    }

    private static Door createDoor(JSONObject object) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String doorType = (String) object.get("Door Type");
        int doorPositionX = (int) (long) object.get("X");
        int doorPositionY = (int) (long) object.get("Y");
        boolean isOpen = (boolean) object.get("isOpen");

        GameMap doorGameMap = new GameMap(20, 20, CellType.FLOOR);
        Cell doorCell = new Cell(doorGameMap, doorPositionX, doorPositionY, CellType.FLOOR);

        Door door = (Door) createInstance(doorType);
        door.setCell(doorCell);
        door.setOpen(isOpen);

        return door;
    }

    private static Actor createActor(JSONObject object) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String actorType = (String) object.get("Actor Type");
        int actorPositionX = (int) (long) object.get("X");
        int actorPositionY = (int) (long) object.get("Y");
        int actorHP = (int) (long) object.get("HP");

        GameMap actorGameMap = new GameMap(20, 20, CellType.FLOOR);
        Cell actorCell = new Cell(actorGameMap, actorPositionX, actorPositionY, CellType.FLOOR);

        Actor actor = (Actor) createInstance(actorType);
        actor.setCell(actorCell);
        actor.setHealth(actorHP);

        return actor;
    }

    private static Drawable createInstance(String instanceClassName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        GameMap drawableGameMap = new GameMap(20, 20, CellType.FLOOR);
        Cell drawableCell = new Cell(drawableGameMap, 1, 1, CellType.FLOOR);

        Class<?> drawableClass = Class.forName(instanceClassName);
        Constructor<?> drawableClassConstructor = drawableClass.getConstructor(drawableCell.getClass());
        Object drawableInstance = drawableClassConstructor.newInstance(drawableCell);

        return (Drawable) drawableInstance;
    }


}
