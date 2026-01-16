package com.example.apiuser.Modules.Users.Repositories;

import com.example.apiuser.Modules.Users.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

}