package com.ptit.librarymanagement.presentation.mainview.main.component;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private CardLayout cardLayout;
    public ContentPanel() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
    }
    public void setPanelDisplay (String viewName) {
        this.cardLayout.show(this, viewName);
    }

}
