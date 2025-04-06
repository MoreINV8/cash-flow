package cash.flow.backend.dto.dashboard;

import java.sql.Date;

import cash.flow.backend.models.Day;
import lombok.Data;

@Data
public class TopSpendRecordDTO {
    private double amount;
    private String category;
    private String label;
    private Date date;

    public TopSpendRecordDTO(Day day) {
        this.amount = day.getTransactionValue();
        this.category = day.getCategoryName();
        this.label = day.getDetail();
        this.date = day.getDate();
    }
}
