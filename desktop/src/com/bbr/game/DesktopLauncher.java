package com.bbr.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.bbr.menu.tmp;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("BombermanBattleRoyale");
		config.setWindowSizeLimits(640,480,1280,720);
		config.setResizable(false);
		String ip = "127.0.0.1";  // the ip address if connecting to a server
		boolean server = true;    // true if the application will run a server
		boolean play = true;      // true if you want a playable bomber, false if spectating
		for(String s : args){
			try{
				String param = s.split("=")[0];
				String arg = s.split("=")[1];
				if(param.matches("ip"))
					ip = arg;
				else if(param.matches("server"))
					server = Boolean.parseBoolean(arg);
				else if(param.matches("play"))
					play = Boolean.parseBoolean(arg);
			} catch(Exception e){
				e.printStackTrace();
			}
		}

		//new Lwjgl3Application(new MainGame(ip,server,play), config);
		new Lwjgl3Application(new tmp(), config);
	}
}
