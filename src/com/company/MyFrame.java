package com.company;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    ContentPane pane;
    MyFrame(){
        pane = new ContentPane();

        add(pane, BorderLayout.CENTER);
        add(pane.leftPanel, BorderLayout.WEST);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
