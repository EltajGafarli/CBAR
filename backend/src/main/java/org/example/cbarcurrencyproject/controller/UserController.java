package org.example.cbarcurrencyproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.cbarcurrencyproject.dto.UserDto;
import org.example.cbarcurrencyproject.dto.UserRequestDto;
import org.example.cbarcurrencyproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.getCurrentUser(userDetails)
                );
    }


    @PutMapping
    public ResponseEntity<UserDto> updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity
                .ok(
                        userService.updateUser(userDetails, userRequestDto)
                );
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.deleteUser(userDetails)
                );
    }



}