package cash.flow.backend.repositorieTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import cash.flow.backend.configs.DatabaseConfig;
import cash.flow.backend.models.Category;
import cash.flow.backend.models.Note;
import cash.flow.backend.models.User;
import cash.flow.backend.repositories.CategoryRepository;
import cash.flow.backend.repositories.NoteRepository;
import cash.flow.backend.repositories.UserRepository;

@SpringBootTest(properties = "spring.profiles.active=test")
@Import({ DatabaseConfig.class, UserRepository.class, NoteRepository.class, CategoryRepository.class })
public class CategoryRepositoryTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private User user;
    private Note note;
    private Category category;


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

        category = new Category();
        category.setCName("testCategory");
        category.setColor("#ffffff");
        category.setNoteFk(note.getNId());
        categoryRepository.createCategory(category);
    }

    @AfterEach
    void clearDatabase() {
        // Clean up the database after each test
        try (Connection connection = dataSource.getConnection()) {
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE;").execute();

            // Truncate all tables
            connection.prepareStatement("TRUNCATE TABLE member;").execute();
            connection.prepareStatement("TRUNCATE TABLE note;").execute();
            connection.prepareStatement("TRUNCATE TABLE category;").execute();

            // Re-enable foreign key checks
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY TRUE;").execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database", e);
        }
    }

    @Test
    void testGetCategoriesByNote() {
        // Test getting categories by note
        List<Category> categories = categoryRepository.getCategoriesByNote(note.getNId());
        assertEquals(categories.size(), 1);
    }

    @Test
    void testCreateCategory() {
        // Test creating a category
        Category newCategory = new Category();
        newCategory.setCName("newCategory");
        newCategory.setColor("#8200AE");
        newCategory.setNoteFk(note.getNId());

        boolean created = categoryRepository.createCategory(newCategory);
        assertTrue(created);
    }

    @Test
    void testUpdateCategory() {
        category = categoryRepository.getCategoriesByNote(note.getNId()).get(0);
        
        // Test updating a category
        category.setCName("updatedCategory");
        category.setColor("#000000");
        
        boolean updated = categoryRepository.updateCategory(category);
        assertTrue(updated);
        
        // Verify the update
        List<Category> categories = categoryRepository.getCategoriesByNote(note.getNId());
        Category c = categories.get(0);
        assertEquals(c.getCName(), "updatedCategory");
        assertEquals(c.getColor(), "#000000");
    }
    
    @Test
    void testDeleteCategory() {
        category = categoryRepository.getCategoriesByNote(note.getNId()).get(0);
        
        // Test deleting a category
        boolean deleted = categoryRepository.deleteCategory(category.getCId());
        assertTrue(deleted);

        // Verify the deletion
        List<Category> categories = categoryRepository.getCategoriesByNote(note.getNId());
        assertEquals(categories.size(), 0);
    }
}
