package com.ptit.librarymanagement.presentation.dialog.component;

import com.formdev.flatlaf.FlatLightLaf;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class MultiSelectForm<T> extends JPanel {
    private HeaderTitle titlePanel;
    private JTextField textField;
    private JDialog dialog;
    private DefaultListModel<T> listModel;
    private JList<T> list;
    private JButton okButton, cancelButton, createButton;
    private T[] items;
    private final Function<T, String> toDisplay;
    private final Set<T> selectedSet = new LinkedHashSet<>();


    public MultiSelectForm(String title, T[] items, Function<T, String> toDisplay) {
        this.items = items == null ? (T[]) new Object[0] : items;
        this.toDisplay = Objects.requireNonNull(toDisplay);
        createButton = new JButton("Thêm");
        titlePanel = new HeaderTitle(title);
        initUi(title);
    }


    public MultiSelectForm(String title, List<T> items, Function<T, String> toDisplay) {
        this(title, items == null ? null : (T[]) items.toArray(), toDisplay);
    }
    private boolean single = false;

    private void initUi(String title) {
        setLayout(new BorderLayout(5, 5));
        setBackground(Color.WHITE);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        add(textField, BorderLayout.CENTER);

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showListDialog(title);
            }
        });

    }

    private void showListDialog(String title) {

        JButton btnAdd = new JButton("Thêm");
        btnAdd.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(dialog, "Nhập tên mục mới:", "Thêm mục", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.trim().isEmpty()) {

                T newItem = (T) input.trim();


                List<T> tempList = new ArrayList<>(Arrays.asList(items));
                tempList.add(newItem);
                items = (T[]) tempList.toArray();


                listModel.addElement(newItem);
                applySelectedToList();
            }
        });
        if (dialog == null) {
            Window owner = SwingUtilities.getWindowAncestor(this);
            Frame frame = null;
            if (owner instanceof Frame) frame = (Frame) owner;

            dialog = new JDialog(frame, title == null ? "Chọn mục" : title, true);
            dialog.setSize(300, 400);
            dialog.setLayout(new BorderLayout(10, 10));
            dialog.setLocationRelativeTo(this);

            listModel = new DefaultListModel<>();
            for (T item : items) {
                listModel.addElement(item);
            }

            list = new JList<>(listModel);
            list.setSelectionMode(single ? ListSelectionModel.SINGLE_SELECTION : ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


            list.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    String text = value == null ? "" : toDisplay.apply((T) value);
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });


            applySelectedToList();

            JScrollPane scroll = new JScrollPane(list);


            JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
            JLabel searchLabel = new JLabel("Tìm kiếm:");
            JTextField searchField = new JTextField();
            searchPanel.add(titlePanel, BorderLayout.NORTH);
            searchPanel.add(searchLabel, BorderLayout.WEST);
            searchPanel.add(searchField, BorderLayout.CENTER);


            searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                private void update() {
                    String text = searchField.getText().trim().toLowerCase();
                    listModel.clear();
                    for (T item : items) {
                        String display = toDisplay.apply(item).toLowerCase();
                        if (display.contains(text)) {
                            listModel.addElement(item);
                        }
                    }
                    applySelectedToList(); // giữ selection
                }

                @Override
                public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
                @Override
                public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
                @Override
                public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
            });



            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            okButton = new JButton("OK");
            cancelButton = new JButton("Hủy");


            buttonPanel.add(createButton);
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            okButton.addActionListener(e -> {
                List<T> selected = list.getSelectedValuesList();
                selectedSet.clear();
                selectedSet.addAll(selected);
                String text = selected.stream().map(toDisplay).collect(Collectors.joining(", "));
                textField.setText(text);
                dialog.dispose();
            });

            cancelButton.addActionListener(e -> dialog.dispose());




            JPanel centerPanel = new JPanel(new BorderLayout(5,5));
            centerPanel.add(searchPanel, BorderLayout.NORTH);
            centerPanel.add(scroll, BorderLayout.CENTER);

            dialog.add(centerPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);

        } else {

            applySelectedToList();
        }


        dialog.setVisible(true);
    }

    public void setActionCreateButton (ActionListener actionCreateButton) {
        this.createButton.addActionListener(actionCreateButton);

    }




    private void applySelectedToList() {
        if (listModel == null || list == null) return;
        // build list of indices to select
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            T element = listModel.getElementAt(i);
            if (selectedSet.contains(element)) {
                indices.add(i);
            }
        }
        int[] idxArray = indices.stream().mapToInt(Integer::intValue).toArray();
        list.setSelectedIndices(idxArray);
    }


    public List<T> getSelectedItems() {
        return new ArrayList<>(selectedSet);
    }


    public void setSelectedItems(Collection<T> items) {
        selectedSet.clear();
        if (items != null) selectedSet.addAll(items);
        // cập nhật textField hiển thị
        String text = selectedSet.stream().map(toDisplay).collect(Collectors.joining(", "));
        textField.setText(text);
    }


    public void setItems(T[] items) {
        this.items = items == null ? (T[]) new Object[0] : items;
        if (listModel != null) {
            listModel.clear();
            for (T item : this.items) listModel.addElement(item);
            applySelectedToList();
        }

        String text = selectedSet.stream().map(toDisplay).collect(Collectors.joining(", "));
        textField.setText(text);
    }

    public void setItems(List<T> items) {
        setItems(items == null ? null : (T[]) items.toArray());
    }


    public String getText() {
        return textField.getText();
    }

    public void removeTextFieldMouseListeners() {
        for (MouseListener ml : textField.getMouseListeners()) {
            textField.removeMouseListener(ml);
        }
    }

}
