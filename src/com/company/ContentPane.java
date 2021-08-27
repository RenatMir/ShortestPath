package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.Random;

public class ContentPane extends JPanel implements MouseListener, ActionListener, MouseMotionListener, ChangeListener {

    private static final int LEFT_PANEL_WIDTH = 200;
    private static final int RIGHT_PANEL_WIDTH = 600;
    private static final int HEIGHT = 600;
    private static int NUM_OF_COLS = 20;
    private static double CELL_WIDTH = RIGHT_PANEL_WIDTH / (double)NUM_OF_COLS;
    private static int denseValue = 3;
    private static long delayValue = 10000000;
    private static boolean isStarted;
    private long startTimer;
    private long finishTimer;
    private long timeElapsed;
    private long delayStart;
    private long delayElapsed;

    Random random;
    private Node start;
    private Node finish;
    private Node[][] matrix;

    JPanel leftPanel;
    JButton startButton;
    JButton cleatButton;
    JButton shuffleMatrix;
    JComboBox<String> functions;
    JComboBox<String> shortestPathAlgorithm;
    JSlider matrixSize;
    JSlider delay;
    JSlider dense;
    JLabel functionsText;
    JLabel algorithm;
    JLabel distanceText;
    JLabel distanceNumber;
    JLabel matrixSizeText;
    JLabel delayText;
    JLabel denseText;

    ContentPane(){
        setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);

