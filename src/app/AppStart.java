package app;

import view.Scene;
import view.Window;

import javax.swing.*;

public class AppStart {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
//            new Controller3D(window.getPanel());
            window.setVisible(true);
            Scene scene = new Scene(800,600);
            scene.start();
        });

        // https://www.google.com/search?q=SwingUtilities.invokeLater
        // https://www.javamex.com/tutorials/threads/invokelater.shtml
    }
}