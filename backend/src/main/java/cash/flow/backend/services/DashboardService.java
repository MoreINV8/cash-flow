package cash.flow.backend.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cash.flow.backend.dto.dashboard.CategoryBrakedownDTO;
import cash.flow.backend.dto.dashboard.DailySpendDTO;
import cash.flow.backend.dto.dashboard.DashboardResponseDTO;
import cash.flow.backend.dto.dashboard.TopSpendRecordDTO;
import cash.flow.backend.dto.noted.MonthDTO;
import cash.flow.backend.models.Category;
import cash.flow.backend.models.Day;
import cash.flow.backend.models.Month;
import cash.flow.backend.models.Note;
import cash.flow.backend.repositories.CategoryRepository;
import cash.flow.backend.repositories.DayRepository;
import cash.flow.backend.repositories.MonthRepository;
import cash.flow.backend.repositories.NoteRepository;
import cash.flow.backend.utils.Helper;

@Service
public class DashboardService {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Map<String, ?> getInitDashboard(String username) {
        Note note = noteRepository.getNoteByUsername(username);

        if (note == null) {
            return null;
        }

        List<Month> months = monthRepository.getMonthByNId(note.getNId(), 10);

        if (months.isEmpty()) {
            return null;
        }

        Month latestMonth = months.get(0);

        Map<String, Object> response = new HashMap<>();
        response.put("noted_month", months.stream().map(month -> new MonthDTO(month)).toList());
        response.put("latest_month_dashboard", getDashboardData(latestMonth, note.getNId()));

        return response;
    }

    private DashboardResponseDTO getDashboardData(Month month, UUID noteId) {
        List<Day> days = dayRepository.getDaysByMonth(month.getMId());

        DashboardResponseDTO response = getSummary(days);

        Map<String, CategoryBrakedownDTO> categorizedDays = new HashMap<>();
        categorizedDays
                .put("uncategorize", new CategoryBrakedownDTO("uncategorize", null));
        
        for (Category category : categoryRepository.getCategoriesByNote(noteId)) {
            categorizedDays.put(Helper.getStringUUID(category.getCId()),
                    new CategoryBrakedownDTO(category.getCName(), category.getColor()));
        }

        for (Day day : days) {
            if (day.getCategoryFk() == null) {
                categorizedDays.get("uncategorize").addTotalSpending(day.getTransactionValue());

            } else {
                String key = Helper.getStringUUID(day.getCategoryFk());
                categorizedDays.get(key).addTotalSpending(day.getTransactionValue());

            }
        }
        response.setCategory_brakedown(categorizedDays.values().stream().toList());

        List<TopSpendRecordDTO> top5Spending = new ArrayList<>();
        for (int i = 0; i < days.size() && i < 5; i++) {
            top5Spending.add(new TopSpendRecordDTO(days.get(i)));
        }
        response.setTop_spend_records(top5Spending);
        response.percentCalculate();

        response.setMonth(month.getYear() + "-" + month.getMonth());

        return response;
    }

    private DashboardResponseDTO getSummary(List<Day> days) {
        DashboardResponseDTO response = new DashboardResponseDTO();

        HashMap<String, DailySpendDTO> dailySpend = new HashMap<>();

        for (Day day : days) {
            response.getSummary().addTotalSpending(day.getTransactionValue());
            response.getSummary().incrementTotalTransactions();

            String key = day.getDate().toString();

            if (dailySpend.containsKey(key)) {
                dailySpend.get(key).addTotalSpending(day.getTransactionValue());
            } else {
                dailySpend.put(key, new DailySpendDTO(day));
            }
        }

        response.getSummary().setTotal_days(dailySpend.size());
        response.getSummary().setAverageDailySpending();

        response.setDaily_spending(dailySpend.values().stream().toList());

        return response;
    }

    public DashboardResponseDTO changeDashboard(String monthId, String username) {
        Note note = noteRepository.getNoteByUsername(username);
        Month month = monthRepository.getMonthByMId(Helper.convertUUID(monthId));

        if (month == null || note == null) {
            return null;
        }

        return getDashboardData(month, note.getNId());
    }

}
