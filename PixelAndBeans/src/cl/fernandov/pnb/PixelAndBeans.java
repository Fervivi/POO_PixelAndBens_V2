package cl.fernandov.pnb;

import cl.fernandov.pnb.gui.LoginFrame;

import javax.swing.SwingUtilities;


public class PixelAndBeans {


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}