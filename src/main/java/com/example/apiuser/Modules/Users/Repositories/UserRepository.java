package com.example.apiuser.Modules.Users.Repositories;

import com.example.apiuser.Modules.Users.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional; // Importa esto
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);

    // --- AGREGA ESTO ---
    Optional<User> findByEmail(String email);
}