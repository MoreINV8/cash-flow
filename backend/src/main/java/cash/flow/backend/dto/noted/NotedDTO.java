package cash.flow.backend.dto.noted;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class NotedDTO {
    private List<Map<String, Object>> latest_2_months;
    private List<CategoryDTO> categories;
}
