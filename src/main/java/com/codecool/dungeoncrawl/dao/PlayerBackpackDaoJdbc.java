package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerBackpackModel;

import javax.sql.DataSource;
import java.sql.*;
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

    }

    @Override
    public PlayerBackpackModel get(int id) {
        return null;
    }

    @Override
    public List<PlayerBackpackModel> getAll() {
        return null;
    }
}
