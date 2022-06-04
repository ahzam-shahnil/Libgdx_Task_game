package com.badlogic.drop;

import com.badlogic.drop.Screens.MainMenuScreen;
import com.badlogic.drop.controllers.AdsController;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.pay.PurchaseManager;
//import com.badlogic.gdx.pay.android.googlebilling.PurchaseManagerGoogleBilling;

public class Main extends Game {
	private final AdsController adsController;
	public SpriteBatch batch;
	public BitmapFont font;
public PurchaseManager purchaseManager;
	public float Width_S = 200;//Gdx.graphics.getWidth();
	public float Height_S = 400;//Gdx.graphics.getHeight();

	public Main(AdsController adsController) {
		this.adsController = adsController;
	}

	public void create() {
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
MainMenuScreen mainMenuScreen = new MainMenuScreen(purchaseManager, adsController,this);

//mainMenuScreen.purchaseManager=new PurchaseManagerGoogleBilling(this);
this.setScreen(mainMenuScreen );
		//this.setScreen(new GameScreen0(this));
	}

	public void render() {
		//Gdx.gl.glClearColor(1,0,0,1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}

