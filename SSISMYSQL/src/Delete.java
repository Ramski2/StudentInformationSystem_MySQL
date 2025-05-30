import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Delete implements ActionListener {
    private final DefaultTableModel model;
    private final JTable table;
    private final SQLHandler sqlHandler;
    boolean success = false;

    public Delete(DefaultTableModel model, JTable table, SQLHandler sqlHandler){
        this.model = model;
        this.table = table;
        this.sqlHandler = sqlHandler;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "There could be data associated. Are you sure you want to delete?", null, JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.NO_OPTION) {
                return;
            }


            if (confirm == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int rowIndex = selectedRows[i];
                    String delVal = (String) model.getValueAt(rowIndex, 0);
                    sqlHandler.deletefromDb(delVal);
                    success = true;
                }
                JOptionPane.showMessageDialog(null, "Data Deleted Successfully!", null, JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No rows selected! Please Select one.", "Row Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
