package org.example.user;

import org.example.connection.DataSource;
import org.example.pair.Pair;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final String QUERY_FOR_ALL_USERS = "SELECT * FROM users";
    private static final String QUERY_FOR_UPDATE = "UPDATE users SET is_chosen = true, last_competition = ? WHERE id in (?,?)";
    private static final String QUERY_FOR_UPDATE_ALL = "UPDATE users SET is_chosen = false";
    private static final String QUERY_FOR_CHECK = "SELECT * FROM users WHERE id in (?,?)";

    public List<User> getAllUsers() {
        try (Connection connection = DataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(QUERY_FOR_ALL_USERS)) {
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

    public void handlePair(Pair pair) {
        try (Connection connection = DataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(QUERY_FOR_UPDATE)) {
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(2, pair.getIdOne());
            statement.setLong(3, pair.getIdTwo());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean checkPair(Pair pair) {
        try (Connection connection = DataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(QUERY_FOR_CHECK)) {

            statement.setLong(1, pair.getIdOne());
            statement.setLong(2, pair.getIdTwo());

            ResultSet res = statement.executeQuery();

            List<User> users = new ArrayList<>();
            while (res.next()) {
                users.add(mapRowToUser(res));
            }
            User u1 = users.get(0);
            User u2 = users.get(1);
            return checkUsers(u1, u2);
        } catch (SQLException e) {
            throw new RuntimeException();
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

    private boolean checkUsers(User u1, User u2) {
        Duration duration1 = Duration.between(u1.getLastCompetition(), LocalDateTime.now());
        int comparison1 = duration1.compareTo(Duration.ofHours(1));
        Duration duration2 = Duration.between(u2.getLastCompetition(), LocalDateTime.now());
        int comparison2 = duration2.compareTo(Duration.ofHours(1));

        boolean additional = u1.isChosen() && u2.isChosen();

        if ((u1.isChosen() && u2.isChosen()) && (comparison1 < 0 && comparison2 < 0)) {
            return false;
        } else if (u1.isChosen() && comparison1 < 0 && !u2.isChosen()) {
            return true;
        } else if (u1.isChosen() && comparison1 > 0 && !u2.isChosen()) {
            return false;
        } else if (u2.isChosen() && comparison2 < 0 && !u1.isChosen()) {
            return true;
        } else if (u2.isChosen() && comparison2 > 0 && !u1.isChosen()) {
            return false;
        } else {
            return true;
        }
    }

    private User mapRowToUser(ResultSet res) throws SQLException {
        long id = res.getLong("id");
        String name = res.getString("name");
        long groupId = res.getLong("group_id");
        boolean isChosen = res.getBoolean("is_chosen");
        LocalDateTime date = res.getTimestamp("last_competition").toLocalDateTime();
        return new User(id, name, groupId, isChosen, date);
    }


}
