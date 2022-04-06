package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.OpponentModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpponentDaoJdbc implements OpponentDao {
    private DataSource dataSource;

    public OpponentDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(OpponentModel opponent) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO opponent ( x, y, name, health, power, shield)" +
                    " VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            importDataSet(opponent, statement);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            opponent.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void importDataSet(OpponentModel opponent, PreparedStatement statement) throws SQLException {
        statement.setInt(1, opponent.getX());
        statement.setInt(2, opponent.getY());
        statement.setString(3, opponent.getOpponentName());
        statement.setInt(4, opponent.getHp());
        statement.setInt(5, opponent.getPower());
        statement.setInt(6, opponent.getShield());
    }

    @Override
    public void update(OpponentModel opponent) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE opponent SET  x = ?, y = ?, name = ? health = ?, " +
                    "power = ?, shield = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            importDataSet(opponent, statement);
            statement.setInt(7, opponent.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OpponentModel get(int id){
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT x, y, name, health, power, shield FROM opponent" +
                    " WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }

            OpponentModel opponentModel = exportDataSet(rs);
            return opponentModel;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading game opponent id: " + id, e);
        }
    }

    @Override
    public List<OpponentModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "SELECT x, y, name, health, power, shield FROM opponent";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<OpponentModel> result = new ArrayList<>();
            while (rs.next()) {

                OpponentModel opponentModel = exportDataSet(rs);
                result.add(opponentModel);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all game opponents",e);
        }
    }

    private OpponentModel exportDataSet(ResultSet rs) throws SQLException {
        int x = rs.getInt(2);
        int y = rs.getInt(1);
        String name = rs.getString(3);
        int hp = rs.getInt(4);
        int power = rs.getInt(5);
        int shield = rs.getInt(6);


        OpponentModel opponentModel = new OpponentModel(name, x, y, hp, power, shield);
        return opponentModel;
    }
}
