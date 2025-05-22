import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Edit_Panel {

    private final DefaultTableModel model;
    private final List<JComponent> fields;
    private final SQLHandler sqlHandler;
    private final JTable table;
    private Connection con;
    private final List<Integer> pages;
    private int tabIndex;
    private List<String> search;
    private List<String> sortBy;

    public Edit_Panel(DefaultTableModel model, List<JComponent> fields, SQLHandler sqlHandler, JTable table, Connection con, List<Integer> pages, int tabIndex, List<String> search, List<String> sort) {
        this.model = model;
        this.fields = fields;
        this.sqlHandler = sqlHandler;
        this.table = table;
        this.con = con;
        this.pages = pages;
        this.tabIndex = tabIndex;
        this.search = search;
        this.sortBy = sort;
    }

    private JPanel createCRUDBtnPanel(String f) {
        JPanel crudBtnPanel = new JPanel();
        JButton add = new JButton("Add");
        JButton del = new JButton("Delete");
        JButton upd = new JButton("Update");

        add.addActionListener(e-> {
            new Create(sqlHandler, fields, f, con, pages, tabIndex, search, sortBy).actionPerformed(e);
            GUI.loadData(model, sqlHandler, pages.get(tabIndex), search.get(tabIndex), sortBy.get(tabIndex));
        });
        del.addActionListener(e -> {
            new Delete(model, table, sqlHandler, tabIndex, pages).actionPerformed(e);
            GUI.loadData(model, sqlHandler, pages.get(tabIndex), search.get(tabIndex), sortBy.get(tabIndex));
        });
        upd.addActionListener(e -> {
            new Update(model, table, fields, sqlHandler, pages, tabIndex, f, con, search, sortBy).actionPerformed(e);
            GUI.loadData(model, sqlHandler, pages.get(tabIndex), search.get(tabIndex), sortBy.get(tabIndex));
        });


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
