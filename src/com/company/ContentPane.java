package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ContentPane extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

    private static final int LEFT_PANEL_WIDTH = 200;
    private static final int RIGHT_PANEL_WIDTH = 600;
    private static final int HEIGHT = 600;
    private static int NUM_OF_COLS = 20;
    private static int CELL_WIDTH = RIGHT_PANEL_WIDTH / NUM_OF_COLS;

    private long DELAY = 100000;

    private int startX, startY;


    JPanel leftPanel;
    ContentPane(){
        setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);

        leftPanel = new JPanel();
        leftPanel.setBackground(Color.lightGray);
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, HEIGHT));
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        for (int i = 0; i < 20;i++){
            g.drawLine(CELL_WIDTH*i, 0, CELL_WIDTH * i, HEIGHT);
            g.drawLine(0, CELL_WIDTH*i, RIGHT_PANEL_WIDTH, CELL_WIDTH*i);
        }

        g.setColor(Color.green);
        g.fillRect(startY * CELL_WIDTH + 1, startX * CELL_WIDTH + 1, CELL_WIDTH-1, CELL_WIDTH-1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        /*startY = e.getX()/CELL_WIDTH;
        startX = e.getY()/CELL_WIDTH;
        System.out.println( startX+ " " + startY);
        repaint();*/
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {


    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
