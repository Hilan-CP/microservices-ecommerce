package com.hdev.ecommerce.controller;

import com.hdev.ecommerce.dto.UserDTO;
import com.hdev.ecommerce.dto.UserRequestDTO;
import com.hdev.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    private ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequestDTO requestDTO){
        UserDTO user = service.createUser(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(user.getId());
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO requestDTO){
        UserDTO user = service.updateUser(id, requestDTO);
        return ResponseEntity.ok(user);
    }
}
