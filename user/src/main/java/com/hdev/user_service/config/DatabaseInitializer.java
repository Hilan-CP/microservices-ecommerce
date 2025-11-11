package com.hdev.user_service.config;

import com.hdev.user_service.entity.Address;
import com.hdev.user_service.entity.User;
import com.hdev.user_service.entity.UserRole;
import com.hdev.user_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class DatabaseInitializer {

    @Autowired
    private UserRepository repository;

    @PostConstruct
    public void initialize(){
        repository.deleteAll();
        Address a1 = new Address("691388fad21409a954c87fc7", "Cassia Street", "Maringá", "Paraná", "Brazil", "87191000");
        Address a2 = new Address("691388fad21409a954c87fc8", "Mauá Avenue", "Maringá", "Paraná", "Brazil", "87191000");
        User u1 = new User("691388fad21409a954c87fc9", "Vitor", "Hugo", "vitor.hugo@email.com", "44987654321", UserRole.ADMIN, a1, Instant.parse("2025-10-01T12:00:00Z"), null);
        User u2 = new User("691388fad21409a954c87fca", "Alexandre", "Tavares", "ale.tavares@email.com", "44912345678", UserRole.CUSTOMER, a2, Instant.parse("2025-10-01T12:00:00Z"), null);
        repository.save(u1);
        repository.save(u2);
    }
}
