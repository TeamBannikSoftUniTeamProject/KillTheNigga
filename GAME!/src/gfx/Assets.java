package gfx;

import java.awt.image.BufferedImage;


public class Assets {

    private static final int width = 95, height = 130;

    public static BufferedImage player1, player2, enemyz, niggahit,dontMove;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/nigga.png"));
        SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage("/textures/enemy - Copy.png"));
        SpriteSheet sheet3 = new SpriteSheet(ImageLoader.loadImage("/textures/niggahit.png"));

        player1 = sheet.crop(0, 0, width, height);
        player2 = sheet.crop(width, 0, width, height);
        enemyz = sheet2.crop(0,0,40,66);
        niggahit = sheet3.crop(0,0,width,height);
        dontMove = sheet.crop(0,0,width,height);
    }
}
