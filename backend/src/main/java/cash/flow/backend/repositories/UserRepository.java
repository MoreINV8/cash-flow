package cash.flow.backend.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cash.flow.backend.models.User;

@Repository
public class UserRepository {
    @Autowired
    private DataSource dataSource;

    public User getUserByUsername(String username) {
        User user = null;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT username, pass, email, active FROM member WHERE username = ?;");

            statement.setString(1, username);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("pass"));
                user.setEmail(resultSet.getString("email"));
                user.setActive(resultSet.getBoolean("active"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }

        return user;
    }
    
    public boolean createUser(User user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO member (username, pass, email, active) VALUES (?, ?, ?, ?);");

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setBoolean(4, false);

            int rowsInserted = statement.executeUpdate();
            
            return rowsInserted > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    public void updateUserStatus(User user) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE member SET active = ? WHERE username = ?;");

            statement.setBoolean(1, user.isActive());
            statement.setString(2, user.getUsername());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }
}
