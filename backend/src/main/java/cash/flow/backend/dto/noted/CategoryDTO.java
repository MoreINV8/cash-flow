package cash.flow.backend.dto.noted;

import cash.flow.backend.models.Category;
import cash.flow.backend.utils.Helper;
import lombok.Data;

@Data
public class CategoryDTO {
    private String c_id;
    private String c_name;
    private String color;

    public CategoryDTO(Category category) {
        this.c_id = Helper.getStringUUID(category.getCId());
        this.c_name = category.getCName();
        this.color = category.getColor();
    }
}
