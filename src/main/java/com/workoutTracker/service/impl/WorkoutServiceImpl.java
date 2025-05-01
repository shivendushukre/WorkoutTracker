package com.workoutTracker.service.impl;

import com.workoutTracker.dto.WorkoutDTO;
import com.workoutTracker.entity.Exercise;
import com.workoutTracker.entity.User;
import com.workoutTracker.entity.Workout;
import com.workoutTracker.repository.UserRepository;
import com.workoutTracker.repository.WorkoutRepository;
import com.workoutTracker.service.WorkoutService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WorkoutDTO createWorkout(WorkoutDTO workoutDTO) {
        User user = getCurrentUser();
        Workout workout = dtoToWorkout(workoutDTO);
        workout.setUser(user);
        return workoutToDTO(workoutRepository.save(workout));
    }

    @Override
    public WorkoutDTO updateWorkout(WorkoutDTO workoutDTO, Integer id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found.."));
        updateWorkoutFromDTO(workout, workoutDTO);
        return workoutToDTO(workoutRepository.save(workout));
    }

    @Override
    public void deleteWorkout(Integer id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public List<WorkoutDTO> listWorkouts() {
        User user = getCurrentUser();
        List<Workout> workoutList = workoutRepository.findByUser(user);
        return workoutList.stream().map(this::workoutToDTO).toList();
    }

    @Override
    public String generateReport() {
        User user = getCurrentUser();
        List<Workout> workoutList = workoutRepository.findByUserAndIsCompletedTrue(user);

        StringBuilder report = new StringBuilder();
        report.append("Workout Report for ").append(user.getUsername()).append("\n\n");

        if (workoutList.isEmpty()) {
            report.append("No completed workouts found.");
        } else {
            report.append("Total completed workouts: ").append(workoutList.size()).append("\n\n");

            for (Workout workout : workoutList) {
                report.append("Workout: ").append(workout.getWorkoutTitle()).append("\n");
                report.append("Type: ").append(workout.getWorkoutType()).append("\n");
                report.append("Duration: ").append(workout.getWorkoutDuration()).append("\n");
                report.append("Exercises:\n");

                for (Exercise exercise : workout.getExerciseList()) {
                    report.append("  - ").append(exercise.getName())
                            .append(": ").append(exercise.getWorkout())
                            .append(" sets, ").append(exercise.getSets())
                            .append(" reps, ").append(exercise.getReps())
                            .append(" kg\n");
                }

                report.append("\n");
            }
        }

        return report.toString();
    }

    private Workout dtoToWorkout(WorkoutDTO workoutDTO) {
        return this.modelMapper.map(workoutDTO, Workout.class);
    }

    private WorkoutDTO workoutToDTO(Workout workout) {
        return this.modelMapper.map(workout, WorkoutDTO.class);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        throw new RuntimeException("Unexpected authentication principal type");
    }

    private void updateWorkoutFromDTO(Workout workout, WorkoutDTO workoutDTO) {
        workout.setWorkoutTitle(workoutDTO.getWorkoutTitle());
        workout.setWorkoutCategory(workoutDTO.getWorkoutCategory());
        workout.setWorkoutType(workoutDTO.getWorkoutType());
        workout.setWorkoutDuration(workoutDTO.getWorkoutDuration());
        workout.setCompleted(workoutDTO.isCompleted());

        if (!workoutDTO.getExerciseList().isEmpty()) {

            // empty current exercise list
            workout.getExerciseList().clear();

            // add new exercise list coming from DTO/payload
            List<Exercise> exerciseList = workoutDTO.getExerciseList()
                    .stream().map((exerciseDTO -> this.modelMapper.map(exerciseDTO, Exercise.class)))
                    .toList();
            workout.getExerciseList().addAll(exerciseList);

            // set the workout reference for each exercise
            exerciseList.forEach(exercise -> exercise.setWorkout(workout));
        }
    }
}
