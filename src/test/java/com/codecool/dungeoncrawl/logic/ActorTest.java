package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void moveUpdatesCells(Stage primaryStage) {
        Player player = new Player(gameMap.getCell(1, 1), "");
        player.move(1, 0, primaryStage);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getCellContent());
        assertEquals(player, gameMap.getCell(2, 1).getCellContent());
    }

    @Test
    void cannotMoveIntoWall(Stage primaryStage) {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1), "");
        player.move(1, 0, primaryStage);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap(Stage primaryStage) {
        Player player = new Player(gameMap.getCell(2, 1), "");
        player.move(1, 0, primaryStage);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor(Stage primaryStage) {
        Player player = new Player(gameMap.getCell(1, 1),"");
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.move(1, 0, primaryStage);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getCellContent());
    }
}