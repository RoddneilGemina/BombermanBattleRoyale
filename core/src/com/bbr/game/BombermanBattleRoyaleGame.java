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

import java.util.ArrayList;

public class BombermanBattleRoyaleGame extends Game {
	SpriteBatch batch;
	Texture img;
	GameMap gm;

	public static World world;
	Bomber basil;
	Controller con;
	Box2DDebugRenderer debugRenderer;
	public static final float SCALE = 10f;
	public static ArrayList<Object> bombsAndExplosions = new ArrayList<>();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gm = new GameMap();
		img = new Texture("badlogic.jpg");
		gm.create();
		world = new World(new Vector2(0,0),true);
		for(int r=0; r<11; r++){
			for(int c=0;c<11;c++){
				if(GameMap.map[r][c]==0) continue;
				BodyDef bd = new BodyDef();
				bd.type = BodyDef.BodyType.StaticBody;
				bd.position.set((int)(r*SCALE + SCALE/2),(int)(c*SCALE + SCALE/2));
				Body body = world.createBody(bd);
				PolygonShape ps = new PolygonShape();
				ps.setAsBox((int)(SCALE/2),(int)(SCALE/2));
				body.createFixture(ps, 0.0f);
			}
		}
		debugRenderer = new Box2DDebugRenderer();
		basil = new Bomber();
		GameMap.basil = basil;
		con = new Controller(basil);
}

	@Override
	public void render () {
		world.step(1/60f,6,2);
		ScreenUtils.clear(0, 0, 0, 1);
		gm.render();
		con.render();
		debugRenderer.render(world,GameMap.camera.combined);
		if(Bomb.batch != null) {
			Bomb.batch.begin();
			for (int i =0; i < bombsAndExplosions.size(); i++) {
				Object o = bombsAndExplosions.get(i);
				if (o instanceof Bomb) {
					((Bomb) o).render();
				} else if (o instanceof Explosion) {
					((Explosion) o).render();
				}
			}
			Bomb.batch.end();
		}
		basil.render();
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
