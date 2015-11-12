package game;

import display.Display;
import gfx.Assets;
import gfx.ImageLoader;
import gfx.SpriteSheet;
import states.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game implements Runnable{
    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private InputHandler inputHandler;
    private BufferStrategy bs;
    private Graphics g;

    private BufferedImage img;
    private BufferedImage img2;
    private BufferedImage img3;
    private SpriteSheet sh;

    //States
    private State gameState;
    private State menuState;
    private State settingsState;

    //Player
    public static Player player =new Player(0,280);

    public static ArrayList<Enemys> ActiveEnemys = new ArrayList<Enemys>();
    public static Rectangle enemy;
   // public static int playerLifes = 5000;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }


    private void init() {


        display = new Display(this.title, this.width, this.height);
        img = ImageLoader.loadImage("/textures/bckg.jpg");
        img2 = ImageLoader.loadImage("/textures/bmw_my - Copy.jpg");
        img3 = ImageLoader.loadImage("/textures/niggahit.png");
        sh = new SpriteSheet(ImageLoader.loadImage("/textures/nigga.png"));


        this.inputHandler = new InputHandler(this.display);

        Assets.init();

        gameState = new GameState();
        menuState = new MenuState();
        settingsState = new SettingsState();

        StateManager.setState(gameState);


        player.setLife(1000);

    }

    private void tick()  {

        if (StateManager.getState() != null) {
            StateManager.getState().tick();
        }
        player.tick();
        for (int i = 0; i < ActiveEnemys.size(); i++) {
            ActiveEnemys.get(i).tick();
            if(player.Intersects(ActiveEnemys.get(i).boundingBox)) {
                player.setLife( player.getLife()-5);

                Assets.player1 = Assets.niggahit;
                System.out.println(player.getLife());

            }
        }


        if(player.getLife()==0){
            JOptionPane.showMessageDialog(null, "GGWP!","YOU DIED", JOptionPane.YES_NO_OPTION);
            stop();
        }
    }


    private void render() {
        this.bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, this.width, this.height);
        g.drawImage(img, 0, 0, this.width, this.height, null);
        player.render(g);
        for (int i = 0; i < ActiveEnemys.size(); i++) {
            ActiveEnemys.get(i).render(g);
        }
        //g.drawImage(img2,this.enemy.x, this.enemy.y, this.enemy.width, this.enemy.height, null);
        //g.fillRect(this.enemy.x, this.enemy.y, this.enemy.width, this.enemy.height);

        if (StateManager.getState() != null){
            StateManager.getState().render(this.g);
        }

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();
        int fps = 100;
        double timePerTick = 1_000_000_000.0 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running) {
            now = System.nanoTime();
            delta += (now-lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            if (delta >= 1) {

                    tick();

                render();
                //Reset the delta
                ticks++;
                delta--;
            }
            if (timer >= 1_000_000_000) {
                System.out.println("Ticks and display.getFrame()s: " + ticks);
                ActiveEnemys.add(new Enemys((int)(400*Math.random()+0),0));
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public synchronized void start() {

        if(running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {

        if(!running) {
            return;
        }
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
