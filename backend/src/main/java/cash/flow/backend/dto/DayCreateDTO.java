package cash.flow.backend.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import cash.flow.backend.models.Day;
import cash.flow.backend.utils.Helper;
import lombok.Data;

@Data
public class DayCreateDTO {
    private String d_id;
    private Date date;
    private String detail;
    private int transaction_value;
    private String note;
    private String category_id;
    private String month_id;

    public Day getDay() {
        Day day = new Day();
        day.setDId(Helper.convertUUID(d_id));
        day.setDetail(detail);
        day.setTransactionValue(transaction_value);
        day.setNote(note);
        day.setCategoryFk(Helper.convertUUID(category_id));
        day.setMonthFk(Helper.convertUUID(month_id));
        day.setDate(date);

        System.out.println("DayCreateDTO.getDId() -> " + d_id);
        System.out.println("DayCreateDTO.getDay() -> " + day);

        return day;
    }
}
