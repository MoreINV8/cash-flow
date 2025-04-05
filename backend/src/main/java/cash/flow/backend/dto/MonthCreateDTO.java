package cash.flow.backend.dto;

import cash.flow.backend.models.Month;
import cash.flow.backend.utils.Helper;
import lombok.Data;

@Data
public class MonthCreateDTO {
    private int year;
    private int month;
    private String note_id;
    
    public Month getMonth() {
        Month month = new Month();
        month.setYear(this.year);
        month.setMonth(this.month);
        month.setNoteFk(Helper.convertUUID(note_id));
        return month;
    }
}
