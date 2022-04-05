package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameDoorsModel;

import javax.sql.DataSource;
import java.sql.*;
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

    }

    @Override
    public GameDoorsModel get(int id) {
        return null;
    }

    @Override
    public List<GameDoorsModel> getAll() {
        return null;
    }
}
