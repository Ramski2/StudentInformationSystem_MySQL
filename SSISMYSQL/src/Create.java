import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class Create implements ActionListener {

    private final SQLHandler sqlHandler;
    private final List<JComponent> fields;
    private final Connection con;
    private final List<Integer> pages;
    private final int tabIndex;
    private final List<String> search;
    private final List<String> sortBy;
    boolean success = false;

    public Create(SQLHandler sqlHandler, List<JComponent> fields, Connection con, List<Integer> pages, int tabIndex, List<String> search, List<String> sortBy){
        this.sqlHandler = sqlHandler;
        this.fields = fields;
        this.con = con;
        this.pages = pages;
        this.tabIndex = tabIndex;
        this.search = search;
        this.sortBy = sortBy;
    }
    public boolean Compare(String[] data){
        List<String[]> sqlData = sqlHandler.readPageSQL(pages.get(tabIndex), search.get(tabIndex), sortBy.get(tabIndex));
        for (String[] cData : sqlData) {
            if (cData[0].equals(data[0])) {
                return true;
            }
        }
        return false;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] data = new String[fields.size()];
        String[] header = sqlHandler.getHeaders();

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
                assert selectedItem != null;
                data[i] = selectedItem.toString();
            }
        }
        for (String val : data){
            if (val.isEmpty() || val.equals("Add New") || val.equals("---------")){
                JOptionPane.showMessageDialog(null,
                        "Data incomplete! Please make sure to put data on all field.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (data[data.length - 1] != null && Fields.NotinSQl(data[data.length - 1], tabIndex, con)){
            String name = header[header.length - 1];
            JOptionPane.showMessageDialog(null, "No such data in the " + name + " Column.", "Invalid Data", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(Compare(data)) {
            JOptionPane.showMessageDialog(null,
                        header[0] + " already exist",
                    null,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        sqlHandler.addtoDb(data);
        Fields.clearFields(fields);

        JOptionPane.showMessageDialog(null,
                "Data Added Successfully!",
                null,
                JOptionPane.INFORMATION_MESSAGE);

        success = true;
    }
}
