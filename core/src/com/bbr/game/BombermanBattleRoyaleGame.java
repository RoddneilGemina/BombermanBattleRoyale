package com.bbr.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class BombermanBattleRoyaleGame extends Game {
	SpriteBatch batch;
	Texture img;
	GameMap gm;

	public static World world;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gm = new GameMap();
		img = new Texture("badlogic.jpg");
		gm.create();
//		world.createBody();
//		world.
}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		gm.render();
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
