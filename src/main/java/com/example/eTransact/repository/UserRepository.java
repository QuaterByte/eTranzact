package com.example.eTransact.repository;

import com.example.eTransact.Model.Account;
import com.example.eTransact.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);


}
