package com.workoutTracker.entity;

import com.workoutTracker.model.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Category name;

    private int sets;

    private int reps;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
}
