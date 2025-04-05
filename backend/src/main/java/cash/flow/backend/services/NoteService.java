package cash.flow.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cash.flow.backend.dto.DayCreateDTO;
import cash.flow.backend.dto.noted.CategoryDTO;
import cash.flow.backend.dto.noted.DayDTO;
import cash.flow.backend.dto.noted.MonthDTO;
import cash.flow.backend.dto.noted.NotedDTO;
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
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public NotedDTO getNoted(String username) {
        Note note = noteRepository.getNoteByUsername(username);

        if (note == null) {
            createNote(username);
            return null;
        }

        List<Month> months = monthRepository.getMonthByNId(note.getNId(), 2);
        List<Day> days = new ArrayList<>();
        if (!months.isEmpty()) {
            Month latestMonth = months.get(0);
            System.out.println("Latest Month: " + latestMonth);
            days = dayRepository.getDaysByMonth(latestMonth.getMId());

            System.out.println("Days: " + days);
        }

        List<Category> categories = categoryRepository.getCategoriesByNote(note.getNId());

        return getNotedDTO(months, days, categories);
    }

    private NotedDTO getNotedDTO(List<Month> months, List<Day> days, List<Category> categories) {
        List<MonthDTO> monthDTOs = months.stream().map(month -> {
            MonthDTO monthDTO = new MonthDTO(month);

            return monthDTO;
        }).toList();

        List<DayDTO> dayDTOs = days.stream().map(day -> {
            DayDTO dayDTO = new DayDTO(day);

            return dayDTO;
        }).toList();

        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO(category);

            return categoryDTO;
        }).toList();

        NotedDTO notedDTO = new NotedDTO();
        notedDTO.setLatest_2_months(monthDTOs);
        notedDTO.setLatest_month_days(dayDTOs);
        notedDTO.setCategories(categoryDTOs);
        return notedDTO;
    }

    public boolean createNote(String username) {
        return noteRepository.createNote(username);
    }

    public boolean createDay(DayCreateDTO dayCreateDTO) {
        return dayRepository.createDay(dayCreateDTO.getDay());
    }

    public boolean updateDay(DayCreateDTO dayCreateDTO) {
        return dayRepository.updateDay(dayCreateDTO.getDay());
    }

    public boolean deleteDay(String dayId) {
        return dayRepository.deleteDay(Helper.convertUUID(dayId));
    }

}
