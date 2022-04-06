package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameItemsModel;
import com.codecool.dungeoncrawl.model.PlayerBackpackModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerBackpackDaoJdbc implements PlayerBackpackDao {

    private DataSource dataSource;

    public PlayerBackpackDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerBackpackModel backpack) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player_backpack (player_name, item_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, backpack.getPlayerName());
            statement.setInt(2, backpack.getItemId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            backpack.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerBackpackModel backpack) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE player_backpack SET player_name = ?, item_id = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, backpack.getPlayerName());
            statement.setInt(2, backpack.getItemId());
            statement.setInt(3, backpack.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerBackpackModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT player_name, item_id FROM player_backpack WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet game = statement.executeQuery();
            if (!game.next()) {
                return null;
            }
            PlayerBackpackModel backpack = new PlayerBackpackModel(game.getString(1), game.getInt(2));
            backpack.setId(id);
            return backpack;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading game backpack id: " + id, e);
        }
    }

    @Override
    public List<PlayerBackpackModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, player_name, item_id FROM player_backpack";
            ResultSet result = conn.createStatement().executeQuery(sql);
            List<PlayerBackpackModel> items = new ArrayList<>();
            while (result.next()) {
                PlayerBackpackModel item = new PlayerBackpackModel(result.getString(2), result.getInt(3));
                item.setId(result.getInt(1));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all game items", e);
        }
    }
}