        random = new Random();


        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH,HEIGHT));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(
                BorderFactory.createTitledBorder("Controls"));

        startButton = new JButton("Start");
        startButton.setFocusable(false);
        startButton.addActionListener(this);
        startButton.setBounds(40,HEIGHT - 560,120,25);

        cleatButton = new JButton("Clear");
        cleatButton.setFocusable(false);
        cleatButton.addActionListener(this);
        cleatButton.setBounds(40,HEIGHT - 150,120,25);

        shuffleMatrix = new JButton("Shuffle");
        shuffleMatrix.setFocusable(false);
        shuffleMatrix.addActionListener(this);
        shuffleMatrix.setBounds(40,HEIGHT - 200,120,25);

        String[] function = {"Start", "Finish", "Create obstacles"};
        functions = new JComboBox<>(function);
        functions.setBounds(40,HEIGHT - 430,120,25);
        functions.addActionListener(this);
        functions.setFocusable(false);
        functionsText = new JLabel("Function : ");
        functionsText.setBounds(40, HEIGHT - 450,120,15);

        String[] chooseAlgorithm = {"A* algorithm", "Dijkstra algorithm"};
        shortestPathAlgorithm = new JComboBox<>(chooseAlgorithm);
        shortestPathAlgorithm.setBounds(40,HEIGHT - 495,120,25);
        shortestPathAlgorithm.addActionListener(this);
        shortestPathAlgorithm.setFocusable(false);
        algorithm = new JLabel("Algorithm : ");
        algorithm.setBounds(40, HEIGHT - 515,120,15);

        matrixSize = new JSlider(5,35, NUM_OF_COLS);
        matrixSize.setBounds(40,HEIGHT - 360, 120,35);
        matrixSize.setMajorTickSpacing(5);
        matrixSize.setPaintTicks(true);
        matrixSize.setBackground(Color.WHITE);
        matrixSize.addChangeListener(this);
        matrixSizeText = new JLabel("Size");
        matrixSizeText.setBounds(40,HEIGHT - 380, 120,15);

        delay = new JSlider(0,100000000, (int) delayValue);
        delay.setBounds(40,HEIGHT - 300, 120,35);
        delay.setMajorTickSpacing(20000000);
        delay.setPaintTicks(true);
        delay.setBackground(Color.WHITE);
        delay.addChangeListener(this);
        delayText = new JLabel("Delay");
        delayText.setBounds(40,HEIGHT - 320, 120,15);

        dense = new JSlider(1,9, denseValue);
        dense.setBounds(40,HEIGHT - 240, 120,35);
        dense.setMajorTickSpacing(2);
        dense.setPaintTicks(true);
        dense.setBackground(Color.WHITE);
        dense.addChangeListener(this);
        denseText = new JLabel("Dense");
        denseText.setBounds(40,HEIGHT - 260, 120,15);

        distanceText = new JLabel("Distance: ");
        distanceText.setBounds(40, HEIGHT-75, 70,15);
        distanceNumber = new JLabel();
        distanceNumber.setBounds(120, HEIGHT-75, 50,15);

        leftPanel.add(functions);
        leftPanel.add(startButton);
        leftPanel.add(cleatButton);
        leftPanel.add(matrixSize);
        leftPanel.add(functionsText);
        leftPanel.add(shortestPathAlgorithm);
        leftPanel.add(algorithm);
        leftPanel.add(distanceText);
        leftPanel.add(distanceNumber);
        leftPanel.add(matrixSizeText);
        leftPanel.add(delay);
        leftPanel.add(delayText);
        leftPanel.add(shuffleMatrix);
        leftPanel.add(dense);
        leftPanel.add(denseText);

        initializeMatrix();

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
        clearMatrix();
        for(int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                if(random.nextInt(10) <= denseValue)
                    matrix[i][j].isWall = true;
            }
        }
        repaint();
    }
    public void clearMatrix(){
        initializeMatrix();
        setStart(null);
        setFinish(null);
        repaint();

    }
    public void waitRepaint(){
        setDelayStart(System.nanoTime());
        do{
            setDelayElapsed(System.nanoTime() - getDelayStart());
        }while(getDelayElapsed() < getDelayValue());
        repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.black);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                g2d.draw(new Rectangle2D.Double(j * CELL_WIDTH, i * CELL_WIDTH, CELL_WIDTH, CELL_WIDTH));
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j].isWall) {
                    g.setColor(Color.BLACK);
                    g2d.fill(new Rectangle2D.Double(j* CELL_WIDTH,i * CELL_WIDTH, CELL_WIDTH, CELL_WIDTH));
                }
                else if(matrix[i][j].closed) {
                    g.setColor(Color.YELLOW);
                    g2d.fill(new Rectangle2D.Double(j* CELL_WIDTH +1,i * CELL_WIDTH +1, CELL_WIDTH -1, CELL_WIDTH -1));
                }
                else if(matrix[i][j].open) {
                    g.setColor(Color.BLUE);
                    g2d.fill(new Rectangle2D.Double(j * CELL_WIDTH +1, i * CELL_WIDTH +1, CELL_WIDTH -1, CELL_WIDTH -1));
                }
            }
        }

        if(getStart() != null) {
            g2d.setColor(Color.green);
            g2d.fill(new Rectangle2D.Double(getStart().y * CELL_WIDTH + 1, getStart().x * CELL_WIDTH + 1, CELL_WIDTH - 1, CELL_WIDTH - 1));
        }
        if(getFinish() != null) {
            g2d.setColor(Color.red);
            g2d.fill(new Rectangle2D.Double(getFinish().y * CELL_WIDTH + 1, getFinish().x * CELL_WIDTH + 1, CELL_WIDTH - 1, CELL_WIDTH - 1));
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
    public static void setIsStarted(boolean isStarted) {
        ContentPane.isStarted = isStarted;
    }
    public static long getDelayValue() {
        return delayValue;
    }

    public static void setDelayValue(int delayValue) {
        ContentPane.delayValue = delayValue;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(Objects.equals(functions.getSelectedItem(), "Start") && !isStarted) {
            int startY = (int)(e.getX() / CELL_WIDTH);
            int startX = (int)(e.getY() / CELL_WIDTH);

            if(!matrix[startX][startY].isWall) {
                setStart(matrix[startX][startY]);
            }
        }
        else if(Objects.equals(functions.getSelectedItem(), "Finish") && !isStarted){
            int endY = (int) (e.getX() / CELL_WIDTH);
            int endX = (int) (e.getY() / CELL_WIDTH);

            if(!matrix[endX][endY].isWall)
                setFinish(matrix[endX][endY]);
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton && !isStarted){
            if(getStart() != null && getFinish() != null)
                MyFrame.chooseAlgorithm(shortestPathAlgorithm.getSelectedItem().toString());
        }
        if(e.getSource() == cleatButton){
            A_Star_Algorithm.stop();
            clearMatrix();
        }
        if(e.getSource() == shuffleMatrix && !isStarted){
            randomObstacles();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(functions.getSelectedItem() == "Create obstacles" && !isStarted) {
            int Y = (int)(e.getX() / CELL_WIDTH);
            int X = (int)(e.getY() / CELL_WIDTH);
            if(matrix[X][Y] != start && matrix[X][Y] != finish) {
                matrix[X][Y].isWall = true;
                repaint();
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == matrixSize && !isStarted){
            NUM_OF_COLS = matrixSize.getValue();
            CELL_WIDTH = (double) RIGHT_PANEL_WIDTH / (double)NUM_OF_COLS;
            clearMatrix();
            initializeMatrix();
            repaint();
        }
        if(e.getSource() == dense && !isStarted){
            denseValue = dense.getValue();
        }
        if(e.getSource() == delay && !isStarted){
            setDelayValue(delay.getValue());
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

    public long getStartTimer() {
        return startTimer;
    }

    public void setStartTimer(long startTimer) {
        this.startTimer = startTimer;
    }

    public long getFinishTimer() {
        return finishTimer;
    }

    public void setFinishTimer(long finishTimer) {
        this.finishTimer = finishTimer;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public long getDelayStart() {
        return delayStart;
    }

    public void setDelayStart(long delayStart) {
        this.delayStart = delayStart;
    }

    public long getDelayElapsed() {
        return delayElapsed;
    }

    public void setDelayElapsed(long delayElapsed) {
        this.delayElapsed = delayElapsed;
    }
}