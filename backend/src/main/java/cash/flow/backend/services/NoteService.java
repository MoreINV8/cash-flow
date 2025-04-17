package cash.flow.backend.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cash.flow.backend.dto.DayCreateDTO;
import cash.flow.backend.dto.MonthCreateDTO;
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
        
        List<Map<String, Object>> response = new ArrayList<>();

        for (var m : months) {
            Map<String, Object> item = new HashMap<>();

            List<DayDTO> days = dayRepository.getDaysByMonth(m.getMId()).stream().map(day -> new DayDTO(day)).toList();

            item.put("month", new MonthDTO(m));
            item.put("days", days);

            response.add(item);
        }

        List<Category> categories = categoryRepository.getCategoriesByNote(note.getNId());

        return getNotedDTO(response, categories);
    }

    private NotedDTO getNotedDTO(List<Map<String, Object>> lastest2Month, List<Category> categories) {

        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO(category);

            return categoryDTO;
        }).toList();

        NotedDTO notedDTO = new NotedDTO();
        notedDTO.setLatest_2_months(lastest2Month);
        notedDTO.setCategories(categoryDTOs);
        return notedDTO;
    }

    public boolean createMonth(MonthCreateDTO monthCreateDTO) {
        Month request = monthCreateDTO.getMonth();
        Month month = monthRepository
                .getMonthByNIdYearMonth(request.getNoteFk(), request.getYear(),
                        request.getMonth());
        if (month != null) {
            return false;
        }

        return monthRepository.createMonth(request);
    }

    public List<DayDTO> changeMonth(String monthId) {
        List<DayDTO> days = new ArrayList<>();

        for (Day day : dayRepository.getDaysByMonth(Helper.convertUUID(monthId))) {
            DayDTO dayDTO = new DayDTO(day);
            days.add(dayDTO);
        }
        
        return days;
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
