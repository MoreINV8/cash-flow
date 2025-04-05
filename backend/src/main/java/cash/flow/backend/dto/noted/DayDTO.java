package cash.flow.backend.dto.noted;

import java.sql.Date;

import cash.flow.backend.models.Day;
import cash.flow.backend.utils.Helper;
import lombok.Data;

@Data
public class DayDTO {
    private String d_id;
    private Date date;
    private String detail;
    private int transaction_value;
    private String note;
    private String category_name;
    private String category_color;

    public DayDTO(Day day) {
        this.d_id = Helper.getStringUUID(day.getDId());
        this.detail = day.getDetail();
        this.transaction_value = day.getTransactionValue();
        this.note = day.getNote();
        this.date = day.getDate();
        this.category_name = day.getCategoryName();
        this.category_color = day.getCategoryColor();
    }
}
