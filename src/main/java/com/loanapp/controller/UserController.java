package com.loanapp.controller;

import com.loanapp.dto.DefaultResponse;
import com.loanapp.dto.UserDto;
import com.loanapp.dto.UserRequest;
import com.loanapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/2/25
 * @email : TiamiyuKehinde5@gmail.com
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Create a new user",
            description = "Create a new user")
    @PostMapping()
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.create(userRequest));
    }

    @Operation(
            summary = "Update a user",
            description = "Update a user")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserRequest userRequest, @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.update(userRequest,id));
    }

    @Operation(
            summary = "Get a user",
            description = "Get a user")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Operation(
            summary = "Get all users",
            description = "Get all users")
    @GetMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = "Delete a user",
            description = "Delete a user")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public DefaultResponse deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
