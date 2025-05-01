package com.workoutTracker.dto;

import com.workoutTracker.model.Category;
import lombok.Data;

@Data
public class ExerciseDTO {

    private Integer id;

    private Category name;

    private int sets;

    private int reps;
}
