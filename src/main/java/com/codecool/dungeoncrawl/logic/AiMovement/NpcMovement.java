package com.codecool.dungeoncrawl.logic.AiMovement;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Goblin;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import javafx.stage.Stage;

public class NpcMovement {
    GameMap map;

    public NpcMovement(GameMap map) {
        this.map = map;
    }

    public void moveNpc(Stage primaryStage){
        moveGhosts(primaryStage);
        moveGoblins(primaryStage);
        moveMonsters(primaryStage);
    }

    private void moveGoblins(Stage primaryStage){
        for (Goblin goblin : map.getGoblins()) {
            goblin.moveGoblin(primaryStage);
        }
    }

    private void moveGhosts(Stage primaryStage){
        for (Ghost ghost : map.getGhosts()) {
            ghost.randomMove(primaryStage);

        }
    }

    private void moveMonsters(Stage primaryStage){
        for (Monster monster: map.getMonsters()) {
            monster.chaseMove(primaryStage);

        }
    }
}
