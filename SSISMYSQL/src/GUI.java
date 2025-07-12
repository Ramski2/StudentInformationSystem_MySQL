import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GUI extends JFrame {

    static String[] name = {"Student"
            , "Program"
            , "College"};
    JTabbedPane tab = new JTabbedPane();
    List<String> search = new ArrayList<>();
    List<String> sortBy = new ArrayList<>();
    String[] columns;
    List<DefaultTableModel> models = new ArrayList<>();
    List<SQLHandler> sqlHandlers = new ArrayList<>();
    List<JTable> tables = new ArrayList<>();
    List<List<JComponent>> fields = new ArrayList<>();
    List<Integer> pages = new ArrayList<>();
    Connection con;

    public GUI() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Information System");
        setSize(840, 540);
        setLocationRelativeTo(null);

        for (String s : name) {
            tab.addTab(s, PanelLayout(s));
        }

        tab.addChangeListener(e -> {
            int index = tab.getSelectedIndex();
            if (index != -1) {
                loadData(models.get(index), sqlHandlers.get(index), pages.get(index), search.get(index), sortBy.get(index));

                List<JComponent> field = fields.get(index);
                    if (field.getLast() instanceof JComboBox) {
                        @SuppressWarnings("unchecked") JComboBox<String> fk = (JComboBox<String>) field.getLast();
                        Fields fHelper = new Fields(name, con, pages, index, search, sortBy);
                        fHelper.refreshfk(fk);
                    }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(tab)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(tab, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                                .addContainerGap())
        );

    }

    public JPanel PanelLayout(String f) {
        JPanel MainPanel = new JPanel();
        MainPanel.setPreferredSize(new Dimension(740, 440));
        GroupLayout panelLayout = new GroupLayout(MainPanel);
        MainPanel.setLayout(panelLayout);

        try {
            con = Database.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SQLHandler sqlHandler = new SQLHandler(f.toLowerCase(), con);
        sqlHandlers.add(sqlHandler);

        columns = sqlHandler.getHeaders();
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        models.add(model);
        int tabIndex = models.size() - 1;
        JTable table = new JTable(model);
        tables.add(table);
        Fields infield = new Fields(name, con, pages, tabIndex, search, sortBy);
        JScrollPane sp = new JScrollPane(table);

        List<JComponent> field = infield.createFields(model);
        fields.add(field);
        pages.add(1);
        search.add("");
        sortBy.add("");

        Edit_Panel edit_panel = new Edit_Panel(model, field, sqlHandler, table, con, pages, tabIndex, search, sortBy);
        Tab_Panel tab_panel = new Tab_Panel(model, sqlHandler, pages, tabIndex, search, sortBy);

        JPanel tabPanel = tab_panel.createTabPanel(f, sp);
        JPanel editPanel = edit_panel.createEditPanelLayout();

       table.addMouseListener(TableListener(table, model, field));

        loadData(model, sqlHandler, pages.get(tabIndex), search.get(tabIndex), sortBy.get(tabIndex));

        return Layout.MainPanelLayout(MainPanel, tabPanel, editPanel);
    }

    public static void loadData(DefaultTableModel model, SQLHandler sqlHandler, int page, String search, String sortBy) {
        model.setRowCount(0);
        try {
            List<String[]> data = sqlHandler.readPageSQL(page, search, sortBy);

            for (String[] row : data) {
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

    private MouseAdapter TableListener(JTable table, DefaultTableModel model, List<JComponent> fields) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    return;
                }
                for (int i = 0; i < model.getColumnCount(); i++) {
                    if (i < fields.size()) {
                        JComponent field = fields.get(i);
                        Object value = model.getValueAt(selectedRow, i);

                        if (field instanceof JTextField) {
                            ((JTextField) field).setText(value != null ? value.toString() : "");
                        } else if (field instanceof JComboBox) {
                            ((JComboBox<?>) field).setSelectedItem(value);
                        }
                    }
                }
            }

        };
    }


}