package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import java.util.Stack;
import java.util.Arrays;

import com.anish.calabashbros.Wall;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;

import com.anish.map.MazeGenerator;
import com.anish.map.Node;
import com.anish.map.FindWay;

public class MazeScreen implements Screen {
    
    private World world;
    private Stack<Node> route;
    private Calabash player = new Calabash(new Color(255, 255, 255), 1, world);
    private Wall wall = new Wall(world);
    private int[][] a = new int[30][30];
    private int curx,cury;

    public MazeScreen() {

        world = new World();
        curx = 0;
        cury = 0;

        MazeGenerator mazeGenerator = new MazeGenerator(30);
        FindWay find = new FindWay();
        mazeGenerator.generateMaze();
        System.out.println("SYMBOLIC MAZE\n" + mazeGenerator.getSymbolicMaze());
        
        a = mazeGenerator.getArr();
        find.load(a);
        this.route = find.route;

        world.put(player, 0, 0);

        for (int i = 0; i < 30; i++) {
            System.out.println(Arrays.toString(a[i]));
            for (int j = 0; j < 30; j++) {
                if (a[i][j] != 1) {
                    world.put(wall, i, j);
                }
            }
        }
        
    }

    public void move(int nextx, int nexty) {
        world.remove(curx, cury);
        curx = nextx;
        cury = nexty;
        world.put(player, nextx, nexty);
    }
    
    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (!this.route.empty()) {
            Node next = route.pop();
            System.out.println(next.x + " " + next.y);
            move(next.x, next.y);
        }

        return this;
    }
}
