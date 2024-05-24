package com.bbr.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bbr.game.Utils.Collider;
import com.bbr.game.Utils.NewGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainGame extends Game {
//	public static GameClientOLD gameClient;
//	public static GameServerOLD gameServer;
	public static boolean isServer = false;
	private boolean isPlaying = false;
	public MainGame(String ip, boolean server, boolean play){
		MainGame.isServer = server;
		//NetworkOLD.setIp(ip);
		isPlaying = play;
	}
//	public static void addNewBomber(NetworkOLD.addBomber ab){
//		bombers.put(ab.bomberID, new Bomber(ab.posX, ab.posY, ab.bomberID));
//	}
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
//		initNetwork();
}

	@Override
	public void render () {
		world.step(1/60f,6,2); //update physics
		ScreenUtils.clear(0, 0, 0, 1);	// clear screen
		gm.render();	// render map
		if(con!=null) con.render(); // update controller

		debugRenderer.render(world,GameMap.camera.combined); // render hitboxes

		renderBombs(); // render bombs

		//ignore network stuff
		Integer[] keys = bombers.keySet().toArray(new Integer[0]);
//		ArrayList<NetworkOLD.PlayerRep> apr = null;
//		if(isServer) apr = new ArrayList<>();

		GameMap.renderer.getBatch().begin();
		for(int i = 0; i < keys.length; i++){
			Bomber curr = bombers.get(keys[i]);
			curr.render();
			//if(isServer) apr.add(new NetworkOLD.PlayerRep(keys[i],curr.getPosX(),curr.getPosY()));
		}
		GameMap.renderer.getBatch().end();


		NewGame.render();

//		if(isServer) {
//			NetworkOLD.GameState gs = new NetworkOLD.GameState(apr);
//			gs.newBombs = gameServer.newBombs;
//			gameServer.newBombs = new ArrayList<>();
//			gameServer.update(gs);
//			gameServer.updatePlayers();
//		} else gameClient.updatePlayers();
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
//	private void initNetwork(){
//		if(isServer) {
//			gameServer = new GameServerOLD();
//		}
//		if(isPlaying){
//			gameClient = new GameClientOLD();
//			gameClient.joinBomber(mainBomber);
//		} else {
//			mainBomber = new Bomber(0);
//			world.destroyBody(mainBomber.getBody());
//			mainBomber = null;
//		}
//	}
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
		newWorld.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				Object collA = contact.getFixtureA().getBody().getUserData();
				Object collB = contact.getFixtureB().getBody().getUserData();
				if(collA instanceof Collider) ((Collider)collA).collide(collB);
				if(collB instanceof Collider) ((Collider)collB).collide(collA);
			}
			@Override
			public void endContact(Contact contact) {}
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {}
		});
		return newWorld;
	}
}
