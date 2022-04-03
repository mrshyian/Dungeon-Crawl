package com.codecool.dungeoncrawl;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


public class GameOver {


    public void start(Stage stage, String playerName, String status) {
        Button button1 = new Button("START NEW GAME"); // przycisk
        Button button2 = new Button("EXIT"); // przycisk
        VBox gameOverGrid = new VBox();
        Label label = new Label(status); // stworzenie nowej etykiety
        Label labelPlayerName = new Label(playerName); // stworzenie nowej etykiety
        label.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-size: 70; -fx-font-weight: bold");
        labelPlayerName.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-size: 70; -fx-font-weight: bold");
        Paint Paint = Color.web("#148ec7");
        label.setTextFill(Paint);
        labelPlayerName.setTextFill(Paint);
        button1.setStyle("-fx-font-family: 'Comic Sans MS'");
        button1.setTextFill(Paint);
        button2.setStyle("-fx-font-family: 'Comic Sans MS'");
        button2.setTextFill(Paint);

        BackgroundImage backk = new BackgroundImage(new Image("background1.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);


        gameOverGrid.setBackground(new Background(backk));
        gameOverGrid.getChildren().add(labelPlayerName);
        gameOverGrid.getChildren().add(label);
        gameOverGrid.getChildren().add(button1);
        gameOverGrid.getChildren().add(button2);

        // formatowanie okna
        gameOverGrid.setPadding(new Insets(10, 10, 10, 10)); // marginesy
        GridPane.setHalignment(label, HPos.CENTER); // wyśrodkowanie tekstu
        GridPane.setHalignment(button1, HPos.CENTER); // wyśrodkowanie przycisku
        GridPane.setHalignment(button2, HPos.CENTER); // wyśrodkowanie przycisku
        gameOverGrid.setAlignment(Pos.BASELINE_CENTER); // centrowanie okna
        VBox.setMargin(button1, new Insets(10, 10, 10, 10)); // ustawianie marginesów dookoła obiektu VBox
        VBox.setMargin(label, new Insets(10, 10, 10, 10)); // ustawianie marginesów dookoła obiektu VBox
        VBox.setMargin(button2, new Insets(10, 10, 10, 10));


        button1.setOnAction(ev -> startGame(stage, playerName)); // przypisanie wydarzenie do przycisku
        button2.setOnAction(ev -> exitGame()); // przypisanie wydarzenie do przycisku

        Scene scene = new Scene(gameOverGrid); // tworzenie nowego okna
        stage.setScene(scene); // wyświetlanie okna
        stage.setHeight(500.1);
        stage.setWidth(700.1);


        stage.setTitle("Dungeon Crawl"); // ustawianie tytułu okna
        stage.show();  // wyświetlenie okna
    }

    private void exitGame() {
        Platform.exit();
        System.exit(0);
    }

    private void startGame(Stage stage , String playerName) {
        Main main = new Main(playerName);
        main.start(stage);
    }
}
