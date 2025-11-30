package com.ptit.librarymanagement.presentation.contentview.component;



import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
public class FunctionToolBar extends JToolBar {
    private final HashMap<String, ButtonToolBar> buttons;
    public FunctionToolBar(HashMap<String, ButtonToolBar> buttons) {
        this.buttons = buttons;
        decorateToolBar();
        initComponent();

    }
    private void decorateToolBar () {
        this.setBackground(Color.WHITE);
        this.setFloatable(false);
        this.setRollover(true);
        this.setBorder(null);
    }
    private void initComponent () {
        for (Map.Entry<String, ButtonToolBar> m : buttons.entrySet()) {
            this.add(m.getValue());
            m.getValue().setEnabled(false);
        }
    }
}
