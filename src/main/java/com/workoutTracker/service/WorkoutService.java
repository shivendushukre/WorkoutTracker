package com.workoutTracker.service;

import com.workoutTracker.dto.WorkoutDTO;

import java.util.List;

public interface WorkoutService {

    WorkoutDTO createWorkout(WorkoutDTO workoutDTO);

    WorkoutDTO updateWorkout(WorkoutDTO workoutDTO, Integer id);

    void deleteWorkout(Integer id);

    List<WorkoutDTO> listWorkouts();

    String generateReport();
}
