package com.project.bmr.User_Service.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bmr.User_Service.entity.User;



public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);
}