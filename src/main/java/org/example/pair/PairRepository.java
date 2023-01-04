package org.example.pair;

import org.example.connection.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PairRepository {

    private static final String QUERY_FOR_PAIRS = "SELECT * FROM pairs";
    private static final String QUERY_FOR_SAVE_PAIR = "INSERT INTO pairs VALUES (?,?)";
    private static final String QUERY_FOR_CHECK = "SELECT * FROM pairs WHERE (user_id = ? and competitor_id = ?) "
            + "OR (user_id = ? and competitor_id = ?)";

    public void savePair(Pair pair) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_SAVE_PAIR)) {
            statement.setLong(1, pair.getIdOne());
            statement.setLong(2, pair.getIdTwo());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<Pair> getPairs() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_PAIRS)) {

            ResultSet res = statement.executeQuery();

            List<Pair> pairs = new ArrayList<>();
            while (res.next()) {
                pairs.add(mapRowToPair(res));
            }
            return pairs;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public boolean checkPair(Pair pair) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_CHECK)) {
            statement.setLong(1, pair.getIdOne());
            statement.setLong(2, pair.getIdTwo());
            statement.setLong(4, pair.getIdOne());
            statement.setLong(3, pair.getIdTwo());
            ResultSet res = statement.executeQuery();
            return res.next();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Pair mapRowToPair(ResultSet res) throws SQLException {
        int idOne = res.getInt("user_id");
        int idTwo = res.getInt("competitor_id");
        return new Pair(idOne, idTwo);
    }
}