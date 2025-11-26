package cl.fernando.pnb.gui;

import javax.swing.SwingUtilities;


public class PixelAndBeans {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}