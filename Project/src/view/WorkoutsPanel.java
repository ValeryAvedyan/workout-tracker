package view;

import controller.WorkoutController;
import model.WorkoutRecord;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WorkoutsPanel extends JPanel
{
    private WorkoutController controller;
    private MainFrame mainFrame;
    private JTable workoutTable;
    private DefaultTableModel tableModel;

    public WorkoutsPanel(WorkoutController controller, MainFrame mainFrame)
    {
        this.controller = controller;
        this.mainFrame = mainFrame;
        setupUI();
    }

    private void setupUI()
    {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();

        JButton addBtn = createColoredButton("Добавить", new Color(100, 160, 100));
        JButton deleteBtn = createColoredButton("Удалить", new Color(220, 80, 80));
        JButton saveBtn = createColoredButton("Сохранить", new Color(80, 180, 80));
        JButton loadBtn = createColoredButton("Загрузить", new Color(80, 120, 200));

        addBtn.addActionListener(e -> switchToAddTab());

        deleteBtn.addActionListener(e ->
        {
            String result = controller.deleteWorkout(workoutTable.getSelectedRow());
            mainFrame.showMessage(result);
            refreshTable();
            mainFrame.refreshAllPanels();
        });

        saveBtn.addActionListener(e ->
        {
            String result = controller.saveData();
            mainFrame.showMessage(result);
        });

        loadBtn.addActionListener(e ->
        {
            String result = controller.loadData();
            mainFrame.showMessage(result);
            refreshTable();
            mainFrame.refreshAllPanels();
        });

        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(loadBtn);

        String[] columns = {"Упражнение", "Подходы", "Повторения", "Вес", "Дата"};
        tableModel = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        workoutTable = new JTable(tableModel);

        workoutTable.setRowHeight(25);
        workoutTable.setShowGrid(true);
        workoutTable.setGridColor(Color.LIGHT_GRAY);

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(workoutTable), BorderLayout.CENTER);
    }

    private JButton createColoredButton(String text, Color color)
    {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }

    private void switchToAddTab()
    {
        mainFrame.switchToTab(1);
    }

    public void refreshTable()
    {
        tableModel.setRowCount(0);
        controller.getAllWorkouts().forEach(record ->
        {
            tableModel.addRow(new Object[]{
                    record.getExerciseName(),
                    record.getSets(),
                    record.getReps(),
                    record.getWeight(),
                    record.getDate()
            });
        });

        applyRowStyles();
    }

    private void applyRowStyles()
    {
        workoutTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row % 2 == 0)
                {
                    c.setBackground(Color.WHITE);
                }
                else
                {
                    c.setBackground(new Color(240, 240, 240));
                }
                c.setForeground(Color.BLACK);

                return c;
            }
        });
    }
}