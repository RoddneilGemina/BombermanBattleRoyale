package com.bbr.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bbr.game.Utils.GameObj;
import com.bbr.game.Utils.Renderer;
import com.bbr.game.Utils.WorldObj;
import com.bbr.net.GameClient;
import com.bbr.net.GameServer;
import com.bbr.net.Network;
//import com.bbr.game.shared.NetInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainGame extends Game {
	public static boolean isServer = false;
	public static boolean isPlaying = false;
	public static GameClient gameClient;
	public static GameServer gameServer;
	public static ArrayList<Body> disposeList = new ArrayList<>();
	public MainGame(String ip, boolean server, boolean play){
		MainGame.isServer = server;
		Network.setIp(ip);
		isPlaying = play;

	}
	public MainGame(){}
	GameMap gm;

	public static World world;
	Controller con;
	Box2DDebugRenderer debugRenderer;
	public static final float SCALE = 10f;
	public static Map<Integer, Bomber> bombers = new HashMap<>();

	public static Bomber mainBomber;
	public static final short EXPLOSION_BITS = 0b01;

	
	@Override
	public void create () {
		gm = new GameMap();
		gm.create();
		world = GameMap.initMap();
		debugRenderer = new Box2DDebugRenderer();
		Renderer.init();
		Renderer.setCamera(GameMap.camera);
		if(isPlaying) initPlayer();
		initNetwork();
		ItemSpawner.spawnItem(10);
}

	@Override
	public void render () {

		world.step(1/60f,6,2); //update physics

		ScreenUtils.clear(0, 0, 0, 1);	// clear screen
		gm.render();	// render map
		if(con!=null) con.render(); // update controller

		//debugRenderer.render(world,GameMap.camera.combined); // render hitboxes

		Renderer.render();
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
		Renderer.renderScreen();

		if(isServer) {
			Network.GameState gs = new Network.GameState(apr);
			gs.newBombs = gameServer.newBombs;
			gameServer.newBombs = new ArrayList<>();
			gameServer.update(gs);
			gameServer.updatePlayers();
		} else gameClient.updatePlayers();

		if(!world.isLocked())
			while(!disposeList.isEmpty()){
				world.destroyBody(disposeList.get(0));
				disposeList.remove(0);
			}

	}

	@Override
	public void dispose () {
		// please put something here
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
			mainBomber.removeDisplays();
			mainBomber = null;
		}
	}
}
