package cash.flow.backend.repositorieTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import cash.flow.backend.configs.DatabaseConfig;
import cash.flow.backend.models.Month;
import cash.flow.backend.models.Note;
import cash.flow.backend.models.User;
import cash.flow.backend.repositories.MonthRepository;
import cash.flow.backend.repositories.NoteRepository;
import cash.flow.backend.repositories.UserRepository;

@SpringBootTest(properties = "spring.profiles.active=test")
@Import({DatabaseConfig.class, UserRepository.class, NoteRepository.class, MonthRepository.class})
public class MonthRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MonthRepository monthRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;
    
    private User user;
    private Note note;
    private Month month;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("test@email.com");
        userRepository.createUser(user);

        note = new Note();
        note.setUserFk(user.getUsername());
        noteRepository.createNote(user.getUsername());
        note = noteRepository.getNoteByUsername(user.getUsername());

        month = new Month();
        month.setYear(2023);
        month.setMonth(10);
        month.setNoteFk(note.getNId());
    }

    @AfterEach
    void clearDatabase() {
        try (var connection = dataSource.getConnection()) {
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE;").execute();

            // Truncate all tables
            connection.prepareStatement("TRUNCATE TABLE months;").execute();
            connection.prepareStatement("TRUNCATE TABLE note;").execute();
            connection.prepareStatement("TRUNCATE TABLE member;").execute();

            // Re-enable foreign key checks
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY TRUE;").execute();
        } catch (Exception e) {
            throw new RuntimeException("Error while clearing the database", e);
        }
    }

    @Test
    void testCreateMonth() {
        boolean created = monthRepository.createMonth(month);
        assertTrue(created, "Month should be created successfully");
    }

    @Test
    void testGetMonthByNIdYearMonth() {
        monthRepository.createMonth(month);
        Month retrievedMonth = monthRepository.getMonthByNIdYearMonth(note.getNId(), month.getYear(), month.getMonth());
        assertNotNull(retrievedMonth, "Month should be retrieved successfully");
        assertEquals(retrievedMonth.getYear(), month.getYear(), "Retrieved month year should match the created month year");
        assertEquals(retrievedMonth.getMonth(), month.getMonth(), "Retrieved month should match the created month");
        assertEquals(retrievedMonth.getNoteFk(), month.getNoteFk(),
                "Retrieved month note ID should match the created month note ID");
    }

}
