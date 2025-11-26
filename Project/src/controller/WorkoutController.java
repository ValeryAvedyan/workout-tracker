package controller;

import model.*;
import util.FileHandler;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WorkoutController {
    private FitnessModel model;
    private static final String FILE_PATH = "src/data/workouts.csv";

    private final String[] EXERCISES = {
            "Жим штанги лежа", "Жим гантелей лежа", "Отжимания",
            "Становая тяга", "Тяга штанги в наклоне", "Подтягивания",
            "Приседания со штангой", "Жим ногами", "Выпады",
            "Жим штанги стоя", "Подъем штанги на бицепс", "Французский жим"
    };

    public WorkoutController() {
        model = new FitnessModel();
    }

    public String addWorkout(String exercise, String setsText, String repsText, String weightText) {
        try {
            int sets = Integer.parseInt(setsText);
            int reps = Integer.parseInt(repsText);
            double weight = Double.parseDouble(weightText);
            String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

            if (sets <= 0 || reps <= 0 || weight < 0) {
                return "Проверьте правильность введенных данных!";
            }

            WorkoutRecord record = new WorkoutRecord(exercise, sets, reps, weight, date);
            model.addWorkoutRecord(record);
            return "Запись добавлена!";
        } catch (Exception ex) {
            return "Ошибка в данных!";
        }
    }

    public String deleteWorkout(int rowIndex) {
        if (rowIndex == -1) {
            return "Выберите запись для удаления";
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Удалить запись?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            model.removeWorkoutRecord(rowIndex);
            return "Запись удалена!";
        }
        return "";
    }

    public String saveData() {
        FileHandler.saveToFile(model.getWorkoutRecords(), FILE_PATH);
        return "Данные сохранены!";
    }

    public String loadData() {
        FileHandler.loadFromFile(model.getWorkoutRecords(), FILE_PATH);
        return "Данные загружены!";
    }

    public String getStatistics() {
        StringBuilder stats = new StringBuilder();
        boolean hasData = false;

        for (String exercise : EXERCISES) {
            List<WorkoutRecord> lastRecords = model.getLastThreeRecords(exercise);

            if (!lastRecords.isEmpty()) {
                stats.append(exercise).append(":\n");

                for (int i = 0; i < lastRecords.size(); i++) {
                    WorkoutRecord record = lastRecords.get(i);
                    String position = (i + 1) + ". ";

                    stats.append("  ").append(position)
                            .append(record.getWeight()).append(" кг")
                            .append(" (").append(record.getDate()).append(")\n");
                }
                stats.append("\n");
                hasData = true;
            }
        }

        if (!hasData) {
            stats.append("Нет данных о тренировках\n");
            stats.append("Добавьте тренировки во вкладке 'Добавить'");
        }

        return stats.toString();
    }

    public List<WorkoutRecord> getAllWorkouts() {
        return model.getWorkoutRecords();
    }

    public String[] getAvailableExercises() {
        return EXERCISES;
    }

    public List<WorkoutRecord> getExerciseHistory(String exercise) {
        return model.getLastThreeRecords(exercise);
    }
}