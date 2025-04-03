package cash.flow.backend.repositorieTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import cash.flow.backend.configs.DatabaseConfig;
import cash.flow.backend.models.Category;
import cash.flow.backend.models.Day;
import cash.flow.backend.models.Month;
import cash.flow.backend.models.Note;
import cash.flow.backend.models.User;
import cash.flow.backend.repositories.CategoryRepository;
import cash.flow.backend.repositories.DayRepository;
import cash.flow.backend.repositories.MonthRepository;
import cash.flow.backend.repositories.NoteRepository;
import cash.flow.backend.repositories.UserRepository;

@SpringBootTest(properties = "spring.profiles.active=test")
@Import({DatabaseConfig.class, UserRepository.class, NoteRepository.class, MonthRepository.class, CategoryRepository.class, DayRepository.class})
public class DayRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private MonthRepository monthRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private User user;
    private Note note;
    private Month month;
    private Category category;
    private Day day;

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
        monthRepository.createMonth(month);
        month = monthRepository.getMonthByNIdYearMonth(note.getNId(), 2023, 10);

        category = new Category();
        category.setCName("testCategory");
        category.setNoteFk(note.getNId());
        category.setColor("#9FB3DF");
        categoryRepository.createCategory(category);
        category = categoryRepository.getCategoriesByNote(note.getNId()).get(0);

        day = new Day();
        day.setDetail("testDetail");
        day.setTransactionValue(100);
        day.setNote("testNote");
        day.setMonthFk(month.getMId());
        day.setCategoryFk(category.getCId());
        dayRepository.createDay(day);
        day = dayRepository.getDaysByMonth(month.getMId()).get(0);
    }

    @AfterEach
    void clearDatabase() {
        try (var connection = dataSource.getConnection()) {
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE;").execute();

            // Truncate all tables
            connection.prepareStatement("TRUNCATE TABLE months;").execute();
            connection.prepareStatement("TRUNCATE TABLE note;").execute();
            connection.prepareStatement("TRUNCATE TABLE member;").execute();
            connection.prepareStatement("TRUNCATE TABLE category;").execute();
            connection.prepareStatement("TRUNCATE TABLE days;").execute();

            // Re-enable foreign key checks
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY TRUE;").execute();
        } catch (Exception e) {
            throw new RuntimeException("Error while clearing the database", e);
        }
    }
    
    @Test
    void testGetDaysByMonth() {
        assertEquals(dayRepository.getDaysByMonth(month.getMId()).size(), 1);
        assertEquals(day.getDetail(), "testDetail");
        assertEquals(day.getTransactionValue(), 100);
        assertEquals(day.getNote(), "testNote");
        assertEquals(day.getMonthFk(), month.getMId());
        assertEquals(day.getCategoryFk(), category.getCId());
        assertEquals(day.getCategoryName(), category.getCName());
        assertEquals(day.getCategoryColor(), "#9FB3DF");
    }

    @Test
    void testCreateDay() {
        Day newDay = new Day();
        newDay.setDetail("newTestDetail");
        newDay.setTransactionValue(200);
        newDay.setNote("newTestNote");
        newDay.setMonthFk(month.getMId());
        newDay.setCategoryFk(category.getCId());

        boolean created = dayRepository.createDay(newDay);
        assertTrue(created);

        assertEquals(dayRepository.getDaysByMonth(month.getMId()).size(), 2, "Should be 2 days now");
    }

    @Test
    void testUpdateDay() {
        day.setDetail("updatedDetail");
        day.setTransactionValue(150);
        day.setNote("updatedNote");
        boolean updated = dayRepository.updateDay(day);
        assertTrue(updated);

        Day updatedDay = dayRepository.getDaysByMonth(month.getMId()).get(0);
        assertEquals(updatedDay.getDetail(), "updatedDetail");
        assertEquals(updatedDay.getTransactionValue(), 150);
        assertEquals(updatedDay.getNote(), "updatedNote");
    }

    @Test
    void testDeleteDay() {
        boolean deleted = dayRepository.deleteDay(day.getDId());
        assertTrue(deleted);

        assertEquals(dayRepository.getDaysByMonth(month.getMId()).size(), 0, "Should be 0 days now");
    }
    
}
