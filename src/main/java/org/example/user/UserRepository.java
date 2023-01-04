package org.example.user;

import org.example.connection.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final String QUERY_FOR_ALL_USERS = "SELECT * FROM users";
    private static final String QUERY_FOR_SINGLE_USER = "SELECT * FROM users WHERE id = ?";
    private static final String QUERY_FOR_UPDATE = "UPDATE users SET is_chosen = true, last_competition = ? " +
            "WHERE id in (?,?)";
    private static final String QUERY_FOR_UPDATE_ALL = "UPDATE users SET is_chosen = false";

    public List<User> getAllUsers() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_ALL_USERS)) {
            ResultSet res = statement.executeQuery();

            List<User> users = new ArrayList<>();
            while (res.next()) {
                users.add(mapRowToUser(res));
            }

            return users;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public User getUser(long userId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_SINGLE_USER)) {

            statement.setLong(1, userId);
            ResultSet res = statement.executeQuery();

            if (res.next()) {
                return mapRowToUser(res);
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void handlePair(long idOne, long idTwo) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_UPDATE)) {
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(2, idOne);
            statement.setLong(3, idTwo);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void refresh() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_UPDATE_ALL)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private User mapRowToUser(ResultSet res) throws SQLException {
        long id = res.getLong("id");
        String name = res.getString("name");
        String surname = res.getString("surname");
        long groupId = res.getLong("group_id");
        boolean isChosen = res.getBoolean("is_chosen");
        LocalDateTime date = res.getTimestamp("last_competition").toLocalDateTime();
        return new User(id, name, surname, groupId, isChosen, date);
    }


}
