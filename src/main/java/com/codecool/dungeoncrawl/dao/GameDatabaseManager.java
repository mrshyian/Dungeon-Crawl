package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.OpponentModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private OpponentDao opponentDao;
    private ItemDao itemDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        opponentDao = new OpponentDaoJdbc(dataSource);
        itemDao = new ItemDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }


    public PlayerModel getPlayer(String userName){
        return playerDao.get(userName);
    }

    public void updatePlayer(Player player){
        PlayerModel model = new PlayerModel(player);
        playerDao.update(model);
    }

    public List<PlayerModel> getAllPlayers(){
        return playerDao.getAll();
    }

    public void saveOpponent(Actor opponent){
        OpponentModel model = new OpponentModel(opponent);
        opponentDao.add(model);
    }

    public OpponentModel getOpponent(int id){
        return opponentDao.get(id);
    }

    public void updateOpponent(Actor opponent){
        OpponentModel model = new OpponentModel(opponent);
        opponentDao.update(model);
    }

    public List<OpponentModel> getAllOpponents(){
        return opponentDao.getAll();
    }

    public void saveItem(Item item){
        ItemModel model = new ItemModel(item);
        itemDao.add(model);
    }

    public ItemModel getItem(int id){
        return itemDao.get(id);
    }

    public void updateItem(Item item){
        ItemModel model = new ItemModel(item);
        itemDao.update(model);
    }

    public List<ItemModel> getAllItems(){
        return itemDao.getAll();
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("PSQL_DB_NAME");
        String user = System.getenv("PSQL_USER_NAME");
        String password = System.getenv("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

}

