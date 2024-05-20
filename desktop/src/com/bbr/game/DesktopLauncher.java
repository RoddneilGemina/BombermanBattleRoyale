package com.bbr.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("BombermanBattleRoyale");
		config.setWindowSizeLimits(640,480,640,480);
		config.setResizable(false);
		String ip = "127.0.0.1";
		boolean server = true;
		boolean play = true;
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
				continue;
			}
		}

		new Lwjgl3Application(new MainGame(ip,server,play), config);
	}
}
