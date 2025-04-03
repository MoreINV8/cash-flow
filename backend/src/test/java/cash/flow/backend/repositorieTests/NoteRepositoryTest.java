package cash.flow.backend.repositorieTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import cash.flow.backend.models.Note;
import cash.flow.backend.models.User;
import cash.flow.backend.repositories.NoteRepository;
import cash.flow.backend.repositories.UserRepository;

@SpringBootTest(properties = "spring.profiles.active=test")
@Import({DatabaseConfig.class, NoteRepository.class, UserRepository.class})
public class NoteRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private DataSource dataSource;

    private User user;
    private Note note;
    
    @BeforeEach
    void setUpNote() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setEmail("test@email.com");

        note = new Note();
        note.setUserFk(user.getUsername());

        userRepository.createUser(user);
    }

    @AfterEach
    void clearDatabase() {
        // Clean up the database after each test
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE;").execute();

            // Truncate all tables
            connection.prepareStatement("TRUNCATE TABLE member;").execute();
            connection.prepareStatement("TRUNCATE TABLE note;").execute();

            // Re-enable foreign key checks
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY TRUE;").execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    @Test
    void testCreateNote() {
        boolean result = noteRepository.createNote(user.getUsername());
        assertTrue(result, "Note should be created successfully");
    }

    @Test
    void testGetNoteByUsername() {
        noteRepository.createNote(user.getUsername());
        Note retrievedNote = noteRepository.getNoteByUsername(user.getUsername());
        assertNotNull(retrievedNote, "Note should not be null");
        assertEquals(retrievedNote.getUserFk(), note.getUserFk(), "User FK should match");
    }
}
