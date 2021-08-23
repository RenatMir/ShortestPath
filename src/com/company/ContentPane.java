package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ContentPane extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

    private static final int LEFT_PANEL_WIDTH = 200;
    private static final int RIGHT_PANEL_WIDTH = 600;
    private static final int HEIGHT = 600;
    private static int NUM_OF_COLS = 8;
    private static int CELL_WIDTH = RIGHT_PANEL_WIDTH / NUM_OF_COLS;

    protected Node start;
    protected Node finish;

    private Node[][] matrix;
    JPanel leftPanel;

    ContentPane(){
        setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);

        leftPanel = new JPanel();
        leftPanel.setBackground(Color.lightGray);
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, HEIGHT));

        initializeMatrix();

        start = matrix[0][0];
        finish = matrix[7][7];

        new A_Star_Algorithm().a_Star(matrix, start, finish, this);

    }

    public void initializeMatrix(){
        matrix = new Node[NUM_OF_COLS][NUM_OF_COLS];
        for(int i = 0; i < NUM_OF_COLS; i++)
            for(int j = 0; j < NUM_OF_COLS; j++)
                matrix[i][j] = new Node(i,j);
        matrix[5][5].isWall = true;
        matrix[4][5].isWall = true;
        matrix[6][6].isWall = true;
        matrix[7][6].isWall = true;

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        for (int i = 0; i < NUM_OF_COLS;i++){
            g.drawLine(CELL_WIDTH*i, 0, CELL_WIDTH * i, HEIGHT);
            g.drawLine(0, CELL_WIDTH*i, RIGHT_PANEL_WIDTH, CELL_WIDTH*i);
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j].isWall)
                    g.setColor(Color.BLACK);
                else if(matrix[i][j].closed)
                    g.setColor(Color.YELLOW);
                else if(matrix[i][j].open)
                    g.setColor(Color.BLUE);
                else g.setColor(Color.WHITE);
                g.fillRect(j*CELL_WIDTH + 1,i * CELL_WIDTH + 1,CELL_WIDTH-1, CELL_WIDTH-1);
            }
        }
        g.setColor(Color.green);
        g.fillRect(1, 1, CELL_WIDTH-1, CELL_WIDTH-1);

        g.setColor(Color.red);
        g.fillRect(CELL_WIDTH * NUM_OF_COLS - CELL_WIDTH + 1, CELL_WIDTH * NUM_OF_COLS - CELL_WIDTH + 1, CELL_WIDTH-1, CELL_WIDTH-1);
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