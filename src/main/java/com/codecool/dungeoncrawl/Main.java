package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.NoSQLDatabase.JSONDatabaseManager;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.AiMovement.NpcMovement;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.PlayMusic;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Cheese;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    GameMap map;
    NpcMovement ai;
    Canvas canvas;
    GraphicsContext context;
    Label infoLabel;
    GridPane inventoryBar;
    GridPane ui;
    String playerName;
    GameDatabaseManager dbManager;

    public Main(String playerName) {
        this.playerName = playerName;
        map = MapLoader.loadMap(playerName);
        ai = new NpcMovement(map);
        canvas = new Canvas(
                map.getWidth()/2 * Tiles.TILE_WIDTH,
                map.getHeight()/2 * Tiles.TILE_WIDTH);
        context = canvas.getGraphicsContext2D();
        infoLabel = new Label();
        inventoryBar = new GridPane();
        ui = new GridPane();
    }


    @Override
    public void start(Stage primaryStage) {
        setupDbManager();

        System.out.println(map.getWidth());

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        borderPane.setBottom(inventoryBar);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);

        inventoryBar.setPadding(new Insets(0));

        refresh(primaryStage);
        scene.setOnKeyPressed(keyEvent -> onKeyPressed(keyEvent, primaryStage));
        scene.setOnKeyReleased(keyEvent -> onKeyReleased(keyEvent, primaryStage));

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.setMinHeight(530);
        primaryStage.setMinWidth(800);
        primaryStage.centerOnScreen(); // wyśrodkowanie sceny na ekranie
        primaryStage.show();

    }

    private void onKeyReleased(KeyEvent keyEvent, Stage primaryStage) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }

    private void onKeyPressed(KeyEvent keyEvent, Stage primaryStage) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1, primaryStage);
                refresh(primaryStage);
                break;
            case DOWN:
                map.getPlayer().move(0, 1, primaryStage);
                refresh(primaryStage);
                break;
            case LEFT:
                map.getPlayer().move(-1, 0, primaryStage);
                refresh(primaryStage);
                break;
            case RIGHT:
                map.getPlayer().move(1, 0, primaryStage);
                refresh(primaryStage);
                break;
            case E:
                map.getPlayer().getBackpack().addItemToBackPack();
                infoLabel.setText(map.getPlayer().getBackpack().showItemInfo());
                refresh(primaryStage);
                break;
            case K:
                map.getPlayer().openDoor();
                refresh(primaryStage);
                break;
            case Q:
                map.getPlayer().healthUp();
                refresh(primaryStage);
                break;
            case S:
                if (keyEvent.isControlDown()){
                    dbManager.saveGame(map);
                }
                Player player = map.getPlayer();
                //dbManager.savePlayer(player);
                JSONDatabaseManager.saveGame();
                break;
        }
    }

    private void refresh(Stage primaryStage) {
        ui.getChildren().clear();
        ai.moveNpc(primaryStage);
//        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < 43; x++) {
            for (int y = 0; y < 20; y++) {
                Player player = map.getPlayer();
                int playerPositionX = player.getX();
                int playerPositionY = player.getY();
                int windowX = cameraStopMove(playerPositionX, playerPositionY, x, y)[0];
                int windowY = cameraStopMove(playerPositionX, playerPositionY, x, y)[1];
                if (windowY < 0 || windowY >= map.getHeight()) {
                    Tiles.drawTile(context, () -> "empty", x, y);
                } else if (windowX < 0 || windowX >= map.getWidth()){
                    Tiles.drawTile(context, () -> "empty", x, y);
                }else {
                    Cell cell = map.getCell(windowX, windowY);
                    Tiles.drawTile(context, cell, x, y);
                    if (cell.getCellContent() != null) {
                        Tiles.drawTile(this.context, cell.getCellContent(), x, y);
                    } else {
                        Tiles.drawTile(this.context, cell, x, y);
                    }
                }

            }
        }
        showInventaryBar();
        if (GameMap.nextMap()){
            PlayMusic.setSoundTrack("src/main/resources/music/teleport.wav", 80.0f);

            map = MapLoader.loadMap(playerName);
            ai = new NpcMovement(map);
            refresh(primaryStage);
        }
        isWin(primaryStage);

        Label healthLabel = new Label("Health: ");
        ui.add(changeLabelFont("#679e02", healthLabel), 0, 0);

        Label nameLabel = new Label("Player name: ");
        ui.add(changeLabelFont("#679e02", nameLabel), 0, 1);

        Label name = new Label(playerName);
        ui.add(changeLabelFont("#5f0a8c", name), 1, 1);

        Label newLabel = new Label(String.valueOf(map.getPlayer().getHealth()) + "/10");
        ui.add(changeLabelFont("#5f0a8c", newLabel), 1, 0);
    }

    private void showInventaryBar(){
        Player player = map.getPlayer();
        for (int i=0; i<12; i++){
            inventoryBar.add(new ImageView(new Image("puste-miejsce.png", 59, 59, false, false)), i, 0);
        }
        if (player.backpack.containItemType("key")){
            inventoryBar.add(new ImageView(new Image("klucz.png", 59, 59, false, false)), 0, 0);
        }
        if (player.backpack.containItemType("cheese")){
            int countOfCheese=0;
            for (int i=0; i<player.backpack.getBackpackContent().toArray().length; i++){
                if (player.backpack.getBackpackContent().get(i) instanceof Cheese){
                    countOfCheese++;
                }
            }
            inventoryBar.add(new ImageView(new Image("ser"+countOfCheese+".png", 59, 59, false, false)), 1, 0);
        }
        if (player.backpack.containItemType("sword")){
            inventoryBar.add(new ImageView(new Image("miecz.png", 59, 59, false, false)), 2, 0);
        }
        if (player.backpack.containItemType("sword1")){
            inventoryBar.add(new ImageView(new Image("miecz.png", 59, 59, false, false)), 3, 0);
        }
        if (player.backpack.containItemType("helmet")){
            inventoryBar.add(new ImageView(new Image("helmet.png", 59, 59, false, false)), 4, 0);
        }
    }

    private int[] cameraStopMove(int playerPositionX, int playerPositionY, int x, int y){
        int windowX;
        int windowY;
        if (playerPositionX<11 && playerPositionY<6){
            windowX = x;
            windowY = y;
        }else if (playerPositionX>34 && playerPositionY<6){
            windowX = x+23;
            windowY = y;
        }else if (playerPositionX<11 && playerPositionY>20){
            windowX = x;
            windowY = y+14;
        }else if (playerPositionX>34 && playerPositionY>20){
            windowX = x+23;
            windowY = y+14;
        } else if (playerPositionX<11){
            windowX = x;
            windowY = playerPositionY + y - 6;
        }else if (playerPositionX>34){
            windowX = x+23;
            windowY = playerPositionY + y - 6;
        } else if (playerPositionY<6){
            windowX = playerPositionX + x - 11;
            windowY = y;
        } else if (playerPositionY>20){
            windowX = playerPositionX + x - 11;
            windowY = y+14;
        } else {
            windowX = playerPositionX + x - 11;
            windowY = playerPositionY + y - 6;
        }
        return new int[]{windowX, windowY};
    }

    private Label changeLabelFont(String colorString, Label newLabel){
        Paint paint = Color.web(colorString);
        newLabel.setTextFill(paint);
        newLabel.setStyle("-fx-font-family: 'Comic Sans MS'");
        return newLabel;
    }

    private void isWin(Stage primaryStage){
        Player player = map.getPlayer();
        if (player.backpack.containItemType("crown")){
            PlayMusic.setSoundTrack("src/main/resources/music/win.wav", 80.0f);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GameOver gameOver = new GameOver();
            gameOver.start(primaryStage, playerName, "YOU WIN");
        }
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
