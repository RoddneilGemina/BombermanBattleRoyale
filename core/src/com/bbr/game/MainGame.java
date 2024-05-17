package com.bbr.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bbr.net.GameClient;
import com.bbr.net.GameServer;
import com.bbr.net.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainGame extends Game {
	public static GameClient gameClient;
	public static GameServer gameServer;
	public static boolean isServer = false;
	public MainGame(boolean isServer){
		MainGame.isServer = isServer;
	}
	private void initNetwork(){
		if(isServer) {
			gameServer = new GameServer();
			mainBomber = new Bomber(0);
			world.destroyBody(mainBomber.getBody());
			mainBomber = null;
		} else {
			gameClient = new GameClient();
			gameClient.joinBomber(mainBomber);
		}

	}
	public static void addNewBomber(Network.addBomber ab){
		bombers.put(ab.bomberID, new Bomber(ab.posX, ab.posY, ab.bomberID));
	}
	public MainGame(){}
	SpriteBatch batch;
	Texture img;
	GameMap gm;

	public static World world;
	Controller con;
	Box2DDebugRenderer debugRenderer;
	public static final float SCALE = 10f;
	public static ArrayList<Object> bombsAndExplosions = new ArrayList<>();
	public static Map<Integer, Bomber> bombers = new HashMap<>();

	public static Bomber mainBomber;
	public static final short EXPLOSION_BITS = 0b01;
	
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
//		basil = new Bomber();
//		GameMap.bomber = basil;
//		con = new Controller(basil);
		if(!isServer){
			int id = 0;
			do{
				id = (int)(Math.random()*1000);
			} while(bombers.containsKey(id));
			mainBomber = new Bomber(id);
			bombers.put(id,mainBomber);
			GameMap.setBomber(mainBomber);
			con = new Controller(mainBomber);
		}
		initNetwork();
}

	@Override
	public void render () {
		world.step(1/60f,6,2);
		ScreenUtils.clear(0, 0, 0, 1);
		gm.render();
		if(con!=null) con.render();
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
		Integer[] keys = bombers.keySet().toArray(new Integer[0]);
		ArrayList<Network.PlayerRep> apr = null;
		if(isServer) apr = new ArrayList<>();
		GameMap.renderer.getBatch().begin();
		for(int i = 0; i < keys.length; i++){
			Bomber curr = bombers.get(keys[i]);
			curr.render();
			if(isServer) apr.add(new Network.PlayerRep(keys[i],curr.getPosX(),curr.getPosY()));
		}

		GameMap.renderer.getBatch().end();
		if(isServer) gameServer.update(new Network.GameState(apr));
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
