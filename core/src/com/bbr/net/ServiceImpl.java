package com.bbr.net;

import com.bbr.game.Bomb;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
public class ServiceImpl extends RemoteServiceServlet implements NetService{
    @Override
    public Bomb sendBomb(String input) {
        String str = "hello "+input+"!";
        Bomb bomb = new Bomb(1,1);
        return bomb;
    }
}
