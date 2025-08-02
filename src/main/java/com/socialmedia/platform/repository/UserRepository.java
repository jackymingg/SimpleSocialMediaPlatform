package com.socialmedia.platform.repository;

import com.socialmedia.platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhone(String phone);

    boolean existsByPhone(String phone);

    @Procedure(procedureName = "sp_register_user")
    void registerUser(String phone, String email, String password, String userName);
}



