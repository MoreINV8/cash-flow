package cash.flow.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cash.flow.backend.dto.CategoryCreateDTO;
import cash.flow.backend.dto.noted.CategoryDTO;
import cash.flow.backend.services.CategoryService;

@RestController
@RequestMapping("/api/category-manage")
public class CategoryManageController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getCategories(@RequestParam String username) {
        List<CategoryDTO> result = categoryService.getCategories(username);

        if (result == null) {
            return ResponseEntity.badRequest().body("Something went wrong!");
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO,
            @RequestParam String username) {
        boolean isSuccess = categoryService.createCategory(categoryCreateDTO.getCategory(), username);
        if (isSuccess) {
            return ResponseEntity.ok("Category created successfully!");
        } else {
            return ResponseEntity.badRequest().body("Something went wrong!");
        }
    }
    
    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        boolean isSuccess = categoryService.updateCategory(categoryCreateDTO.getCategory());
        if (isSuccess) {
            return ResponseEntity.ok("Category updated successfully!");
        } else {
            return ResponseEntity.badRequest().body("Something went wrong!");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategory(@RequestParam String categoryId) {
        boolean isSuccess = categoryService.deleteCategory(categoryId);
        if (isSuccess) {
            return ResponseEntity.ok("Category deleted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Something went wrong!");
        }
    }
    
}
