package com.bbr.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bbr.game.Utils.GameObj;
import com.bbr.game.Utils.NewGame;
import com.bbr.game.Utils.WorldObj;
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
	private boolean isPlaying = false;
	public MainGame(String ip, boolean server, boolean play){
		MainGame.isServer = server;
		Network.setIp(ip);
		isPlaying = play;
	}
	public static void addNewBomber(Network.addBomber ab){
		bombers.put(ab.bomberID, new Bomber(ab.posX, ab.posY, ab.bomberID));
	}
	public MainGame(){}
	SpriteBatch batch;
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
		gm.create();
		world = initMap();
		debugRenderer = new Box2DDebugRenderer();
		NewGame.init();
		NewGame.setCamera(GameMap.camera);
		if(isPlaying) initPlayer();
		initNetwork();
//		ExampleObj obg = new ExampleObj(15,15,1f,1f);
//		con = new Controller(obg);
//		NewGame.setToBatch(obg,2);
}

	@Override
	public void render () {
		world.step(1/60f,6,2);
		ScreenUtils.clear(0, 0, 0, 1);
		gm.render();
		if(con!=null) con.render();
		debugRenderer.render(world,GameMap.camera.combined);
		renderBombs();
		NewGame.render();
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
		if(isServer) {
			Network.GameState gs = new Network.GameState(apr);
			gs.newBombs = gameServer.newBombs;
			gameServer.newBombs = new ArrayList<>();
			gameServer.update(gs);
			gameServer.updatePlayers();
		} else gameClient.updatePlayers();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
	private void renderBombs(){
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
	}
	private void initPlayer(){
		int id = 0;
		do{
			id = (int)(Math.random()*1000);
		} while(bombers.containsKey(id));

		mainBomber = new Bomber(id);
		bombers.put(id,mainBomber);
		GameMap.setBomber(mainBomber);
		con = new Controller(mainBomber);
	}
	private void initNetwork(){
		if(isServer) {
			gameServer = new GameServer();
		}
		if(isPlaying){
			gameClient = new GameClient();
			gameClient.joinBomber(mainBomber);
		} else {
			mainBomber = new Bomber(0);
			world.destroyBody(mainBomber.getBody());
			mainBomber = null;
		}
	}
	public World initMap(){
		World newWorld = new World(new Vector2(0,0),true);
		for(int r=0; r<11; r++){
			for(int c=0;c<11;c++){
				if(GameMap.map[r][c]==0) continue;
				BodyDef bd = new BodyDef();
				bd.type = BodyDef.BodyType.StaticBody;
				bd.position.set((int)(r*SCALE + SCALE/2),(int)(c*SCALE + SCALE/2));
				Body body = newWorld.createBody(bd);
				PolygonShape ps = new PolygonShape();
				ps.setAsBox((int)(SCALE/2),(int)(SCALE/2));
				body.createFixture(ps, 0.0f);
			}
		}
		return newWorld;
	}
}
