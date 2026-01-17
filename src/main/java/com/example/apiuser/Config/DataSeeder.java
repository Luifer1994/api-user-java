package com.example.apiuser.Config;

import com.example.apiuser.Modules.Users.Models.User;
import com.example.apiuser.Modules.Users.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    public static final UUID SUPER_ADMIN_ID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

    @Override
    @Transactional
    public void run(String... args) {
        String adminEmail = "almendralesluifer@gmail.com";

        // Usamos existsByEmail para evitar cargar la entidad antes de tiempo
        if (!userRepository.existsByEmail(adminEmail)) {
            User superAdmin = User.builder()
                    .id(SUPER_ADMIN_ID)
                    .name("Super Admin")
                    .email(adminEmail)
                    .build();

            try {
                userRepository.saveAndFlush(superAdmin);
                System.out.println("✅ SUPER ADMIN USER CREADO (ID: " + SUPER_ADMIN_ID + ")");
            } catch (Exception e) {
                System.err.println("⚠️ El admin ya existía o hubo un choque de transacción: " + e.getMessage());
            }
        }
    }
}