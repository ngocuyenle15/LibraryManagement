package com.ptit.librarymanagement.presentation.contentview.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;

import javax.swing.*;
import java.awt.*;

public class ButtonToolBar extends JButton{
    
    public ButtonToolBar(String text, String pathIcon) {
        decorateButton(text, pathIcon);
    }

    private void decorateButton(String text, String pathIcon) {
        this.setFont(new Font(FlatRobotoFont.FAMILY, 1, 14));
        this.setForeground(new Color(1, 88, 155));
        this.setIcon(new FlatSVGIcon(pathIcon));
        this.setText(text);
        this.setFocusable(false);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.putClientProperty("JButton.buttonType", "toolBarButton");
    }


}
