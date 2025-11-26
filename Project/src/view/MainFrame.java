package view;

import controller.WorkoutController;
import javax.swing.*;

public class MainFrame extends JFrame {
    private WorkoutController controller;
    private WorkoutsPanel workoutsPanel;
    private StatisticsPanel statisticsPanel;
    private JTabbedPane tabs;

    public MainFrame() {
        controller = new WorkoutController();
        setupUI();
        controller.loadData();
        refreshAllPanels();
    }

    private void setupUI() {
        setTitle("Учет физических тренировок");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        workoutsPanel = new WorkoutsPanel(controller, this);
        statisticsPanel = new StatisticsPanel(controller);

        tabs = new JTabbedPane();
        tabs.addTab("Тренировки", workoutsPanel);
        tabs.addTab("Добавить", new AddWorkoutPanel(controller, this));
        tabs.addTab("Статистика", statisticsPanel);

        add(tabs);
    }

    public void switchToTab(int tabIndex) {
        tabs.setSelectedIndex(tabIndex);
    }

    public void refreshAllPanels() {
        workoutsPanel.refreshTable();
        statisticsPanel.refreshStatistics();
    }

    public void showMessage(String message) {
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(this, message);
        }
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}