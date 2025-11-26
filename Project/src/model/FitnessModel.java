package model;

import java.util.*;

public class FitnessModel {
    private List<WorkoutRecord> workoutRecords;
    private Map<String, List<WorkoutRecord>> exerciseRecords;

    public FitnessModel() {
        workoutRecords = new ArrayList<>();
        exerciseRecords = new HashMap<>();
    }

    public void addWorkoutRecord(WorkoutRecord record) {
        workoutRecords.add(record);
        updateExerciseRecords(record);
    }

    public void removeWorkoutRecord(int index) {
        if (index >= 0 && index < workoutRecords.size()) {
            WorkoutRecord removedRecord = workoutRecords.remove(index);
            rebuildAllExerciseRecords();
        }
    }

    public List<WorkoutRecord> getWorkoutRecords() {
        return workoutRecords;
    }

    public int getTotalWorkouts() {
        return workoutRecords.size();
    }

    public double getMaxWeight(String exercise) {
        double max = 0;
        for (WorkoutRecord record : workoutRecords) {
            if (record.getExerciseName().equals(exercise) && record.getWeight() > max) {
                max = record.getWeight();
            }
        }
        return max;
    }

    public List<WorkoutRecord> getLastThreeRecords(String exercise) {
        return exerciseRecords.getOrDefault(exercise, new ArrayList<>());
    }

    private void updateExerciseRecords(WorkoutRecord newRecord) {
        String exercise = newRecord.getExerciseName();
        List<WorkoutRecord> records = exerciseRecords.getOrDefault(exercise, new ArrayList<>());

        records.add(newRecord);
        records.sort((r1, r2) -> Double.compare(r2.getWeight(), r1.getWeight()));

        if (records.size() > 3) {
            records = new ArrayList<>(records.subList(0, 3));
        }

        exerciseRecords.put(exercise, records);
    }

    private void rebuildAllExerciseRecords() {
        exerciseRecords.clear();
        for (WorkoutRecord record : workoutRecords) {
            updateExerciseRecords(record);
        }
    }
}