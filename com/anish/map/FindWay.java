package com.anish.map;

import java.util.Stack;
//import java.util.Arrays;

public class FindWay {

    private int[][] ways = new int[30][30];
    private int dimension;

    private int[][] direction=new int[4][2];
    
    public void load(int[][] a) {
        
        this.dimension = 30;
        direction[0][0] = -1;
        direction[0][1] = 0;
        direction[1][0] = 1;
        direction[1][1] = 0;
        direction[2][0] = 0;
        direction[2][1] = -1;
        direction[3][0] = 0;
        direction[3][1] = 1;

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (a[i][j] == 1) {
                    ways[i][j] = 0;
                } else {
                    ways[i][j] = -1;
                }

            }
        }
        //System.out.println("PRINT THE MAZE");
        //for (int i = 0; i < 30; i++) {
        //    System.out.println(Arrays.toString(ways[i]));
        //}
        
        find();
    }

    public Stack<Node> route = new Stack<>();

    void find() {
        ways[0][0] = 1;
        Stack<Node> stack = new Stack<>();
        int nextx, nexty, i, curx, cury;
        stack.push(new Node(0, 0));
        while (!stack.empty()) {
            Node cur = stack.pop();
            if (cur.x == dimension - 1 && cur.y == dimension - 1)
                break;
            for (i = 0; i < 4; i++) {
                nextx = cur.x + direction[i][0];
                nexty = cur.y + direction[i][1];
                if (ValidNode(nextx, nexty)) {
                    if (ways[nextx][nexty] == 0) {
                        ways[nextx][nexty] = ways[cur.x][cur.y] + 1;
                        Node next = new Node(nextx, nexty);
                        stack.push(next);
                    }
                }
            }
        }
        
        
        route.push(new Node(dimension - 1, dimension - 1));
        curx = dimension - 1;
        cury = dimension - 1;

        //System.out.println("PRINT THE WAYS");
        //for (i = 0; i < 30; i++) {
        //    System.out.println(Arrays.toString(ways[i]));
        //}

        while (curx != 0 || cury != 0) {
            //System.out.println(curx + "  " + cury);
            if (curx == 0 && cury == 0) {
                break;
            }
            for (i = 0; i < 4; i++) {
                nextx = curx + direction[i][0];
                nexty = cury + direction[i][1];
                if (ValidNode(nextx, nexty)) {
                    if (ways[nextx][nexty] == ways[curx][cury] - 1) {
                        route.push(new Node(nextx, nexty));
                        curx = nextx;
                        cury = nexty;
                        break;
                    }
                }
            }
        }

    }

    private Boolean ValidNode(int x,int y) {
        return x >= 0 && y >= 0 && x < dimension && y < dimension;
    }


}
