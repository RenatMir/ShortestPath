package com.company;

public class Node {
    int x, y;
    boolean isWall;
    boolean open;
    boolean closed;
    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString(){
        return ("(x: " + x + " y: " + y  + ")");
    }
}

