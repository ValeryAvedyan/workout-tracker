package view;

import controller.WorkoutController;
import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private WorkoutController controller;
    private JTextArea statsArea;

    public StatisticsPanel(WorkoutController controller) {
        this.controller = controller;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("История рекордов (топ-3)", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        statsArea = new JTextArea();
        statsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        statsArea.setEditable(false);
        statsArea.setBackground(new Color(250, 250, 250));

        JButton updateBtn = createColoredButton("Обновить статистику", new Color(150, 100, 200));
        updateBtn.addActionListener(e -> refreshStatistics());

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(statsArea), BorderLayout.CENTER);
        add(updateBtn, BorderLayout.SOUTH);
    }

    private JButton createColoredButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }

    public void refreshStatistics() {
        String stats = controller.getStatistics();
        statsArea.setText(stats);
    }
}