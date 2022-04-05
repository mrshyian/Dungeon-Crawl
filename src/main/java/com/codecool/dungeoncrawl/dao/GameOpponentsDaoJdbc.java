package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameOpponentsModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_opponents SET game_id = ?, opponent_id = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, opponent.getGameId());
            statement.setInt(2, opponent.getOpponentId());
            statement.setInt(3, opponent.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameOpponentsModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT game_id, opponent_id FROM game_opponents WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet game = statement.executeQuery();
            if (!game.next()) {
                return null;
            }
            GameOpponentsModel opponent = new GameOpponentsModel(game.getInt(1), game.getInt(2));
            opponent.setId(id);
            return opponent;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading game opponent id: " + id, e);
        }
    }

    @Override
    public List<GameOpponentsModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, game_id, opponent_id FROM game_opponents";
            ResultSet result = conn.createStatement().executeQuery(sql);
            List<GameOpponentsModel> opponents = new ArrayList<>();
            while (result.next()) {
                GameOpponentsModel opponent = new GameOpponentsModel(result.getInt(2), result.getInt(3));
                opponent.setId(result.getInt(1));
                opponents.add(opponent);
            }
            return opponents;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all game opponents", e);
        }
    }
}
