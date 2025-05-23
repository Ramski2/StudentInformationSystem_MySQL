import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class Update implements ActionListener {
    private final DefaultTableModel model;
    private final JTable table;
    private final List<JComponent> fields;
    private final SQLHandler sqlHandler;
    private final List<Integer> pages;
    private final int tabIndex;
    private final String f;
    private final Connection con;
    private final List<String> search;
    private final List<String> sortBy;


    public Update(DefaultTableModel model, JTable table, List<JComponent> fields, SQLHandler sqlHandler, List<Integer> pages, int tabIndex, String f, Connection con, List<String> search, List<String> sortBy){
        this.model = model;
        this.table = table;
        this.fields = fields;
        this.sqlHandler = sqlHandler;
        this.pages = pages;
        this.tabIndex = tabIndex;
        this.f = f;
        this.con = con;
        this.search = search;
        this.sortBy = sortBy;
    }
    public boolean Compare(String[] data, int index){
        List<String[]> sqlData = sqlHandler.readPageSQL(pages.get(tabIndex), search.get(tabIndex), sortBy.get(tabIndex));
        sqlData.remove(index);
        for (String[] cData : sqlData){
            if (cData[0].equals(data[0]) ){
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String[] data = new String[fields.size()];
        String[] header = sqlHandler.getHeaders();

        boolean valid = true;
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i) instanceof JFormattedTextField) {
                data[i] = ((JFormattedTextField) fields.get(i)).getText().trim();
                if (Fields.validateID(data[i])) {
                    JOptionPane.showMessageDialog(null, model.getColumnName(0) + " has to above 2000 and below Current Year", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (fields.get(i) instanceof JTextField) {
                data[i] = ((JTextField) fields.get(i)).getText().trim();
            } else if (fields.get(i) instanceof JComboBox<?>) {
                Object selectedItem = ((JComboBox<?>) fields.get(i)).getSelectedItem();
                String name = header[header.length - 1];
                if (selectedItem == null || selectedItem.equals("Add New") || selectedItem.equals("---------")) {
                    JOptionPane.showMessageDialog(null, "No " + name + " selected!", "Invalid " + name, JOptionPane.ERROR_MESSAGE);
                    valid = false;
                    break;
                }
                Object lastselectedItem = ((JComboBox<?>) fields.getLast()).getSelectedItem();

                if (lastselectedItem != null && !Fields.existinSQl(lastselectedItem.toString(), f, con)){
                    JOptionPane.showMessageDialog(null, "No such data in the " + name + " Column.", "Invalid Data", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    data[i] = selectedItem.toString();
                }
            }
        }
        if (!valid) return;
        for (String val : data){
            if (val.isEmpty()){
                JOptionPane.showMessageDialog(null,
                        "Data incomplete! Please make sure to put data on all field.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (table.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(null, "No row selected! If you selected one, make sure it shows up on the field above by pressing again.", "Row Selection Error", JOptionPane.ERROR_MESSAGE);
        }else {
                int rowIndex = table.getSelectedRow();
                if (Compare(data, rowIndex)){
                    JOptionPane.showMessageDialog(null, model.getColumnName(0) + " already exist", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String delVal = (String) model.getValueAt(rowIndex, 0);
                sqlHandler.updateDb(data, delVal);
                Fields.clearFields(fields);

            JOptionPane.showMessageDialog(null, "Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        }

    }
}
