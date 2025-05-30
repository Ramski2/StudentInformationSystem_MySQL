import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.text.ParseException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Fields {
    private static String[] table;
    private static Connection con;
    private final List<Integer> pages;
    private final int tabIndex;
    private final List<String> search;
    private final List<String> sortBy;

    public Fields(String[] table, Connection con, List<Integer> pages, int tabIndex, List<String> search, List<String> sortBy) {
        Fields.table = table;
        Fields.con = con;
        this.pages = pages;
        this.tabIndex = tabIndex;
        this.search = search;
        this.sortBy = sortBy;
    }


    public List<JComponent> createFields(DefaultTableModel model) {
        List<JComponent> fields = new ArrayList<>();

        JComboBox<String> fk = new JComboBox<>();

        int index = tabIndex + 1;

        if (index == 1) {
            JFormattedTextField field = new JFormattedTextField(format());

            int curryear = Year.now().getValue();
            String idNum = getIncrement(curryear);
            field.setText(curryear + idNum);
            fields.add(field);

            for (int i = 0; i < model.getColumnCount() - 4; i++) {
                fields.add(new JTextField());
            }
            String[] gender = {"---------","Male", "Female", "Other", "Rather Not Say"};
            String[] year = {"---------","1", "2", "3", "4"};

            fields.add(new JComboBox<>(gender));
            fields.add(new JComboBox<>(year));

            fields.add(fk);
            fKey(fk, index);
        } else if (index == 2) {
            for (int i = 0; i < model.getColumnCount() - 1; i++) {
                fields.add(new JTextField());
            }
            fields.add(fk);
            fKey(fk, index);

        } else {
            for (int i = 0; i < model.getColumnCount(); i++) {
                fields.add(new JTextField());
            }
        }

        fk.addItemListener(e -> {
            if ("Add New".equals(fk.getSelectedItem())){
                fk.setSelectedIndex(0);
                InputWindow(fk);

            }
        });

        return fields;
    }

    public void fKey(JComboBox<String> fk, int index) {
        fk.removeAllItems();
        fk.addItem("---------");
        fk.addItem("Add New");

        if (index < table.length) {
            SQLHandler sqlHandler = new SQLHandler(table[index].toLowerCase(), con);
            List<String[]> sqlData = sqlHandler.readSQL();
            List<String> values = boxValue(sqlData);

            for (String value : values) {
                fk.addItem(value);
            }
        }
    }

    public static boolean NotinSQl(String selectedItem, int tabIndex, Connection con){
        int index = tabIndex + 1;
        if (index == table.length) return false;
        SQLHandler newSQL = new SQLHandler(table[index], con);
        List<String[]> cData = newSQL.readSQL();
        for (String[] c : cData) {
            if (selectedItem.equals(c[0])) {
                return false;
            }
        }
        return true;
    }

    public List<String> boxValue(List<String[]> csvData) {
        List<String> values = new ArrayList<>();
        for (String[] row : csvData) {
            if (row.length > 0) {
                values.add(row[0]);
            }
        }
        return values;
    }

    public MaskFormatter format() {
        MaskFormatter format;
        try {
            format = new MaskFormatter("####-####");
            format.setValidCharacters("0123456789");

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return format;
    }

    public static boolean validateID(String data) {
        String[] id = data.split("-");

        try {
            int year = Integer.parseInt(id[0]);
            int currentyear = Year.now().getValue();
            if (year < 2000 || year > currentyear) return true;
            Integer.parseInt(id[1]);

            return false;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearFields(List<JComponent> fields) {
        for (JComponent tfield : fields) {
            if (tfield instanceof JFormattedTextField){
                int curryear = Year.now().getValue();
                String idNum = getIncrement(curryear);
                ((JFormattedTextField) tfield).setText(curryear + idNum);
            }
            if (tfield instanceof JTextField && !(tfield instanceof JFormattedTextField)) {
                ((JTextField) tfield).setText("");
            } else if (tfield instanceof JComboBox<?>) {
                ((JComboBox<?>) tfield).setSelectedIndex(0);
            }
        }
    }


    private static String getIncrement(int year) {
        SQLHandler sqlHandler = new SQLHandler(table[0], con);
        List<String[]> sqlData = sqlHandler.readSQL();
        int maxNum = 0;

        for (String[] row : sqlData) {
            if (row.length > 0) {
                String[] parts = row[0].split("-");
                if (parts.length == 2) {
                    try {
                        int rowYear = Integer.parseInt(parts[0]);
                        int rowNum = Integer.parseInt(parts[1]);

                        if (rowYear == year && rowNum > maxNum) {
                            maxNum = rowNum;
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        maxNum++;
        return String.format("%04d", maxNum);
    }

    private boolean isDialogOpen = false;
    private void InputWindow(JComboBox<String> fk) {
        if (isDialogOpen) return;
        isDialogOpen = true;
        int index = tabIndex + 1;
        if (index >= table.length) return;


        SQLHandler sqlHandler = new SQLHandler(table[index].toLowerCase(), con);
        String[] data = sqlHandler.getHeaders();
        List<JComponent> fields = new ArrayList<>();
        List<JLabel> labels = new ArrayList<>();

        JDialog dialog = new JDialog();
        dialog.setTitle("New Entry");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isDialogOpen = false;
            }
        });
        dialog.setResizable(false);
        dialog.setModal(true);

        for (int i = 0; i < (data.length); i++){
            labels.add(new JLabel(data[i]));
            JComponent field = (index == 1 && i == data.length - 1) ? new JComboBox<>() : new JTextField();
            if (field instanceof JComboBox<?>){
                @SuppressWarnings("unchecked") JComboBox<String> cb = (JComboBox<String>) field;
                fKey(cb, index+1);
                cb.removeItem("Add New");
            }
            fields.add(field);
        }

        JPanel inputWin = Layout.InputWinLayout(fields, labels);

        JLabel title = new JLabel("Add New Data");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton save = new JButton("Save");

        save.addActionListener(e-> {
            Create create = new Create(sqlHandler, fields, con, pages, index, search, sortBy);
            create.actionPerformed(e);
            if (create.isSuccess()){
                isDialogOpen = false;
                refreshfk(fk);
                dialog.dispose();
            }

        });

        JPanel contentPane = Layout.AddNewContentPaneLayout(title, inputWin, save);
        dialog.setContentPane(contentPane);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }
    public void refreshfk(JComboBox<String> fk) {
        int index = tabIndex + 1;
        if (index < table.length) {
            fKey(fk, index);
        }
    }
}


