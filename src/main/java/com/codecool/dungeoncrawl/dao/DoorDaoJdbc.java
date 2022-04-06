package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.DoorModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class DoorDaoJdbc implements DoorDao {

    private DataSource dataSource;

    public DoorDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(DoorModel door) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO door (x, y, is_open) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, door.getX());
            statement.setInt(2, door.getY());
            statement.setBoolean(3, door.isOpen());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            door.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(DoorModel door) {

    }

    @Override
    public DoorModel get(int id) {
        return null;
    }

    @Override
    public List<DoorModel> getAll() {
        return null;
    }
}
