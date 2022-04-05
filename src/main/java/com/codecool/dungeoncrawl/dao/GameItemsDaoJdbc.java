package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameItemsModel;

import javax.sql.DataSource;
import java.sql.*;
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

    }

    @Override
    public GameItemsModel get(int id) {
        return null;
    }

    @Override
    public List<GameItemsModel> getAll() {
        return null;
    }
}
