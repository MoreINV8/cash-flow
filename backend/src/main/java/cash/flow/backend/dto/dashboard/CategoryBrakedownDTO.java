package cash.flow.backend.dto.dashboard;

import lombok.Data;

@Data
public class CategoryBrakedownDTO {
    private String category;
    private String color;
    private double total;
    private double percentage;
    private int count;

    public CategoryBrakedownDTO(String category, String color) {
        this.category = category;
        this.color = color;
        this.total = 0.0;
        this.count = 0;
    }

    public void addTotalSpending(double spend) {
        this.total += spend;
        this.count++;
    }
}
