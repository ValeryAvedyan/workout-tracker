package util;

import model.WorkoutRecord;
import java.io.*;
import java.util.List;

public class FileHandler {
    public static void saveToFile(List<WorkoutRecord> records, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (WorkoutRecord record : records) {
                writer.println(record.getExerciseName() + "," +
                        record.getSets() + "," +
                        record.getReps() + "," +
                        record.getWeight() + "," +
                        record.getDate());
            }
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    public static void loadFromFile(List<WorkoutRecord> records, String filename) {
        records.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    WorkoutRecord record = new WorkoutRecord(
                            parts[0],
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3]),
                            parts[4]
                    );
                    records.add(record);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }
    }
}