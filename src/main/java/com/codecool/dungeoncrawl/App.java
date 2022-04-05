//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.codecool.dungeoncrawl;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.SQLException;

public class App {
    public App() {
    }
    static GameDatabaseManager dbManager;
    public static void main(String[] args) throws SQLException {
        GameDatabaseManager con = new GameDatabaseManager();
        dbManager = new GameDatabaseManager();
        dbManager.setup();

        PlayerModel player = new PlayerModel("mchal", 6, 6);
        player.setHp(12);
        player.setPlayerView("view");
        player.setPower(12);
        player.setShield(12);

        dbManager.updatePlayer(player);

    }
}
