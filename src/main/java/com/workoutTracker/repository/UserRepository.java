package com.workoutTracker.repository;

import com.workoutTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByEmail(User user);
    Optional<User> findByUsername(String username);
}
