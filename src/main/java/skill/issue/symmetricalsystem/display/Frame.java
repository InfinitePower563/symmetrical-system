package skill.issue.symmetricalsystem.display;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.EventObject;

public class Frame extends JFrame {
    private static final int SCREEN_WIDTH = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;

    public Frame(String[][] tracksAsTable, String[] columns) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, 500);


        JTable table = new JTable(tracksAsTable, columns);
        table.setEnabled(false);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, renderer);

        // Set custom editor to disable editing
        TableCellEditor editor = new TableCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return true;
            }

            @Override
            public boolean stopCellEditing() {
                return true;
            }

            @Override
            public void cancelCellEditing() {
            }

            @Override
            public void addCellEditorListener(CellEditorListener l) {
            }

            @Override
            public void removeCellEditorListener(CellEditorListener l) {
            }
        };
        table.setDefaultEditor(Object.class, editor);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
        //adjustColumnWidths(table);
        //distribute table column widths evenly
        int columnWidth = SCREEN_WIDTH / table.getColumnCount();
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth);
        }

        JLabel label = new JLabel("VATSIM NAT Tracks List || TMI: " + tracksAsTable[0][1]);
        label.setFont(new Font("Monospaced", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label, BorderLayout.NORTH);

        setVisible(true);
    }
}