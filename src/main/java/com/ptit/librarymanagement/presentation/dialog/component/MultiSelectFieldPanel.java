package com.ptit.librarymanagement.presentation.dialog.component;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;

@Getter
public class MultiSelectFieldPanel<T> extends JPanel {

    private JLabel label;
    private MultiSelectForm<T> field;
    private final Function<T, String> toDisplay;
    public MultiSelectFieldPanel(String labelText, T[] items, Function<T, String> toDisplay) {
        this.toDisplay = Objects.requireNonNull(toDisplay);

        initUi(labelText);
        this.field = new MultiSelectForm<>(labelText, items, toDisplay);
        field.setBackground(Color.WHITE);
        field.setBorder(new EmptyBorder(0, 10, 5, 10));
        field.setOpaque(true);
        this.add(field);
    }

    public MultiSelectFieldPanel(String labelText, List<T> items, Function<T, String> toDisplay) {
        this.toDisplay = Objects.requireNonNull(toDisplay);
        initUi(labelText);
        this.field = new MultiSelectForm<>(labelText, items, toDisplay);
        field.setOpaque(true);
        this.add(field);
    }

    private void initUi(String labelText) {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(0, 10, 5, 10));
        this.setPreferredSize(new Dimension(100, 100));

        label = new JLabel(labelText);
        this.add(label);

    }


    public Set<T> getSelectedItems() {
        List<T> list = field.getSelectedItems();
        return new LinkedHashSet<>(list);
    }


    public void setSelectedItems(Collection<T> items) {
        field.setSelectedItems(items);
    }


    public void setItems(T[] items) {

        Set<T> selected = getSelectedItems();

        field.setItems(items);

        field.setSelectedItems(selected);
    }

    public void setItems(List<T> items) {
        Set<T> selected = getSelectedItems();
        field.setItems(items);
        field.setSelectedItems(selected);
    }


    public void setLabelText(String text) {
        label.setText(text);
    }


}
