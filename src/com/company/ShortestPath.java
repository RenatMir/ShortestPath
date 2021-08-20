package com.company;

import java.util.*;

public class ShortestPath {
    int startX, startY;
    int endX, endY;
    boolean hasNeighbor;

    public void Dijkstra(char[][] matrix){
        startX = 0;
        startY = 0;
        endX = 6;
        endY = 7;

        var start = new Node(startX,startY);
        var end = new Node(endX, endY);

        var x = new Node(3, 3);
        System.out.println(x.g);
        var y = new Node(2, 4);
        System.out.println(y.g);

        Comparator<Node> stringLengthComparator = new Comparator<Node>() {

            public int compare(Node node1, Node node2) {
                int compareResult = 0;
                if(node1.f < node2.f)
                    compareResult = - 1;
                else if(node1.f > node2.f)
                    compareResult = 1;
                else if(node1.h < node2.h)
                    compareResult = -1;

                return compareResult;
            }
        };

        List<Node> closed = new LinkedList<>();
        Queue<Node> open = new PriorityQueue<>(stringLengthComparator);

        closed.add(start);
        open.add(start);


        while(!open.isEmpty()){

            var current = open.remove();

            if(matrix[current.x][current.y] == 'D' ) {
                System.out.println(closed);
                System.out.println("Finish");
                break;
            }
            else {
                matrix[current.x][current.y]='0';
                chooseNeighbor(current, matrix, open);
                closed.add(open.peek());
            }
        }
    }

    public void chooseNeighbor(Node current, char[][] matrix, Queue<Node> open){
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
    public void checkNodeValid(Node current, int x, int y, char[][] matrix, Queue<Node> open){
        Node node;

        if(((current.x + x >= 0) && (current.x + x <= matrix.length-1))
            &&((current.y + y >= 0) && (current.y + y <= matrix[0].length-1)
            && matrix[current.x + x][current.y + y] != '0')){

            node = new Node(current.x + x,current.y + y);
            hasNeighbor = true;
            open.add(node);
            if(matrix[current.x + x][current.y + y] != 'D')
                matrix[current.x + x][current.y + y]='0';
        }
    }

    public static void main(String[] args){
        char[][] matrix = {
                {'S', '0', '1', '1', '1', '1', '0','0'},
                {'1', '0', '1', '0', '0', '1', '0','0'},
                {'1', '1', '0', '1', '1', '1', '0','0'},
                {'0', '1', '0', '1', '0', '0', '1','0'},
                {'1', '0', '0', '0', '1', '0', '1','0'},
                {'0', '1', '0', '1', '0', '0', '1','0'},
                {'0', '0', '1', '0', '0', '0', '1','D'},
                {'0', '0', '0', '0', '0', '0', '0','0'},
        };
        var x = new ShortestPath();
        x.Dijkstra(matrix);
    }
    public class Node {
        int x, y;
        double g, h;
        int f;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
            h = calculateH();
            g = calculateG();
            f = (int)(calculateF()*10);
        }
        public double calculateH(){
            return Math.sqrt(Math.pow(x - endX,2) + Math.pow(y - endY,2));
        }
        public double calculateG(){
            return Math.sqrt(Math.pow(x - startX,2) + Math.pow(y - startY,2));
        }
        public double calculateF(){
            return h+g;
        }

        @Override
        public String toString(){
            return ("(x: " + x + " y: " + y  + ")");
        }
    }
}
