import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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
    String[] columns;
    List<DefaultTableModel> models = new ArrayList<>();
    List<SQLHandler> sqlHandlers = new ArrayList<>();
    List<JTable> tables = new ArrayList<>();
    List<List<JComponent>> fields = new ArrayList<>();
    List<Integer> pages = new ArrayList<>();
    Connection con;
    int page = 1;
    int tabIndex;

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
                loadData(models.get(index), sqlHandlers.get(index), page);

                List<JComponent> field = fields.get(index);
                    if (field.getLast() instanceof JComboBox) {
                        JComboBox<String> fk = (JComboBox<String>) field.getLast();
                        Fields fHelper = new Fields(name, con, pages, tabIndex);
                        fHelper.refreshfk(fk, name[index]);
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
        con =Database.connect();
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
        tabIndex = models.size() - 1;
        JTable table = new JTable(model);
        tables.add(table);
        Fields infield = new Fields(name, con, pages, tabIndex);
        JScrollPane sp = new JScrollPane(table);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        List<JComponent> field = infield.createFields(model, f);
        fields.add(field);
        pages.add(1);


        Edit_Panel edit_panel = new Edit_Panel(sorter, model, field, sqlHandler, table, con, pages, tabIndex);
        Tab_Panel tab_panel = new Tab_Panel(sorter, model, sqlHandler, pages, tabIndex);


        JPanel tabPanel = tab_panel.createTabPanel(f, sp);
        JPanel editPanel = edit_panel.createEditPanelLayout(f);


       table.addMouseListener(TableListener(table, model, field, sorter));

        loadData(model, sqlHandler, page);

        return Layout.MainPanelLayout(MainPanel, tabPanel, editPanel);
    }

    public static void loadData(DefaultTableModel model, SQLHandler sqlHandler, int page) {
        model.setRowCount(0);
        try {
            List<String[]> data = sqlHandler.readPageSQL(page);

            for (String[] row : data) {
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

    /*public static void updateReferences(String refval, String f, Connection con) {
        int index = Arrays.asList(name).indexOf(f) - 1;
        SQLHandler ref;
        if (index > -1 && index < name.length) {
            ref = new SQLHandler(name[index], con);
            List<String[]> refCSV = ref.readSQL();
            for (String[] refData : refCSV) {
                if (refData[refData.length - 1].equals(refval)) {
                    refData[refData.length - 1] = null;
                    ref.updateDb(refData);
                }
            }

        }
    }

     */
    private MouseAdapter TableListener(JTable table, DefaultTableModel model, List<JComponent> fields, TableRowSorter<DefaultTableModel> sorter) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    return;
                }
                selectedRow = sorter.convertRowIndexToModel(selectedRow);
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