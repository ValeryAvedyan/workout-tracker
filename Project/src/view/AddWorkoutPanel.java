package view;

import controller.WorkoutController;
import javax.swing.*;
import java.awt.*;

public class AddWorkoutPanel extends JPanel {
    private WorkoutController controller;
    private MainFrame mainFrame;

    public AddWorkoutPanel(WorkoutController controller, MainFrame mainFrame) {
        this.controller = controller;
        this.mainFrame = mainFrame;
        setupUI();
    }

    private void setupUI() {
        setLayout(new GridLayout(5, 2, 10, 10));

        JComboBox<String> exerciseBox = new JComboBox<>(controller.getAvailableExercises());
        JTextField setsField = new JTextField("3");
        JTextField repsField = new JTextField("10");
        JTextField weightField = new JTextField("0");

        add(new JLabel("Упражнение:"));
        add(exerciseBox);
        add(new JLabel("Подходы:"));
        add(setsField);
        add(new JLabel("Повторения:"));
        add(repsField);
        add(new JLabel("Вес (кг):"));
        add(weightField);

        JButton addBtn = createColoredButton("Добавить", new Color(100, 160, 100));
        addBtn.addActionListener(e -> {
            String result = controller.addWorkout(
                    (String) exerciseBox.getSelectedItem(),
                    setsField.getText(),
                    repsField.getText(),
                    weightField.getText()
            );

            mainFrame.showMessage(result);
            if (result.equals("Запись добавлена!")) {
                setsField.setText("3");
                repsField.setText("10");
                weightField.setText("0");
                mainFrame.refreshAllPanels();
            }
        });

        add(new JLabel());
        add(addBtn);
    }

    private JButton createColoredButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }
}