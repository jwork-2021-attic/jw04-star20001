
关于第一问我只修改了WorldScreen的代码

将bros改写成二维矩阵形式

具体代码如下

```
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
                world.put(bros[i][j], i, j);
                brosline[i * num + j] = bros[i][j];
                System.out.println(i * num + j);
            
            }
        }
 
        BubbleSorter<Calabash> b = new BubbleSorter<>();
        b.load(brosline);
        b.sort();

        sortSteps = this.parsePlan(b.getPlan());
        
    }
    
    
