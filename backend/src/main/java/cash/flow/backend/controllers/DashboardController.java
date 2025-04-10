package cash.flow.backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cash.flow.backend.services.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping
    public ResponseEntity<?> dashboard(@RequestParam String username) {
        List<Map<String, Object>> response = dashboardService.getInitDashboard(username);
        if (response == null) {
            return ResponseEntity.badRequest().body("Something went wrong!");
        } else {
            return ResponseEntity.ok(response);
        }
    }
    
}
