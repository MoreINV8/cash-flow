package cash.flow.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cash.flow.backend.dto.DayCreateDTO;
import cash.flow.backend.dto.MonthCreateDTO;
import cash.flow.backend.dto.noted.DayDTO;
import cash.flow.backend.dto.noted.NotedDTO;
import cash.flow.backend.services.NoteService;

@RestController
@RequestMapping("/api/take-note")
public class TakeNoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/change")
    public ResponseEntity<?> changeMonth(@RequestParam String monthId) {
        List<DayDTO> changeData = noteService.changeMonth(monthId);
        
        return ResponseEntity.ok(changeData);
    }
    
    @GetMapping
    public ResponseEntity<?> getNoted(@RequestParam String username) {
        NotedDTO notedDTO = noteService.getNoted(username);
        if (notedDTO == null) {
            return ResponseEntity.badRequest().body("Something went wrong!");
        } else {
            return ResponseEntity.ok(notedDTO);
        }
    }

    @PostMapping("/month")
    public ResponseEntity<?> createMonth(@RequestBody MonthCreateDTO dayCreateDTO) {
        boolean isSuccess = noteService.createMonth(dayCreateDTO);
        if (isSuccess) {
            return ResponseEntity.ok("Month created successfully!");
        } else {
            return ResponseEntity.badRequest().body("Something went wrong!");
        }
    }

    @PostMapping("/day")
    public ResponseEntity<?> createDay(@RequestBody DayCreateDTO dayCreateDTO) {
        boolean isSuccess = noteService.createDay(dayCreateDTO);
        if (isSuccess) {
            return ResponseEntity.ok("Day created successfully!");
        } else {
            return ResponseEntity.badRequest().body("Something went wrong!");
        }
    }

    @PutMapping("/day")
    public ResponseEntity<?> updateDay(@RequestBody DayCreateDTO dayCreateDTO) {
        boolean isSuccess = noteService.updateDay(dayCreateDTO);
        if (isSuccess) {
            return ResponseEntity.ok("Day updated successfully!");
        } else {
            return ResponseEntity.badRequest().body("Something went wrong!");
        }
    }

    @DeleteMapping("/day")
    public ResponseEntity<?> deleteDay(@RequestParam String dayId) {
        boolean isSuccess = noteService.deleteDay(dayId);
        if (isSuccess) {
            return ResponseEntity.ok("Day deleted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Something went wrong!");
        }
    }

}
