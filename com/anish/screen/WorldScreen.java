package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.anish.calabashbros.SelectSorter;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;
import com.anish.calabashbros.RandomArray;
import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    private Calabash[][] bros;
    private Calabash[] brosline;
    String[] sortSteps;

    private int num = 16;
    
    public WorldScreen() {
        world = new World();
        bros = new Calabash[num][num];
        brosline = new Calabash[num*num];
    
        int i, j;
        RandomArray arr = new RandomArray(num*num);
        arr.getRandom();
        for (i = 0; i < num; i++) {
            for (j = 0; j < num; j++) {

                int r, b, g;
                Random rand = new Random();
                r = rand.nextInt(256);
                b = rand.nextInt(256);
                g = rand.nextInt(256);

                bros[i][j] = new Calabash(new Color(r, g, b), arr.a[i * num + j], world);
                world.put(bros[i][j], 10 + 2 * i, 10 + 2 * j);
                brosline[i * num + j] = bros[i][j];
                System.out.println(i * num + j);
            
            }
        }
 
        SelectSorter<Calabash> b = new SelectSorter<>();
        b.load(brosline);
        b.sort();

        sortSteps = this.parsePlan(b.getPlan());
        
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Calabash[][] bros, String step) {
        String[] couple = step.split("<->");
        getBroByRank(bros, Integer.parseInt(couple[0])).swap(getBroByRank(bros, Integer.parseInt(couple[1])));
    }

    private Calabash getBroByRank(Calabash[][] bros, int rank) {
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (bros[i][j].getRank() == rank) {
                    return bros[i][j];
                }
            }
        }
        return null;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (i < this.sortSteps.length) {
            this.execute(bros, sortSteps[i]);
            i++;
        }

        return this;
    }

}
