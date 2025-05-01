package com.workoutTracker.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkoutDTO {

    private Integer id;

    private String workoutTitle;

    private String workoutType;

    private String workoutDuration;

    private String workoutCategory;

    private boolean isCompleted;

    private List<ExerciseDTO> exerciseList;
}
