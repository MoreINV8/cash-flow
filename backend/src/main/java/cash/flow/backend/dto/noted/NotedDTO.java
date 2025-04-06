package cash.flow.backend.dto.noted;

import java.util.List;

import lombok.Data;

@Data
public class NotedDTO {
    private List<DayDTO> latest_month_days;
    private List<MonthDTO> latest_2_months;
    private List<CategoryDTO> categories;
}
