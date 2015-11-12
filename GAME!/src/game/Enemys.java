package game;

import gfx.Assets;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by pc on 12.11.2015 ã..
 */
public class Enemys {
    private int x, y;
    private int velocity;
    private int width, height;
    private int dmg1;
    private Random rnd;
    private int health;
    private BufferedImage enemy;

    public Rectangle boundingBox;

    public static boolean isFallingDown;

    public Enemys(int x,int y) {
        this.x = x;
        this.y = y;
        this.width =20;
        this.height =20;
        this.boundingBox = new Rectangle(this.width, this.height);
     isFallingDown=false;
        this.velocity = 2;
        this.dmg1 = 1;
        this.health = 50;

    }

    public int getHealth1() {
        return this.health;
    }

    public void tick() {
        this.boundingBox.setBounds(this.x, this.y, this.width, this.height);

        if (!isFallingDown) {
            this.y += this.velocity;
        }
    }
    public void render(Graphics g) {
        g.drawImage(Assets.enemyz, this.x, this.y, null);
    }
}