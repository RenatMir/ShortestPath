package com.company;

public class Node {
    int x, y;
    boolean isWall;
    boolean open;
    boolean closed;
    private Node parent;
    boolean isDiagonal;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    public Node getParent() {
        return parent;
    }

    @Override
    public String toString(){
        return ("(x: " + x + " y: " + y  + ")");
    }
}

