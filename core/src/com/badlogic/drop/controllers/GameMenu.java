package com.badlogic.drop.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMenu {

    public Texture menuImage;
    public static float menu0_x;
    public static float menu0_y;
    public static float menu0_w;
    public static float menu0_h;

    private float Width_S = 0;//Gdx.graphics.getWidth();
    private float Height_S = 0;//Gdx.graphics.getHeight();

    public static int flagmenu=0;
    public static boolean flagmenuChange=false;

    public GameMenu(float Width_S, float Height_S) {
        // load the images for the droplet and the bucket, 64x64 pixels each
        menuImage = new Texture(Gdx.files.internal("1.png"));
        this.Width_S = Width_S;
        this.Height_S = Height_S;
        menu0_x = Width_S-30f;
        menu0_y = 10f;
        menu0_w = 20f;
        menu0_h = 20f;

    }

    public void render(SpriteBatch batch, float x_ots, float y_ots) {
        batch.draw(menuImage, x_ots+menu0_x, y_ots+menu0_y,menu0_w,menu0_h);
        if (flagmenu == 1){//Выбор 1-го уровня
            batch.draw(menuImage, x_ots+menu0_x-20, y_ots+menu0_y+20,menu0_w,menu0_h);
        };

    }


}