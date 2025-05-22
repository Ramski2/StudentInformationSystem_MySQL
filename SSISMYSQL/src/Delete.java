import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class Delete implements ActionListener {
    private final DefaultTableModel model;
    private final JTable table;
    private final SQLHandler sqlHandler;

    public Delete(DefaultTableModel model, JTable table, SQLHandler sqlHandler, int tabIndex, List<Integer> pages){
        this.model = model;
        this.table = table;
        this.sqlHandler = sqlHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "There could be data associated. Are you sure you want to delete?", null, JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.NO_OPTION) return;

            if (confirm == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int rowIndex = selectedRows[i];
                    String delVal = (String) model.getValueAt(rowIndex, 0);
                    sqlHandler.deletefromDb(delVal);
                }
                JOptionPane.showMessageDialog(null, "Data Deleted Successfully!", null, JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No rows selected! Please Select one.", "Row Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
