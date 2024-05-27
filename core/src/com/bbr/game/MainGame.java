package com.bbr.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bbr.game.Utils.Renderer;
import com.bbr.game.shared.NetServiceAsync;
//import com.bbr.game.shared.NetInterface;

import java.util.HashMap;
import java.util.Map;

public class MainGame extends Game {
	public static boolean isServer = false;
	private boolean isPlaying = false;
	public static NetServiceAsync netass;
	public MainGame(String ip, boolean server, boolean play, NetServiceAsync netass){
		this.netass = netass;
		MainGame.isServer = server;
		//NetworkOLD.setIp(ip);
		isPlaying = play;
		//gameService = net;
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
}

	@Override
	public void render () {
		world.step(1/60f,6,2); //update physics
		ScreenUtils.clear(0, 0, 0, 1);	// clear screen
		gm.render();	// render map
		if(con!=null) con.render(); // update controller

		debugRenderer.render(world,GameMap.camera.combined); // render hitboxes

		Integer[] keys = bombers.keySet().toArray(new Integer[0]);

		GameMap.renderer.getBatch().begin();
		for(int i = 0; i < keys.length; i++){
			Bomber curr = bombers.get(keys[i]);
			curr.render();
		}
		GameMap.renderer.getBatch().end();


		Renderer.render();
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

}
