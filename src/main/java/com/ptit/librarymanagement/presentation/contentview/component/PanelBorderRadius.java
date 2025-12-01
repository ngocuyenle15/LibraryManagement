
package com.ptit.librarymanagement.presentation.contentview.component;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class PanelBorderRadius extends JPanel{
    private int shadowSize = 3;
    private Color HowerBackgroundColor = new Color(187, 222, 251);

    public PanelBorderRadius() {
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        createShadow(graphics);
    }

    private void createShadow(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        int size = shadowSize *2;
        int x = 0;
        int y = 0;
        int width = getWidth() - size;
        int height = getHeight() - size;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g2.setBackground(HowerBackgroundColor);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRoundRect(0, 0, width,  height, 15, 15);
        g2.drawImage(img, x, y, null);
    }

    public int getShadowSize() {
        return shadowSize;
    }

    public void setShadowSize(int shadowSize) {
        this.shadowSize = shadowSize;
    }

    public Color getHowerBackgroundColor() {
        return HowerBackgroundColor;
    }

    public void setHowerBackgroundColor(Color howerBackgroundColor) {
        HowerBackgroundColor = howerBackgroundColor;
    }
}
