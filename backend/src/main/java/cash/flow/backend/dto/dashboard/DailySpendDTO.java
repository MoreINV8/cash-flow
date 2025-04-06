package cash.flow.backend.dto.dashboard;

import java.sql.Date;

import cash.flow.backend.models.Day;
import lombok.Data;

@Data
public class DailySpendDTO {
    private Date date;
    private double spending;
    private int total_items;

    public DailySpendDTO(Day day) {
        this.date = day.getDate();
        this.spending = day.getTransactionValue();
        this.total_items = 0;
    }

    public void addTotalSpending(double transactionValue) {
        this.spending += transactionValue;
        this.total_items++;
    }
}
