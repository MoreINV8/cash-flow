package cash.flow.backend.dto.dashboard;

import java.util.List;

import lombok.Data;

@Data
public class DashboardResponseDTO {
    private String month;
    private SummaryDTO summary;
    private List<CategoryBrakedownDTO> category_brakedown;
    private List<DailySpendDTO> daily_spending;
    private List<TopSpendRecordDTO> top_spend_records;

    public DashboardResponseDTO() {
        this.summary = new SummaryDTO();
    }
    
}
