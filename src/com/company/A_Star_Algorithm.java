package com.company;
import javax.swing.*;
import java.util.*;

public class A_Star_Algorithm {
    boolean hasNeighbor;
    boolean[][] isVisited;
    int length;
    Node start;
    Node finish;
    ContentPane pane;


    public void a_Star(Node[][] matrix, Node start, Node finish, ContentPane pane){
        this.pane = pane;
        this.start = start;
        start.setParent(null);
        this.finish = finish;
        length = matrix.length;
        isVisited = new boolean[length][length];

        var swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground(){

                Comparator<Node> stringLengthComparator = new Comparator<Node>() {
                    public int compare(Node node1, Node node2) {
                        int f1 = calculateF(node1.x, node1.y);
                        int f2 = calculateF(node2.x, node2.y);
                        int compareResult = 0;
                        if(f1 < f2)
                            compareResult = - 1;
                        else if(f1 > f2)
                            compareResult = 1;
                        else if(calculateH(node1.x, node1.y) < calculateH(node2.x, node2.y))
                            compareResult = -1;

                        return compareResult;
                    }
                };

                List<Node> closed = new ArrayList<>();
                Queue<Node> open = new PriorityQueue<>(stringLengthComparator);

                open.add(start);

                do{
                    var current = open.remove();

                    if(matrix[current.x][current.y] == finish) {
                        System.out.println("Finish");

                        while(matrix[current.x][current.y].getParent() != null){
                            closed.add(matrix[current.x][current.y]);
                            matrix[current.x][current.y].closed = true;
                            try {
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            pane.repaint();
                            matrix[current.x][current.y] = matrix[current.x][current.y].getParent();
                        }
                        closed.add(start);
                        Collections.reverse(closed);
                        System.out.println(closed);
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
                }while (!open.isEmpty());
                return null;
            }
        };swingWorker.execute();

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
        checkNodeValid(current, -1,-1,matrix, open);
        checkNodeValid(current, -1,0,matrix, open);
        checkNodeValid(current, -1,1,matrix, open);
        checkNodeValid(current, 0,-1,matrix, open);
        checkNodeValid(current, 0,1,matrix, open);
        checkNodeValid(current, 1,-1,matrix, open);
        checkNodeValid(current, 1,0,matrix, open);
        checkNodeValid(current, 1,1,matrix, open);
        if(!hasNeighbor)
            open.remove(current);
    }
    public void checkNodeValid(Node current, int x, int y, Node[][] matrix, Queue<Node> open){


        if(((current.x + x >= 0) && (current.x + x <= length-1))
                && ((current.y + y >= 0) && (current.y + y <= length-1)
                && !matrix[current.x + x][current.y + y].isWall)
                && !isVisited[current.x + x][current.y + y]){

            hasNeighbor = true;
            open.add(matrix[current.x + x][current.y + y]);
            matrix[current.x + x][current.y + y].open = true;
            matrix[current.x + x][current.y + y].setParent(current);

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pane.repaint();

            if(matrix[current.x + x][current.y + y] != finish)
                isVisited[current.x + x][current.y + y] = true;
        }
    }

}
