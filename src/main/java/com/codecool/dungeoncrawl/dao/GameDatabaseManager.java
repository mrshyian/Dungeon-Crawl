package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Door;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.*;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private OpponentDao opponentDao;
    private ItemDao itemDao;
    private DoorDao doorDao;
    private GameDoorsDao gameDoorsDao;
    private GameItemsDao gameItemsDao;
    private GameOpponentsDao gameOpponentsDao;
    private GameStateDao gameStateDao;
    private PlayerBackpackDao playerBackpackDao;


    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource); //
        opponentDao = new OpponentDaoJdbc(dataSource); //
        itemDao = new ItemDaoJdbc(dataSource); //
        doorDao = new DoorDaoJdbc(dataSource); //
        gameDoorsDao = new GameDoorsDaoJdbc(dataSource); //
        gameItemsDao = new GameItemsDaoJdbc(dataSource); //
        gameOpponentsDao = new GameOpponentsDaoJdbc(dataSource); //
        gameStateDao = new GameStateDaoJdbc(dataSource); //
        playerBackpackDao = new PlayerBackpackDaoJdbc(dataSource);
    }

    public void saveGame(GameMap game) {
        if(game.getId() > 0) {
            gameStateDao.delete(game.getId());
        }
        PlayerModel playerModel = new PlayerModel(game.getPlayer());
        playerDao.add(playerModel);
        savePlayerBackpack(game.getPlayer());
        GameStateModel gameStateModel;
        try {
            gameStateModel = saveGameState(playerModel);
            saveOpponents(game, gameStateModel);
            saveItems(game, gameStateModel);
            saveDoors(game, gameStateModel);
            game.setId(gameStateModel.getId());
        }
        catch (IOException | URISyntaxException ex){
            System.out.println("Cannot save GameState");
        }
    }

    private GameStateModel saveGameState(PlayerModel playerModel) throws IOException, URISyntaxException {
        int lvl = MapLoader.flag;
        String currentMap;
        Date date = new Date(System.currentTimeMillis()); // bierzący czas przerabiany na date
        if (lvl == 1){
            Path path = Paths.get("src","main","resources", "emptymap.txt");
            currentMap = Files.readString(path.toAbsolutePath());
        }
        else if (lvl == 2){
            currentMap = Files.readString(Path.of("emptymap1.txt"));
        }
        else {
            currentMap = Files.readString(Path.of("emptymap2.txt"));
        }
        GameStateModel gameStateModel = new GameStateModel(currentMap, date, playerModel);
        gameStateDao.add(gameStateModel);
        return gameStateModel;
    }

    private void saveOpponents(GameMap gameMap, GameStateModel gameStateModel){
        saveGoblins(gameMap, gameStateModel);
        saveGhosts(gameMap, gameStateModel);
        saveMonsters(gameMap, gameStateModel);
        saveSkeletons(gameMap, gameStateModel);
    }

    private void saveGoblins(GameMap gameMap, GameStateModel gameStateModel){
        ArrayList<Goblin> goblins = gameMap.getGoblins();
        for (Goblin goblin: goblins){
            OpponentModel opponentModel = new OpponentModel(goblin);
            opponentDao.add(opponentModel);
            GameOpponentsModel gameOpponentsModel = new GameOpponentsModel(gameStateModel.getId(), opponentModel.getId());
            gameOpponentsDao.add(gameOpponentsModel);
        }
    }

    private void saveGhosts(GameMap gameMap, GameStateModel gameStateModel){
        ArrayList<Ghost> ghosts = gameMap.getGhosts();
        for (Ghost ghost: ghosts){
            OpponentModel opponentModel = new OpponentModel(ghost);
            opponentDao.add(opponentModel);
            GameOpponentsModel gameOpponentsModel = new GameOpponentsModel(gameStateModel.getId(), opponentModel.getId());
            gameOpponentsDao.add(gameOpponentsModel);
        }
    }

    private void saveMonsters(GameMap gameMap, GameStateModel gameStateModel){
        ArrayList<Monster> monsters = gameMap.getMonsters();
        for (Monster monster: monsters){
            OpponentModel opponentModel = new OpponentModel(monster);
            opponentDao.add(opponentModel);
            GameOpponentsModel gameOpponentsModel = new GameOpponentsModel(gameStateModel.getId(), opponentModel.getId());
            gameOpponentsDao.add(gameOpponentsModel);
        }
    }

    private void saveSkeletons(GameMap gameMap, GameStateModel gameStateModel){
        ArrayList<Skeleton> skeletons = gameMap.getSkeletons();
        for (Skeleton skeleton: skeletons){
            OpponentModel opponentModel = new OpponentModel(skeleton);
            opponentDao.add(opponentModel);
            GameOpponentsModel gameOpponentsModel = new GameOpponentsModel(gameStateModel.getId(), opponentModel.getId());
            gameOpponentsDao.add(gameOpponentsModel);
        }
    }

    private void saveItems(GameMap gameMap, GameStateModel gameStateModel){
        ArrayList<Item> items = gameMap.getItems();
        for (Item item: items){
            ItemModel itemModel = new ItemModel(item);
            itemDao.add(itemModel);
            GameItemsModel gameItemsModel = new GameItemsModel(gameStateModel.getId(), itemModel.getId());
            gameItemsDao.add(gameItemsModel);
        }
    }

    private void saveDoors(GameMap gameMap, GameStateModel gameStateModel){
        ArrayList<Door> doors = gameMap.getDoors();
        for (Door door: doors){
            DoorModel doorModel = new DoorModel(door);
            doorDao.add(doorModel);
            GameDoorsModel gameDoorsModel = new GameDoorsModel(gameStateModel.getId(), doorModel.getId());
            gameDoorsDao.add(gameDoorsModel);
        }
    }

    private void savePlayerBackpack(Player player){
        ArrayList<Item> items = player.getBackpack().getBackpackContent();
        for (Item item: items){
            ItemModel itemModel = new ItemModel(item);
            itemDao.add(itemModel);
            PlayerBackpackModel playerBackpackModel = new PlayerBackpackModel(player.getName(), itemModel.getId());
            playerBackpackDao.add(playerBackpackModel);
        }
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

