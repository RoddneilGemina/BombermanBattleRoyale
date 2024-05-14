package com.bbr.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

public class BombermanBattleRoyaleGame extends Game {
	SpriteBatch batch;
	Texture img;
	GameMap gm;

	public static World world;
	Bomber basil;
	Box2DDebugRenderer debugRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gm = new GameMap();
		img = new Texture("badlogic.jpg");
		gm.create();
		world = new World(new Vector2(0,0),true);
		double scale = 80.0;
		for(int r=0; r<11; r++){
			for(int c=0;c<11;c++){
				if(GameMap.map[r][c]==0) continue;
				BodyDef bd = new BodyDef();
				bd.type = BodyDef.BodyType.StaticBody;
				bd.position.set((int)(r*scale + scale/2),(int)(c*scale + scale/2));
				Body body = world.createBody(bd);
				PolygonShape ps = new PolygonShape();
				ps.setAsBox((int)(scale/2),(int)(scale/2));
				body.createFixture(ps, 0.0f);
			}
		}
		debugRenderer = new Box2DDebugRenderer();
		basil = new Bomber();
}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		gm.render();
		basil.render();
		debugRenderer.render(world,gm.camera.combined);
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
