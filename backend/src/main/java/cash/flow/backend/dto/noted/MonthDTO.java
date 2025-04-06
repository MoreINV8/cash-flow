package cash.flow.backend.dto.noted;

import cash.flow.backend.models.Month;
import cash.flow.backend.utils.Helper;
import lombok.Data;

@Data
public class MonthDTO {
    private String m_id;
    private int year;
    private int month;

    public MonthDTO(Month month) {
        this.m_id = Helper.getStringUUID(month.getMId());
        this.year = month.getYear();
        this.month = month.getMonth();
    }
}
