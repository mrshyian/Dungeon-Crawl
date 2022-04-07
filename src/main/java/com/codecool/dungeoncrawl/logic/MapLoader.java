//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.NoSQLDatabase.JSONDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import org.json.simple.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {
    public static int flag = 0;
    // private static Player player = new Player(new Cell(new GameMap(5,5,CellType.EMPTY),1,1,CellType.FLOOR));
    private static Player player;

    public MapLoader() {
    }

    public static GameMap loadMap(String playerName) {
        String[] maps = {"/mapTest.txt", "/map1.txt", "/map2.txt"};
        if (flag==3) {
            flag = 0;
        }
        InputStream is = MapLoader.class.getResourceAsStream(maps[flag]);
        flag++;

        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();// /2
        int height = scanner.nextInt();// /2
        scanner.nextLine();
        GameMap map = new GameMap(width, height, CellType.EMPTY);

        for (int y = 0; y < height; ++y) {
            String line = scanner.nextLine();

            for (int x = 0; x < width; ++x) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '!':
                        case '"':
                        case '$':
                        case '%':
                        case '&':
                        case '\'':
                        case '(':
                        case ')':
                        case '*':
                        case '+':
                        case ',':
                        case '-':
                        case '/':
                        case '0':
                            cell.setType(CellType.FLOOR);
                            new Crown(cell);
                            break;
                        case '1':
                            cell.setType(CellType.CASTLE1);
                            break;
                        case '2':
                            cell.setType(CellType.CASTLE2);
                            break;
                        case '3':
                            cell.setType(CellType.CASTLE3);
                            break;
                        case '4':
                            cell.setType(CellType.CASTLE4);
                            break;
                        case '5':
                            cell.setType(CellType.CASTLE5);
                            break;
                        case '6':
                            cell.setType(CellType.CASTLE6);
                            break;
                        case '7':
                            cell.setType(CellType.CASTLE7);
                            break;
                        case '8':
                            cell.setType(CellType.CASTLE8);
                            break;
                        case '9':
                        case ':':
                        case ';':
                        case '<':
                        case '=':
                        case '>':
                        case '?':
                        case 'E':
                        case 'F':
                        case 'G':
                        case 'H':
                        case 'I':
                        case 'J':
                        case 'K':
                        case 'L':
                        case 'M':
                        case 'N':
                        case 'R':
                        case 'T':
                        case 'U':
                        case 'V':
                        case 'W':
                        case 'X':
                        case 'Y':
                        case 'Z':
                        case '[':
                        case '\\':
                        case ']':
                        case '^':
                        case '_':
                        case '`':
                        case 'a':
                        case 'c':
                        case 'e':
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.setGhostInitial(new Ghost(cell));
                            break;
                        case 'i':
                        case 'j':
                        case 'm':
                            cell.setType(CellType.FLOOR);
                            map.setMonsterInitial(new Monster(cell));
                            break;
                        case 'n':
                        case 'o':
                        case 'p':
                        case 'u':
                        case 'v':
                        case 'w':
                        case 'x':
                        case 'y':
                            cell.setType(CellType.FLOOR);
                            map.setGoblinInitial(new Goblin(cell));
                            break;
                        default:
                            char var10002 = line.charAt(x);
                            throw new RuntimeException("Unrecognized character: '" + var10002 + "'");
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            if (flag == 1) {
                                player = new Player(cell, playerName);
                            } else {
                                cell.setCellContent(player);
                                player.setCell(cell);
                            }
                            map.setPlayer(player);
                            break;
                        case 'A':
                            cell.setType(CellType.ARROW);
                            break;
                        case 'B':
                            cell.setType(CellType.BRIDGE);
                            break;
                        case 'C':
                            cell.setType(CellType.FLOOR);
                            new Cheese(cell);
                            break;
                        case 'D':
                            cell.setType(CellType.DOORCLOSE);
                            break;
                        case 'O':
                            cell.setType(CellType.OAKS);
                            break;
                        case 'P':
                            cell.setType(CellType.PINES);
                            break;
                        case 'Q':
                            cell.setType(CellType.BRIDGESTART);
                            break;
                        case 'S':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            new Sword1(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.DOOROPEN);
                            break;
                        case 'f':
                            cell.setType(CellType.FIRE);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'q':
                            cell.setType(CellType.FLOOR);
                            new Helmet(cell);
                            break;
                        case 'r':
                            cell.setType(CellType.RIVERBODY);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 't':
                            cell.setType(CellType.STAIRS);
                            break;
                        case 'z':
                            cell.setType(CellType.SKULL);
                            break;
                        case 'l':
                            cell.setType(CellType.BEAR);
                            break;
                    }
                }
            }
        }

//        if (flag > 3){
//            loadJSONSaveOnMap(map);
//        }
        loadJSONSaveOnMap(map);
        return map;

        //--------zaladowanie Actors z bazy-------------------
//        ArrayList<Actor> actorsFromJSON = new ArrayList<>(); //przykładowo
//        for (Actor actor : actorsFromJSON){
//            Cell cellForActor = map.getCell(actor.getX(), actor.getY());
//            cellForActor.setType(CellType.FLOOR);
//            cellForActor.setCellContent(actor);
//            actor.setCell(cellForActor);
//            if (actor instanceof Ghost){
//                map.setGhostInitial((Ghost) actor);
//            } else if (actor instanceof Monster){
//                map.setMonsterInitial((Monster) actor);
//            } else if (actor instanceof Goblin){
//                map.setGoblinInitial(new Goblin(cellForActor));
//            } else if (actor instanceof Player){
//                //--------zaladowanie Playera z bazy-------------------
//                ArrayList<Item> backpackFromJSON = new ArrayList<>(); //przykładowo
//                for (Item item : backpackFromJSON){
//                    ((Player) actor).backpack.addItemToBackPackDirecly(item);
//                }
//            }
//        }

    }


    private static void loadJSONSaveOnMap(GameMap map){
        ArrayList<Object> allObjects = JSONDatabaseManager.getSave();
        for (Object object : allObjects){
            if (object instanceof Actor){
                Cell cellForActor = map.getCell(((Actor) object).getCell().getX(), ((Actor) object).getCell().getY());
                cellForActor.setType(CellType.FLOOR);
                cellForActor.setCellContent((Actor) object);
                ((Actor) object).setCell(cellForActor);

                if (object instanceof Player){
                    GameMap.setPlayer((Player) object);
                }

                if (object instanceof Ghost){
                    map.setGhostInitial((Ghost) object);
                } else if (object instanceof Monster){
                    map.setMonsterInitial((Monster) object);
                } else if (object instanceof Goblin){
                    map.setGoblinInitial(new Goblin(cellForActor));
                }
            }

            if (object instanceof Item){
                Cell cellForActor = map.getCell(((Item) object).getCell().getX(), ((Item) object).getCell().getY());
                cellForActor.setType(CellType.FLOOR);
                cellForActor.setCellContent((Item) object);
                ((Item) object).setCell(cellForActor);
            }

        }
    }
}
