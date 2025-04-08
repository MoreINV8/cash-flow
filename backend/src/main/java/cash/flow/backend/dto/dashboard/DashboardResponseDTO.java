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

    public void percentCalculate() {
        for (var category : this.category_brakedown) {
            double percent = category.getTotal() * 100 / this.summary.getTotal_spending();

            category.setPercentage(percent);
        }
    }
    
}
