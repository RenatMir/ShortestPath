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
    private Node start;
    private Node finish;

    private Node[][] matrix;

    JPanel leftPanel;
    JButton startButton;
    JButton cleatButton;
    JComboBox<String> functions;
    JComboBox<String> shortestPathAlgorithm;
    JSlider matrixSize;
    JLabel functionsText;
    JLabel algorithm;
    JLabel distanceText;
    JLabel distanceNumber;

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
        startButton.setBounds(40,35,120,25);

        cleatButton = new JButton("Clear");
        cleatButton.setFocusable(false);
        cleatButton.addActionListener(this);
        cleatButton.setBounds(40,HEIGHT - 100,120,25);

        String[] function = {"Start", "Finish", "Create obstacles"};
        functions = new JComboBox<>(function);
        functions.setBounds(40,100,120,25);
        functions.addActionListener(this);
        functions.setFocusable(false);
        functionsText = new JLabel("Function : ");
        functionsText.setBounds(40, 80,120,15);

        String[] chooseAlgorithm = {"A* algorithm", "Dijkstra algorithm"};
        shortestPathAlgorithm = new JComboBox<>(chooseAlgorithm);
        shortestPathAlgorithm.setBounds(40,150,120,25);
        shortestPathAlgorithm.addActionListener(this);
        shortestPathAlgorithm.setFocusable(false);
        algorithm = new JLabel("Algorithm : ");
        algorithm.setBounds(40, 130,120,15);

        matrixSize = new JSlider(5,35, 20);
        matrixSize.setBounds(40,215, 120,35);
        matrixSize.setMajorTickSpacing(5);
        matrixSize.setPaintTicks(true);
        matrixSize.addChangeListener(this);

        distanceText = new JLabel("Distance: ");
        distanceText.setBounds(40, HEIGHT-150, 70,15);
        distanceNumber = new JLabel();
        distanceNumber.setBounds(120, HEIGHT-150, 50,15);

        leftPanel.add(functions);
        leftPanel.add(startButton);
        leftPanel.add(cleatButton);
        leftPanel.add(matrixSize);
        leftPanel.add(functionsText);
        leftPanel.add(shortestPathAlgorithm);
        leftPanel.add(algorithm);
        leftPanel.add(distanceText);
        leftPanel.add(distanceNumber);



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
    public void setDistance(double distance){
        distanceNumber.setText("" + (int)distance);
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
        setStart(null);
        setFinish(null);
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
        if(getStart() != null) {
            g.setColor(Color.green);
            g.fillRect(getStart().y * CELL_WIDTH + 1, getStart().x * CELL_WIDTH + 1, CELL_WIDTH - 1, CELL_WIDTH - 1);
        }
        if(getFinish() != null) {
            g.setColor(Color.red);
            g.fillRect(getFinish().y * CELL_WIDTH + 1, getFinish().x * CELL_WIDTH + 1, CELL_WIDTH - 1, CELL_WIDTH - 1);
        }
    }
    public Node getStart(){
        return start;
    }
    public Node getFinish(){
        return finish;
    }
    public void setFinish(Node node){
        finish = node;
    }
    public void setStart(Node node){
        start = node;
    }
    public Node[][] getMatrix(){
        return matrix;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(Objects.equals(functions.getSelectedItem(), "Start")) {
            int startY = e.getX() / CELL_WIDTH;
            int startX = e.getY() / CELL_WIDTH;

            if(!matrix[startX][startY].isWall) {
                setStart(matrix[startX][startY]);
            }
        }
        else if(Objects.equals(functions.getSelectedItem(), "Finish")){
            int endY = e.getX() / CELL_WIDTH;
            int endX = e.getY() / CELL_WIDTH;

            if(!matrix[endX][endY].isWall)
                setFinish(matrix[endX][endY]);
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton){
            if(getStart() != null && getFinish() != null)
                MyFrame.chooseAlgorithm(shortestPathAlgorithm.getSelectedItem().toString());
        }
        if(e.getSource() == cleatButton){
            clearMatrix();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(functions.getSelectedItem() == "Create obstacles") {
            int Y = e.getX() / CELL_WIDTH;
            int X = e.getY() / CELL_WIDTH;

            matrix[X][Y].isWall = true;
            repaint();
        }
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
    @Override
    public void mouseMoved(MouseEvent e) {

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
}