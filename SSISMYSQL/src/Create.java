import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class Create implements ActionListener {

    private final SQLHandler sqlHandler;
    private final List<JComponent> fields;
    private String f;
    private Connection con;
    private List<Integer> pages;
    private int tabIndex;

    public Create(SQLHandler sqlHandler, List<JComponent> fields, String f, Connection con, List<Integer> pages, int tabIndex){
        this.sqlHandler = sqlHandler;
        this.fields = fields;
        this.f = f;
        this.con = con;
        this.pages = pages;
        this.tabIndex = tabIndex;
    }
    public boolean Compare(String[] data){
        List<String[]> sqlData = sqlHandler.readPageSQL(pages.get(tabIndex));
        for (String[] cData : sqlData) {
            if (cData[0].equals(data[0])) {
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
                    JOptionPane.showMessageDialog(null, header[0] + " has to above 2000 and below Current Year", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (fields.get(i) instanceof JTextField && !(fields.get(i) instanceof JFormattedTextField)) {
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

        if(Compare(data)) {
            JOptionPane.showMessageDialog(null,
                        header[0] + " already exist",
                    null,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (data[data.length - 1].equals("None")){
            data[data.length-1] = null;
        }
        sqlHandler.addtoDb(data);
        Fields.clearFields(fields);
        JOptionPane.showMessageDialog(null,
                "Data Added Successfully!",
                null,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
