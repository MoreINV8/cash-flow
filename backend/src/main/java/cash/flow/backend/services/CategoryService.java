package cash.flow.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cash.flow.backend.dto.noted.CategoryDTO;
import cash.flow.backend.models.Category;
import cash.flow.backend.models.Note;
import cash.flow.backend.repositories.CategoryRepository;
import cash.flow.backend.repositories.NoteRepository;
import cash.flow.backend.utils.Helper;

@Service
public class CategoryService {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getCategories(String username) {
        Note note = noteRepository.getNoteByUsername(username);

        if (note != null) {
            List<Category> categories = categoryRepository.getCategoriesByNote(note.getNId());
            List<CategoryDTO> categoryDTOs = categories.stream()
                    .map(category -> new CategoryDTO(category))
                    .toList();

            return categoryDTOs;
        } else {
            return null;
        }
    }

    public boolean createCategory(Category category, String username) {
        Note note = noteRepository.getNoteByUsername(username);
        if (note != null) {
            category.setNoteFk(note.getNId());
            return categoryRepository.createCategory(category);
        } else {
            return false;
        }
    }

    public boolean updateCategory(Category category) {
        return categoryRepository.updateCategory(category);
    }

    public boolean deleteCategory(String categoryId) {
        return categoryRepository.deleteCategory(Helper.convertUUID(categoryId));
    }

}
