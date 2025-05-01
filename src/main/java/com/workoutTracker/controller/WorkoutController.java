package com.workoutTracker.controller;

import com.workoutTracker.dto.WorkoutDTO;
import com.workoutTracker.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<WorkoutDTO> createWorkout(@RequestBody WorkoutDTO workoutDTO) {
        return ResponseEntity.ok(this.workoutService.createWorkout(workoutDTO));
    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutDTO> updateWorkout(@RequestBody WorkoutDTO workoutDTO, @PathVariable Integer workoutId){
        return ResponseEntity.ok(this.workoutService.updateWorkout(workoutDTO, workoutId));
    }

    @DeleteMapping("/{workoutId}")
    public void deleteWorkout(@PathVariable Integer workoutId) {
        this.workoutService.deleteWorkout(workoutId);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutDTO>> getListOfWorkouts() {
        return ResponseEntity.ok(this.workoutService.listWorkouts());
    }

    @GetMapping("/report")
    public ResponseEntity<String> generateReport() {
        return ResponseEntity.ok(this.workoutService.generateReport());
    }
}
