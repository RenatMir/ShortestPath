package com.company;
import javax.swing.*;
import java.util.*;

public class A_Star_Algorithm {
    private static SwingWorker swingWorker;
    boolean hasNeighbor;
    boolean[][] isVisited;
    int length;
    Node start;
    Node finish;
    ContentPane pane;
    Node[][] matrix;
    int distance = 0;

     A_Star_Algorithm(ContentPane pane, Node start, Node finish, Node[][] matrix){
        this.pane = pane;
        this.start = start;
        start.setParent(null);
        this.finish = finish;
        this.matrix = matrix;
        length = matrix.length;
        isVisited = new boolean[length][length];

        a_Star();
    }
    private void a_Star(){
         swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground(){

                Comparator<Node> stringLengthComparator = new Comparator<Node>() {
                    public int compare(Node node1, Node node2) {
                        int f1 = calculateF(node1.x, node1.y);
                        int f2 = calculateF(node2.x, node2.y);
                        int compareResult;
                        if(f1 < f2)
                            compareResult = - 1;
                        else if(calculateH(node1.x, node1.y) < calculateH(node2.x, node2.y))
                            compareResult = -1;
                        else
                            compareResult = 1;

                        return compareResult;
                    }
                };

                ContentPane.setIsStarted(true);
                List<Node> closed = new ArrayList<>();
                Queue<Node> open = new PriorityQueue<>(stringLengthComparator);

                open.add(start);

                while (true){
                    if(isCancelled()) {
                        ContentPane.setIsStarted(false);
                        return null;
                    }

                    var current = open.remove();
                    System.out.println(current);
                    if(matrix[current.x][current.y] == finish) {
                        System.out.println("Finish");

                        while(matrix[current.x][current.y].getParent() != null){
                            if(isCancelled()) {
                                ContentPane.setIsStarted(false);
                                return null;
                            }

                            closed.add(matrix[current.x][current.y]);
                            matrix[current.x][current.y].closed = true;

                            if(matrix[current.x][current.y].isDiagonal) {
                                distance += 14;
                            }
                            else
                                distance += 10;

                            pane.waitRepaint();
                            //System.out.println(calculateG(matrix[current.x][current.y].x,matrix[current.x][current.y].y) + ", " + distance);
                            matrix[current.x][current.y] = matrix[current.x][current.y].getParent();
                        }
                        closed.add(start);
                        Collections.reverse(closed);
                        pane.setDistance(distance);
                        break;
                    }
                    else {
                        isVisited[current.x][current.y] = true;
                        chooseNeighbor(current, matrix, open);
                    }
                    if(open.isEmpty()) {
                        System.out.println("There is no path");
                        break;
                    }
                }
                ContentPane.setIsStarted(false);
                return null;
            }
        };swingWorker.execute();

    }
    public static void stop(){
         swingWorker.cancel(true);
    }
    public double calculateH(int x, int y){
        return Math.sqrt(Math.pow(x - finish.x,2) + Math.pow(y - finish.y,2));
    }
    public double calculateG(int x, int y){
        return Math.sqrt(Math.pow(x - start.x,2) + Math.pow(y - start.y,2));
    }
    public int calculateF(int x, int y){
        return (int)(calculateH(x,y) + calculateG(x,y)) * 10;
    }

    public void chooseNeighbor(Node current, Node[][] matrix, Queue<Node> open){
        hasNeighbor = false;
        checkNodeValid(current, -1,-1,matrix, open); //left top
        checkNodeValid(current, -1,0,matrix, open);  //top
        checkNodeValid(current, -1,1,matrix, open);  //right top
        checkNodeValid(current, 0,-1,matrix, open);  //left
        checkNodeValid(current, 0,1,matrix, open);   //right
        checkNodeValid(current, 1,-1,matrix, open);  //left bottom
        checkNodeValid(current, 1,0,matrix, open);   //bottom
        checkNodeValid(current, 1,1,matrix, open);   //right bottom
        if(!hasNeighbor)
            open.remove(current);
    }
    public void checkNodeValid(Node current, int x, int y, Node[][] matrix, Queue<Node> open){


        if(((current.x + x >= 0) && (current.x + x <= length-1))
                && ((current.y + y >= 0) && (current.y + y <= length-1)
                && !matrix[current.x + x][current.y + y].isWall)
                && !isVisited[current.x + x][current.y + y]){

            matrix[current.x + x][current.y + y].isDiagonal = x != 0 && y != 0;

            hasNeighbor = true;
            open.add(matrix[current.x + x][current.y + y]);
            matrix[current.x + x][current.y + y].open = true;
            matrix[current.x + x][current.y + y].setParent(current);

            pane.waitRepaint();

            if(matrix[current.x + x][current.y + y] != finish)
                isVisited[current.x + x][current.y + y] = true;
        }
    }

}
