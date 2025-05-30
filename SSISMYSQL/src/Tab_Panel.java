import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Tab_Panel {
    private final DefaultTableModel model;
    private final SQLHandler sqlHandler;
    private final List<Integer>pages;
    private final int tabIndex;
    private final List<String> search;
    private final List<String> sortBy;

    public Tab_Panel(DefaultTableModel model, SQLHandler sqlHandler, List<Integer> pages, int tabIndex, List<String> search, List<String> sortBy) {
        this.model = model;
        this.sqlHandler = sqlHandler;
        this.pages = pages;
        this.tabIndex = tabIndex;
        this.search = search;
        this.sortBy = sortBy;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        JTextField srchfields = new JTextField();
        JComboBox<String> sortByBox = new JComboBox<>();
        JLabel sortlbl = new JLabel("Sort By:");

        String[] srch = sqlHandler.getHeaders();
        for (String s : srch) {
            sortByBox.addItem(s + " ASC");
            sortByBox.addItem(s + " DESC");
        }

        sortByBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String searchString = search.get(tabIndex);
                String sortString = (String) e.getItem();

                int len = sortString.length();
                String prefix = sortString.substring(0, len - 5).toLowerCase().replace(" ", "_");
                String suffix = sortString.substring(len - 5);  // last 4 characters unchanged
                String sort = prefix + suffix;
                sortBy.set(tabIndex, sort);
                    GUI.loadData(model, sqlHandler, pages.get(tabIndex), searchString, sortBy.get(tabIndex));
            }
        });

        srchfields.addKeyListener(srchKeyListener(srchfields));

        return Layout.SearchPanelLayout(searchPanel, srchfields, sortlbl, sortByBox);
    }

    private JPanel createPagePanel() {
        JPanel pagePanel = new JPanel();
        JButton prevBtn = new JButton("Prev");
        JButton nextBtn = new JButton("Next");
        JLabel pageLbl = new JLabel();
        prevBtn.addActionListener(e -> {
            int page = pages.get(tabIndex);
            if (page > 1) {
                page--;
                pages.set(tabIndex, page);
                GUI.loadData(model, sqlHandler, page, search.get(tabIndex), sortBy.get(tabIndex));
                pageLbl.setText("Page: " + pages.get(tabIndex));
            }
        });
        nextBtn.addActionListener(e -> {
            int page = pages.get(tabIndex);
            int totalCnt = sqlHandler.getTotalCount();
            if ((page * 17) < totalCnt) {
                page++;
                pages.set(tabIndex, page);
                GUI.loadData(model, sqlHandler, page, search.get(tabIndex), sortBy.get(tabIndex));
                pageLbl.setText("Page: " + pages.get(tabIndex));
            }
        });
        pageLbl.setText("Page: " + pages.get(tabIndex));
        return Layout.pagePanelLayout(pagePanel, prevBtn, nextBtn, pageLbl);
    }

    protected JPanel createTabPanel(String name, JScrollPane sp) {
        JPanel tabPanel = new JPanel();
        JLabel tabTitle;

            tabTitle = new JLabel(name + " Table");
            tabTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JButton editTable = new JButton("Edit Table");
        JButton refresh = new JButton("Refresh Table");
        JPanel searchPanel = createSearchPanel();
        JPanel pagePanel = createPagePanel();

        tabPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        editTable.addActionListener(e -> editTableActionPerformed(tabPanel));
        refresh.addActionListener(e -> GUI.loadData(model, sqlHandler, pages.get(tabIndex), search.get(tabIndex), sortBy.get(tabIndex)));


        return Layout.TabPanelLayout(tabPanel, sp, tabTitle, editTable, searchPanel, refresh, pagePanel);
    }

    private KeyAdapter srchKeyListener(JTextField tsearch) {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchString = tsearch.getText();
                search.set(tabIndex, searchString);
                if (sortBy != null) {
                    String sort = sortBy.get(tabIndex).toLowerCase().replace(" ", "_");
                    GUI.loadData(model, sqlHandler, pages.get(tabIndex), search.get(tabIndex), sort);
                }

            }

        };
    }

    private void editTableActionPerformed(JPanel tabPanel) {
        Component parent = tabPanel.getParent();
        while (!(parent instanceof JPanel) && parent != null) {
            parent = parent.getParent();
        }
        if (parent != null) {
            for (Component c : ((JPanel) parent).getComponents()) {
                if (c instanceof JPanel && c.getPreferredSize().width < 300) {
                    for (Component inner : ((JPanel) c).getComponents()) {
                        if (inner instanceof JPanel) {
                            for (Component field : ((JPanel) inner).getComponents()) {
                                field.setEnabled(true);
                            }
                        } else if (inner instanceof JButton) {
                            inner.setEnabled(true);
                        }
                    }
                }
            }
        }
    }
}
