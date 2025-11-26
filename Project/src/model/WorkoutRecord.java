package model;

public class WorkoutRecord {
    private String exerciseName;
    private int sets;
    private int reps;
    private double weight;
    private String date;

    public WorkoutRecord(String exerciseName, int sets, int reps, double weight, String date) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }

    public boolean isHeavierThan(WorkoutRecord other) {
        return this.weight > other.weight;
    }
}