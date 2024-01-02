package com.math.MathLearningWebsite.Controller;

import com.math.MathLearningWebsite.Services.previleges.superAdmin.SuperAdminService;
import com.math.MathLearningWebsite.enumerations.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/super/admin/api/v1")
public class SuperAdminController {
    @Autowired
    private final SuperAdminService superAdminService;
    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestParam("email") String email){
        String response = superAdminService.addAdmin(email);
        switch (response){
            case "Admin added" -> {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            case  "User not found" -> {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    @PostMapping("/unlockAdmin")
    public ResponseEntity<String> unlockAdmin(@RequestParam("email") String email){
        String response = superAdminService.unLockAdmin(email);
        switch (response){
            case "Admin locked successfully." -> {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            case "User not found" ->{
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            default -> {
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }
    }
    @PostMapping("/lockAdmin")
    public ResponseEntity<String> lockAdmin(@RequestParam("email") String email){
        String response = superAdminService.lockAdmin(email);
        switch (response){
            case "Admin locked successfully." -> {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            case "User not found" ->{
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            default -> {
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }
    }

    @PostMapping("/updateAdmin")
    public ResponseEntity<String> updateUserRole(@RequestParam("role") Role role, @RequestParam("email") String email) {
        String response = superAdminService.updateUserRole(role,email);
        if (response.equals("Success")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}