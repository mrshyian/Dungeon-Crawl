package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameDoorsModel;
import com.codecool.dungeoncrawl.model.GameItemsModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDoorsDaoJdbc implements GameDoorsDao {

    private DataSource dataSource;

    public GameDoorsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameDoorsModel door) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_doors (game_id, door_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, door.getGameId());
            statement.setInt(2, door.getDoorId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            door.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameDoorsModel door) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_doors SET game_id = ?, door_id = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, door.getGameId());
            statement.setInt(2, door.getDoorId());
            statement.setInt(3, door.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GameDoorsModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT game_id, door_id FROM game_doors WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet game = statement.executeQuery();
            if (!game.next()) {
                return null;
            }
            GameDoorsModel door = new GameDoorsModel(game.getInt(1), game.getInt(2));
            door.setId(id);
            return door;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading game door id: " + id, e);
        }
    }

    @Override
    public List<GameDoorsModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, game_id, door_id FROM game_doors";
            ResultSet result = conn.createStatement().executeQuery(sql);
            List<GameDoorsModel> doors = new ArrayList<>();
            while (result.next()) {
                GameDoorsModel door = new GameDoorsModel(result.getInt(2), result.getInt(3));
                door.setId(result.getInt(1));
                doors.add(door);
            }
            return doors;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all game doors", e);
        }
    }
}
