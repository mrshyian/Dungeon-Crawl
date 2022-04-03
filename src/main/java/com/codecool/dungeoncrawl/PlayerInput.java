package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.PlayMusic;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


public class PlayerInput extends Application {

    public static void start(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        PlayMusic.setSoundTrack("src/main/resources/music/start.wav", 50.0f);

        VBox ui = new VBox();
        TextField playerName = new TextField(); // pole na wpisanie tekstu
        playerName.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-text-fill: #679e02");
        Label label = new Label("Enter player name:"); // stworzenie nowej etykiety
        Paint Paint = Color.web("#679e02");
        label.setTextFill(Paint);
        label.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-size: 30");
        Button button = new Button("START"); // przycisk
        button.setStyle("-fx-font-family: 'Comic Sans MS'");
        button.setTextFill(Paint);
        BackgroundImage backk = new BackgroundImage(new Image("unknown.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        ui.setBackground(new Background(backk));
        ui.getChildren().add(label);
        ui.getChildren().add(playerName);
        ui.getChildren().add(button);

        // formatowanie okna
        ui.setPadding(new Insets(10, 10, 10, 10)); // marginesy
        ui.setAlignment(Pos.BASELINE_CENTER); // centrowanie okna
        VBox.setMargin(button, new Insets(10, 10, 10, 10)); // ustawianie marginesów dookoła obiektu VBox
        VBox.setMargin(label, new Insets(10, 10, 10, 10)); // ustawianie marginesów dookoła obiektu VBox
        VBox.setMargin(playerName, new Insets(10, 10, 10, 10)); // ustawianie marginesów dookoła obiektu VBox

        button.setOnAction(ev -> startGame(stage, playerName.getText())); // przypisanie wydarzenie do przycisku

        Scene scene = new Scene(ui); // tworzenie nowego okna
        stage.setScene(scene); // wyświetlanie okna
        stage.setMinHeight(400.1);
        stage.setMinWidth(400.1);

        stage.setTitle("Dungeon Crawl"); // ustawianie tytułu okna
        stage.show();  // wyświetlenie okna
    }

    private void startGame(Stage stage, String playerName) {
        Main main = new Main(playerName);
        main.start(stage);
    }
}
