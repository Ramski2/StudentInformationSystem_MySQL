import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Layout {

    protected static JPanel TabPanelLayout(JPanel tabPanel, JScrollPane sp, JLabel tabTitle, JButton editTable, JPanel searchPanel, JButton refresh, JPanel pagePanel){
        GroupLayout tabPanelLayout = new GroupLayout(tabPanel);
        tabPanel.setLayout(tabPanelLayout);
        tabPanelLayout.setHorizontalGroup(
                tabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(tabPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(tabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(sp, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(tabPanelLayout.createSequentialGroup()
                                                .addComponent(tabTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(refresh)
                                                .addGap(40, 40, 40)
                                                .addComponent(editTable)
                                                .addGap(23, 23, 23)))
                                .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, tabPanelLayout.createSequentialGroup()
                                .addContainerGap(28, Short.MAX_VALUE)
                                .addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53))
                        .addGroup(tabPanelLayout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addComponent(pagePanel, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabPanelLayout.setVerticalGroup(
                tabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(tabPanelLayout.createSequentialGroup()
                                .addGroup(tabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tabTitle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(tabPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(tabPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(refresh)
                                                        .addComponent(editTable))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sp, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pagePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(25, Short.MAX_VALUE))
        );
        return tabPanel;
    }

    protected static JPanel pagePanelLayout(JPanel pagePanel, JButton prevBtn, JButton nextBtn, JLabel pageNum) {
        GroupLayout pagePanelLayout = new GroupLayout(pagePanel);
        pagePanel.setLayout(pagePanelLayout);

        pagePanelLayout.setHorizontalGroup(
                pagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(pagePanelLayout.createSequentialGroup()
                                .addContainerGap(19, Short.MAX_VALUE)
                                .addComponent(prevBtn, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pageNum)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nextBtn, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
        );
        pagePanelLayout.setVerticalGroup(
                pagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, pagePanelLayout.createSequentialGroup()
                                .addGap(0, 2, Short.MAX_VALUE)
                                .addGroup(pagePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(prevBtn)
                                        .addComponent(nextBtn)
                                        .addComponent(pageNum)))
        );
        return pagePanel;
    }

    protected static JPanel SearchPanelLayout(JPanel searchPanel, JTextField srchfields, JLabel srchlbl, JComboBox<String> srchBy){
        GroupLayout sPanelLayout = new GroupLayout(searchPanel);
        searchPanel.setLayout(sPanelLayout);

        sPanelLayout.setHorizontalGroup(
                sPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(sPanelLayout.createSequentialGroup()
                                .addComponent(srchfields, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                                .addComponent(srchlbl)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(srchBy, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        sPanelLayout.setVerticalGroup(
                sPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, sPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(sPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(srchfields, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(srchBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(srchlbl)))
        );

        return searchPanel;
    }

    protected static JPanel CRUDBtnPanelLayout(JPanel crudBtnPanel, JButton add, JButton del, JButton upd){
        GroupLayout crudBtnPanelLayout = new GroupLayout(crudBtnPanel);
        crudBtnPanel.setLayout(crudBtnPanelLayout);


        crudBtnPanelLayout.setHorizontalGroup(
                crudBtnPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(crudBtnPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(add, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(del, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(upd, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        crudBtnPanelLayout.setVerticalGroup(
                crudBtnPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(crudBtnPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(crudBtnPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(add)
                                        .addComponent(del)
                                        .addComponent(upd))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        return crudBtnPanel;
    }

    protected  static JPanel InputPanelLayout(JPanel inputPanel, List<JLabel> tabName, List<JComponent> fields){
        GroupLayout inputPanelLayout = new GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);

        GroupLayout.SequentialGroup hgroup = inputPanelLayout.createSequentialGroup();
        GroupLayout.ParallelGroup ngroup = inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.ParallelGroup fgroup = inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING);

        for (int i = 0; i < tabName.size(); i++) {
            ngroup.addComponent(tabName.get(i));
            if (i < fields.size()) {
                fgroup.addComponent(fields.get(i), GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE);
            }
        }
        hgroup.addContainerGap().addGroup(ngroup).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(fgroup).addContainerGap();
        inputPanelLayout.setHorizontalGroup(hgroup);

        GroupLayout.SequentialGroup vgroup = inputPanelLayout.createSequentialGroup();

        for (int i = 0; i < tabName.size(); i++) {
            GroupLayout.ParallelGroup rowgroup = inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            rowgroup.addComponent(tabName.get(i));
            if (i < fields.size()) {
                rowgroup.addComponent(fields.get(i), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            }
            vgroup.addGroup(rowgroup).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        }


        inputPanelLayout.setVerticalGroup(vgroup);


        return inputPanel;
    }

    protected static JPanel EditPanelLayout(JPanel editPanel, JPanel inputPanel, JLabel editTitle, JPanel crudBtnPanel, JButton saveEdit, JButton clear){

        GroupLayout editPanelLayout = new GroupLayout(editPanel);
        editPanel.setLayout(editPanelLayout);
        editPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        editPanelLayout.setHorizontalGroup(
                editPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(editPanelLayout.createSequentialGroup()
                                .addGroup(editPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(editPanelLayout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(editPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(editTitle, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, editPanelLayout.createSequentialGroup()
                                .addContainerGap(21, Short.MAX_VALUE)
                                .addGroup(editPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, editPanelLayout.createSequentialGroup()
                                                .addGroup(editPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(crudBtnPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(saveEdit))
                                                .addGap(22, 22, 22))
                                        .addGroup(GroupLayout.Alignment.TRAILING, editPanelLayout.createSequentialGroup()
                                                .addComponent(clear)
                                                .addGap(30, 30, 30))))
        );
        editPanelLayout.setVerticalGroup(
                editPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(editPanelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(editTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveEdit)
                                .addGap(3, 3, 3)
                                .addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(crudBtnPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(clear)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        return editPanel;
    }

    protected static JPanel InputWinLayout(List<JComponent> Fields, List<JLabel>name){
        JPanel inputWin = new JPanel();
        GroupLayout inputWinLayout = new GroupLayout(inputWin);
        inputWin.setLayout(inputWinLayout);

        GroupLayout.SequentialGroup hgroup = inputWinLayout.createSequentialGroup();
        GroupLayout.ParallelGroup ngroup = inputWinLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.ParallelGroup fgroup = inputWinLayout.createParallelGroup(GroupLayout.Alignment.LEADING);

        for (int i = 0; i < name.size(); i++){
            ngroup.addComponent(name.get(i));
            if (i < Fields.size()){
                fgroup.addComponent(Fields.get(i), 0, 144, Short.MAX_VALUE);
            }
        }
        hgroup.addContainerGap().addGroup(ngroup).addGap(0,0, Short.MAX_VALUE).addGroup(fgroup);
        inputWinLayout.setHorizontalGroup(hgroup);

        GroupLayout.SequentialGroup vgroup = inputWinLayout.createSequentialGroup();

        for (int i = 0; i < name.size(); i++){
            GroupLayout.ParallelGroup rowGroup = inputWinLayout.createParallelGroup();
            rowGroup.addComponent(name.get(i));
            if(i < Fields.size()){
                rowGroup.addComponent(Fields.get(i), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            }
            vgroup.addGroup(rowGroup).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        }
        inputWinLayout.setVerticalGroup(vgroup);
        return inputWin;
    }

    protected static JPanel AddNewContentPaneLayout(JLabel Title, JPanel inputWin, JButton save){
        JPanel contentPane = new JPanel();
        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(save)
                                .addGap(64, 64, 64))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(inputWin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Title)
                                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(Title)
                                .addGap(18, 18, 18)
                                .addComponent(inputWin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(save)
                                .addContainerGap(110, Short.MAX_VALUE))
        );
        return contentPane;
    }

    protected static JPanel MainPanelLayout(JPanel panel, JPanel tabPanel, JPanel editPanel){
        GroupLayout PanelLayout = new GroupLayout(panel);
        panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
                PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PanelLayout.createSequentialGroup()
                                .addComponent(tabPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(editPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        PanelLayout.setVerticalGroup(
                PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(PanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(editPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tabPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );


        return panel;
    }
}
