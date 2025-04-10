package cash.flow.backend.dto.dashboard;

import lombok.Data;

@Data
public class SummaryDTO {
    private double total_spending = 0.0;
    private double average_daily_spending = 0;
    private int total_days = 0;
    private int total_transactions = 0;

    public void addTotalSpending(double amount) {
        this.total_spending += amount;
    }

    public void incrementTotalTransactions() {
        this.total_transactions++;
    }

    public void setAverageDailySpending() {
        if (this.total_days == 0) {
            this.average_daily_spending = 0;
            return;
        }
        
        this.average_daily_spending = this.total_spending / this.total_days;
    }
}
