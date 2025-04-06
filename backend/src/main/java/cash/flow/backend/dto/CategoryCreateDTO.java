package cash.flow.backend.dto;

import cash.flow.backend.models.Category;
import cash.flow.backend.utils.Helper;
import lombok.Data;

@Data
public class CategoryCreateDTO {
    private String c_id;
    private String c_name;
    private String color;

    public Category getCategory() {
        Category category = new Category();
        category.setCId(Helper.convertUUID(c_id));
        category.setCName(c_name);
        category.setColor(color);

        return category;
    }
}
