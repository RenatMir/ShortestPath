package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Random;

public class ContentPane extends JPanel implements MouseListener, ActionListener, MouseMotionListener, ChangeListener {

    private static final int LEFT_PANEL_WIDTH = 200;
    private static final int RIGHT_PANEL_WIDTH = 600;
    private static final int HEIGHT = 600;
    private static int NUM_OF_COLS = 12;
    private static int CELL_WIDTH = RIGHT_PANEL_WIDTH / NUM_OF_COLS;

    Random random;
    protected Node start;
    protected Node finish;

    private Node[][] matrix;

    JPanel leftPanel;
    JButton startButton;
    JButton cleatButton;
    JComboBox<String> Functions;
    JSlider matrixSize;

    ContentPane(){
        setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);

        random = new Random();

        leftPanel = new JPanel();
        leftPanel.setBackground(Color.lightGray);
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, HEIGHT));

        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH,HEIGHT));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(
                BorderFactory.createTitledBorder("Controls"));

        startButton = new JButton("Start");
        startButton.setFocusable(false);
        startButton.addActionListener(this);
        startButton.setBounds(40,20,120,25);

        cleatButton = new JButton("Clear");
        cleatButton.setFocusable(false);
        cleatButton.addActionListener(this);
        cleatButton.setBounds(40,175,120,25);

        String[] string = {"Start", "Finish", "Create obstacles"};
        Functions = new JComboBox<>(string);
        Functions.setBounds(40,100,120,25);
        Functions.addActionListener(this);

        matrixSize = new JSlider(5,35, 20);
        matrixSize.setBounds(40,215, 120,25);
        matrixSize.setMajorTickSpacing(5);
        matrixSize.setPaintTicks(true);
        matrixSize.addChangeListener(this);

        leftPanel.add(Functions);
        leftPanel.add(startButton);
        leftPanel.add(cleatButton);
        leftPanel.add(matrixSize);



        initializeMatrix();
        //randomObstacles();
    }

    public void initializeMatrix(){
        matrix = new Node[NUM_OF_COLS][NUM_OF_COLS];
        for(int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                matrix[i][j] = new Node(i, j);
            }
        }
    }
    public void randomObstacles(){
        for(int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                if(random.nextInt(10) < 3)
                    matrix[i][j].isWall = true;
            }
        }
    }
    public void clearMatrix(){
        initializeMatrix();
        start = null;
        finish = null;
        repaint();

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
        if(start != null) {
            g.setColor(Color.green);
            g.fillRect(start.y * CELL_WIDTH + 1, start.x * CELL_WIDTH + 1, CELL_WIDTH - 1, CELL_WIDTH - 1);
        }
        if(finish != null) {
            g.setColor(Color.red);
            g.fillRect(finish.y * CELL_WIDTH + 1, finish.x * CELL_WIDTH + 1, CELL_WIDTH - 1, CELL_WIDTH - 1);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(Objects.equals(Functions.getSelectedItem(), "Start")) {
            int startY = e.getX() / CELL_WIDTH;
            int startX = e.getY() / CELL_WIDTH;

            if(!matrix[startX][startY].isWall) {
                start = matrix[startX][startY];
            }
        }
        else if(Objects.equals(Functions.getSelectedItem(), "Finish")){
            int endY = e.getX() / CELL_WIDTH;
            int endX = e.getY() / CELL_WIDTH;

            if(!matrix[endX][endY].isWall)
                finish = matrix[endX][endY];
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton){
            if(start != null && finish != null)
                new A_Star_Algorithm().a_Star(matrix, start, finish, this);
        }
        if(e.getSource() == cleatButton){
            clearMatrix();
        }
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
    public void mouseDragged(MouseEvent e) {
        if(Functions.getSelectedItem() == "Create obstacles") {
            int Y = e.getX() / CELL_WIDTH;
            int X = e.getY() / CELL_WIDTH;

            matrix[X][Y].isWall = true;
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == matrixSize){
            NUM_OF_COLS = matrixSize.getValue();
            CELL_WIDTH = RIGHT_PANEL_WIDTH / NUM_OF_COLS;
            initializeMatrix();
            repaint();
        }
    }
}