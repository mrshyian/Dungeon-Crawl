package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.DoorModel;
import com.codecool.dungeoncrawl.model.GameItemsModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE door SET x = ?, y = ?, is_open = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, door.getX());
            statement.setInt(2, door.getY());
            statement.setBoolean(3, door.isOpen());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DoorModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x, y, is_open FROM door WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet door = statement.executeQuery();
            if (!door.next()) {
                return null;
            }
            DoorModel doorModel = new DoorModel(door.getInt(1), door.getInt(2), door.getBoolean(3));
            doorModel.setId(id);
            return doorModel;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading game item id: " + id, e);
        }
    }

    @Override
    public List<DoorModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x, y, is_open FROM door";
            ResultSet result = conn.createStatement().executeQuery(sql);
            List<DoorModel> doors = new ArrayList<>();
            while (result.next()) {
                DoorModel door = new DoorModel(result.getInt(1), result.getInt(2), result.getBoolean(3));
                door.setId(result.getInt(1));
                doors.add(door);
            }
            return doors;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all game items", e);
        }
    }
}
