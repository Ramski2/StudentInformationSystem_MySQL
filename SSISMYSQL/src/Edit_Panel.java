import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Edit_Panel {

    private final TableRowSorter<DefaultTableModel> sorter;
    private final DefaultTableModel model;
    private final List<JComponent> fields;
    private final SQLHandler sqlHandler;
    private final JTable table;
    private Connection con;
    private final List<Integer> pages;
    private int tabIndex;

    public Edit_Panel(TableRowSorter<DefaultTableModel> sorter, DefaultTableModel model, List<JComponent> fields, SQLHandler sqlHandler, JTable table, Connection con, List<Integer> pages, int tabIndex) {
        this.sorter = sorter;
        this.model = model;
        this.fields = fields;
        this.sqlHandler = sqlHandler;
        this.table = table;
        this.con = con;
        this.pages = pages;
        this.tabIndex = tabIndex;
    }

    private JPanel createCRUDBtnPanel(String f) {
        JPanel crudBtnPanel = new JPanel();
        JButton add = new JButton("Add");
        JButton del = new JButton("Delete");
        JButton upd = new JButton("Update");

        add.addActionListener(e-> {
            new Create(sqlHandler, fields, f, con, pages, tabIndex).actionPerformed(e);
            GUI.loadData(model, sqlHandler, pages.get(tabIndex));
        });
        del.addActionListener(new Delete(model, table, sorter, sqlHandler, tabIndex, pages));
        upd.addActionListener(new Update(model, table, fields, sorter, sqlHandler, pages, tabIndex, f, con));


        return Layout.CRUDBtnPanelLayout(crudBtnPanel, add, del, upd);
    }

    protected JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setName("inputPanel");
        List<JLabel> tabName = new ArrayList<>();


        for (int i = 0; i < model.getColumnCount(); i++) {
            tabName.add(new JLabel(model.getColumnName(i) + ":"));
        }
        return Layout.InputPanelLayout(inputPanel, tabName, fields);
    }

    protected JPanel createEditPanelLayout(String f) {
        JPanel editPanel = new JPanel();

        JLabel editTitle = new JLabel("Table Edit");
        editTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JButton close = new JButton("Close");
        JButton clear = new JButton("Clear");

        close.setEnabled(false);

        for (JComponent field : fields) {
            field.setEnabled(false);
        }

        JPanel inputPanel = createInputPanel();
        JPanel crudBtnPanel = createCRUDBtnPanel(f);

        for (Component c : crudBtnPanel.getComponents()) {
            if (c instanceof JButton) {
                c.setEnabled(false);
            }
        }
        clear.setEnabled(false);
        clear.addActionListener(e -> Fields.clearFields(fields));

        close.addActionListener(e -> closeActionPerformed(close, crudBtnPanel, clear));

        return Layout.EditPanelLayout(editPanel, inputPanel, editTitle, crudBtnPanel, close, clear);
    }

    private void closeActionPerformed(JButton close, JPanel crudBtnPanel, JButton clear) {
        close.setEnabled(false);

        for (JComponent field : fields) {
            field.setEnabled(false);
        }
        for (Component c : crudBtnPanel.getComponents()) {
            if (c instanceof JButton) {
                c.setEnabled(false);
            }
        }
        clear.setEnabled(false);

    }

}
