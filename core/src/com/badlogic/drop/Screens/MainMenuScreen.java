package com.badlogic.drop.Screens;

import com.badlogic.drop.Main;
import com.badlogic.drop.controllers.AdsController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.pay.PurchaseManager;

public class MainMenuScreen implements Screen {

	public   PurchaseManager purchaseManager;
	private final AdsController adsController;
	private Stage stage;
    final Main game;
	public Skin skin;

	private Table table_menu1;
	private TextButton play_reclam,play_pay,exit_b;
	//private SpriteBatch batch_menu1;
	//private Sprite sprite_menu1;
	private float Width_S = 200;//Gdx.graphics.getWidth();
	private float Height_S = 400;//Gdx.graphics.getHeight();

	OrthographicCamera camera;
	//OrthographicCamera camera;

	public MainMenuScreen(PurchaseManager purchaseManager, AdsController adsController, final Main gam) {
		this.purchaseManager = purchaseManager;
		this.adsController = adsController;
		this.game = gam;

		this.Width_S = this.game.Width_S;
		this.Height_S = this.game.Height_S;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Width_S, Height_S);
		game.batch.setProjectionMatrix(camera.combined);

		stage = new Stage(new StretchViewport(Width_S, Height_S));

		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		play_reclam = new TextButton("Admob", skin);
		play_pay = new TextButton("Pay", skin);

		exit_b = new TextButton("Exit", skin);

		table_menu1 = new Table();
		table_menu1.setFillParent(true);

		table_menu1.setPosition(0,0, Align.center|Align.top);

		table_menu1.add(play_reclam).padBottom(30).row();
		table_menu1.add(play_pay).padBottom(30).row();
		table_menu1.add(exit_b);

		stage.addActor(table_menu1);


		play_reclam.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (MainMenuScreen.this.adsController !=null){

					MainMenuScreen.this.adsController.showRewardedVideo();
					System.out.println("hiii");
				}
			}
		});
		if (this.purchaseManager != null) {
//			table.add(new Label("Purchase Manager: " + purchaseManager.storeName(), skin));
//			table.row();
			play_pay.addListener(new ChangeListener() {

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					new PaymentForm(MainMenuScreen.this).show(stage);
				}
			});
		}
		exit_b.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Gdx.app.exit();
				dispose();
			}
		});

		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(stage);
		Gdx.input.setInputProcessor(im);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		update();

		game.batch.begin();
        //
		game.batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public void update() {
	}

	@Override
	public void dispose() {

	}

}


