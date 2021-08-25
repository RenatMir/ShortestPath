package com.company;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    private static ContentPane pane;
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
    public static void main(String[] args){
        new MyFrame();
    }
    public static void chooseAlgorithm(String choice){
        switch (choice) {
            case "A* algorithm" -> {
                new A_Star_Algorithm(pane, pane.getStart(), pane.getFinish(), pane.getMatrix());
                break;
            }
            case "Dijkstra algorithm" -> {
                //new A_Star_Algorithm(pane, pane.getStart(), pane.getFinish());
                break;
            }
        }
    }
}
