package cash.flow.backend.repositorieTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import cash.flow.backend.configs.DatabaseConfig;
import cash.flow.backend.models.User;
import cash.flow.backend.repositories.UserRepository;

@SpringBootTest(properties = "spring.profiles.active=test")
@Import({DatabaseConfig.class, UserRepository.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setEmail("test@email.com");
    }

    @AfterEach
    void clearDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE;").execute();

            // Truncate all tables
            connection.prepareStatement("TRUNCATE TABLE member;").execute();

            // Re-enable foreign key checks
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY TRUE;").execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    @Test
    void testCreateUser() {
        userRepository.createUser(user);

        try (Connection c = dataSource.getConnection()) {
            var statement = c.prepareStatement("SELECT count(*) FROM member;");
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertEquals(1, count, "User should be created successfully.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Test
    void testGetUserByUsername() {
        userRepository.createUser(user);
        User retrievedUser = userRepository.getUserByUsername("testuser");

        assertEquals(user.getUsername(), retrievedUser.getUsername(), "Username should match.");
        assertEquals(user.getPassword(), retrievedUser.getPassword(), "Password should match.");
        assertEquals(user.getEmail(), retrievedUser.getEmail(), "Email should match.");
        assertFalse(user.isActive());
    }

    @Test
    void testUpdateUserStatus() {
        userRepository.createUser(user);
        user.setActive(true);
        userRepository.updateUserStatus(user);

        User updatedUser = userRepository.getUserByUsername("testuser");
        
        System.out.println("Updating user status: " + user);
        System.out.println("Updated user status: " + updatedUser);

        assertEquals(true, updatedUser.isActive(), "User status should be updated to active.");
    }
}
