package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.ItemModel;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoJdbc implements ItemDao {
    private DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void add(ItemModel item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO item (x, y, name, health, power, shield)" +
                    " VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            importDataSet(item, statement);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            item.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ItemModel item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE item SET  x = ?, y = ?, name = ?, health = ?, " +
                    "power = ?, shield = ? WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            importDataSet(item, statement);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x, y, name, health, power, shield FROM item" +
                    " WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }

            int x = rs.getInt(1);
            int y = rs.getInt(2);
            String name = rs.getString(3);
            int hp = rs.getInt(4);
            int power = rs.getInt(5);
            int shield = rs.getInt(6);

            ItemModel itemModel = new ItemModel(id, x, y, name, hp, power, shield);;
            return itemModel;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading game item id: " + id, e);
        }
    }


    @Override
    public List<ItemModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "SELECT id, x, y, name, health, power, shield FROM item";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<ItemModel> result = new ArrayList<>();
            while (rs.next()) {

                int id = rs.getInt(1);
                int x = rs.getInt(2);
                int y = rs.getInt(3);
                String name = rs.getString(4);
                int hp = rs.getInt(5);
                int power = rs.getInt(6);
                int shield = rs.getInt(7);


                ItemModel itemModel = new ItemModel(id, x, y, name, hp, power, shield);
                result.add(itemModel);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all game items",e);
        }
    }


    private void importDataSet(ItemModel item, PreparedStatement statement) throws SQLException {
        statement.setInt(1, item.getX());
        statement.setInt(2, item.getY());
        statement.setString(3, item.getItemName());
        statement.setInt(4, item.getHp());
        statement.setInt(5, item.getPower());
        statement.setInt(6, item.getShield());
        statement.executeUpdate();
    }
    }
