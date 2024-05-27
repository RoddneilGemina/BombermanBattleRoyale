package com.bbr.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.bbr.game.Bomb;
import com.bbr.game.MainGame;
import com.bbr.game.shared.Message;
import com.bbr.game.shared.NetService;
import com.bbr.game.shared.NetServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class HtmlLauncher extends GwtApplication implements EntryPoint {
        private final NetServiceAsync gameService = GWT.create(NetService.class);

        @Override
        public void onModuleLoad() {
//                gameService.sendText("HEY", new AsyncCallback<Message>() {
//                        @Override
//                        public void onFailure(Throwable throwable) {
//                                Window.alert("AAAAAA");
//                        }
//
//                        @Override
//                        public void onSuccess(Message message) {
//
//                        }
//                });
        }

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
                return new MainGame(ip,server,play,gameService);
        }
}