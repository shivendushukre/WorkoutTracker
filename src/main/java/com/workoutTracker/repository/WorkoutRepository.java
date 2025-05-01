package com.workoutTracker.repository;

import com.workoutTracker.entity.User;
import com.workoutTracker.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    List<Workout> findByUser(User user);
    List<Workout> findByUserAndIsCompletedTrue(User user);
}
