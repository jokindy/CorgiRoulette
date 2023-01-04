package org.example.group;

import org.example.connection.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRepository {

    private static final String QUERY_FOR_PAIRS = "SELECT id FROM groups";

    public List<Integer> getGroupsId() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_FOR_PAIRS)) {

            ResultSet res = statement.executeQuery();

            List<Integer> group = new ArrayList<>();
            while (res.next()) {
                int id = res.getInt("id");
                group.add(id);
            }
            return group;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


}
