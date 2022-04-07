package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (name, playerview, x, y, health, power, shield)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setString(2, player.getPlayerView());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.setInt(5, player.getHp());
            statement.setInt(6, player.getPower());
            statement.setInt(7, player.getShield());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {
            try (Connection conn = dataSource.getConnection()) {
                String sql = "UPDATE player SET playerview = ?, x = ?, y = ?, health = ?, " +
                        "power = ?, shield = ? WHERE name = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, player.getPlayerView());
                statement.setInt(2, player.getX());
                statement.setInt(3, player.getY());
                statement.setInt(4, player.getHp());
                statement.setInt(5, player.getPower());
                statement.setInt(6, player.getShield());
                statement.setString(7, player.getPlayerName());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public PlayerModel get(String userName){
    try (Connection conn = dataSource.getConnection()) {
        String sql = "SELECT name, playerview, x, y, health, power, shield FROM player" +
                " WHERE name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, userName);
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        ResultSet rs = statement.executeQuery();
        if (!rs.next()) {
            return null;
        }

        PlayerModel playerModel =dataSet(rs);
        return playerModel;
    } catch (SQLException e) {
        throw new RuntimeException("Error while reading player id:" + userName, e);
    }
}

    private PlayerModel dataSet(ResultSet rs) throws SQLException {
        String name = rs.getString(1);
        String playerView = rs.getString(2);
        int x = rs.getInt(3);
        int y = rs.getInt(4);
        int health = rs.getInt(5);
        int power = rs.getInt(6);
        int shield = rs.getInt(7);

        PlayerModel playerModel = new PlayerModel(name, playerView, x, y, health, power, shield);
        return playerModel;

    }

    @Override
    public List<PlayerModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "SELECT name, playerview, x, y, health, power, shield FROM player";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<PlayerModel> result = new ArrayList<>();
            while (rs.next()) {

                PlayerModel playerModel = dataSet(rs);
                result.add(playerModel);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all players", e);
        }
    }

    @Override
    public void remove(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM player WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("You cannot delete player with name: " + name, e);
        }
    }
}
