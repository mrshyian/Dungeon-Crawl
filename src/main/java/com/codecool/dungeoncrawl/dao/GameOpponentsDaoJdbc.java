package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameOpponentsModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class GameOpponentsDaoJdbc implements GameOpponentsDao {

    private DataSource dataSource;

    public GameOpponentsDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameOpponentsModel opponent) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_opponents (game_id, opponent_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, opponent.getGameId());
            statement.setInt(2, opponent.getOpponentId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            opponent.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameOpponentsModel opponent) {

    }

    @Override
    public GameOpponentsModel get(int id) {
        return null;
    }

    @Override
    public List<GameOpponentsModel> getAll() {
        return null;
    }
}
