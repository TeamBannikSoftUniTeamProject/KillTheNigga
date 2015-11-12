package game;

import gfx.Assets;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;

public class Player {
    private int x, y;
    private int velocity;
    private int width, height;
    private int health;
    private int countRight=0;
    private int countLeft =0;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    private int justCount = 0;
    private   int life=500;

    private Rectangle boundingBox;

    public static boolean goingLeft;
    public static boolean goingRight;

    public Player(int x,int y) {
        this.x = x;
        this.y = y;
        this.width = 95;
        this.height = 130;
        this.health = 50;
        this.velocity = 2;
        this.boundingBox = new Rectangle(this.width, this.height);

        goingLeft = false;
        goingRight = false;
    }

    public int getHealth() {
        return this.health;
    }

    //Checks if the player intersects with something
    public boolean Intersects(Rectangle r) {
        if(this.boundingBox.contains(r) || r.contains(this.boundingBox)) {
            return true;
        }
        return false;
    }

    public void tick() {

        this.boundingBox.setBounds(this.x, this.y, this.width, this.height);

        if (!goingLeft && !goingRight){
            Assets.player1 = Assets.dontMove;
        }
        if(goingLeft && this.x>=-5) {
            SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/nigga1.png"));
            Assets.player1=sheet.crop(countLeft*width,0,width,height);
            countLeft++;
            if(countLeft==5){
                countLeft=0;
            }
            this.x -= this.velocity;
        }
        else if (this.x==-5){
            this.x=-5;
        }
        if(goingRight && this.x<=520 ) {
            SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/nigga1.png"));
            Assets.player1=sheet.crop(countRight*width,height,width,height);
            countRight++;
            if(countRight==5){
                countRight=0;
            }
            this.x += this.velocity;
        }
        else if(this.x==520){
            this.x=520;
        }
    }

    //Draws the player
    public void render(Graphics g) {
        g.drawImage(Assets.player1, this.x, this.y, null);
    }
}
