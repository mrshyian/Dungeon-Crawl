package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameItemsModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameItemsDaoJdbc implements GameItemsDao {

    private DataSource dataSource;

    public GameItemsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameItemsModel item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_items (game_id, item_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, item.getGameId());
            statement.setInt(2, item.getItemId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            item.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameItemsModel item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_items SET game_id = ?, item_id = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, item.getGameId());
            statement.setInt(2, item.getItemId());
            statement.setInt(3, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameItemsModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT game_id, item_id FROM game_items WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet game = statement.executeQuery();
            if (!game.next()) {
                return null;
            }
            GameItemsModel item = new GameItemsModel(game.getInt(1), game.getInt(2));
            item.setId(id);
            return item;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading game item id: " + id, e);
        }
    }

    @Override
    public List<GameItemsModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, game_id, item_id FROM game_items";
            ResultSet result = conn.createStatement().executeQuery(sql);
            List<GameItemsModel> items = new ArrayList<>();
            while (result.next()) {
                GameItemsModel item = new GameItemsModel(result.getInt(2), result.getInt(3));
                item.setId(result.getInt(1));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all game items", e);
        }
    }
}
