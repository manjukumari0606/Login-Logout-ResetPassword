package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/usersInfo")
public class UserController {

	@Autowired
    private UserServiceImpl userService;

  
    @GetMapping("/all")
    public List<User> getAllUsersWithRoles() {
        return userService.findAllUsersWithRoles();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestBody User user) {
        if (user.getRoles() != null) {
            userService.updateUserRole(userId, user.getRoles());
            return ResponseEntity.ok("Role updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role parameter is missing");
        }
    }

}
