package com.math.MathLearningWebsite.Controller;

import com.math.MathLearningWebsite.Services.previleges.admin.AdminService;
import com.math.MathLearningWebsite.enumerations.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/lock-account")
    public ResponseEntity<String> lockUserAccount(@RequestParam String email) {
        String result = adminService.lockUserAccount(email);
        if (result.equals("Success")) {
            return ResponseEntity.ok("User account locked successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }

    @PostMapping("/unlock-account")
    public ResponseEntity<String> unlockUserAccount(@RequestParam String email) {
        String result = adminService.unlockUserAccount(email);
        if (result.equals("Success")) {
            return ResponseEntity.ok("User account unlocked successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }

    @PostMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestParam String email) {
        String result = adminService.deleteAccount(email);
        if (result.equals("Success")) {
            return ResponseEntity.ok("User account deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @PostMapping("/update-role")
    public ResponseEntity<String> updateRole(@RequestParam String email, @RequestParam Role role) {
        String result = adminService.updateRole(email, role);
        if (result.equals("Success")) {
            return ResponseEntity.ok("User role updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }
}
