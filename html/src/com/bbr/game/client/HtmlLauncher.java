package com.bbr.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.bbr.game.MainGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                return new GwtApplicationConfiguration(true);
                // Fixed size application:
                //return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                String ip = "127.0.0.1";  // the ip address if connecting to a server
                boolean server = true;    // true if the application will run a server
                boolean play = true;      // true if you want a playable bomber, false if spectating
                return new MainGame(ip,server,play);
        }
}