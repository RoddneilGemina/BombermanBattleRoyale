package com.bbr.game.server;

import com.bbr.game.shared.Message;
import com.bbr.game.shared.NetService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
public class NetServiceImpl extends RemoteServiceServlet implements NetService {
//    @Override
//    public Bomb sendBomb(float posX, float posY) {
//        return new Bomb((int)posX, (int)posY);
//    }
    public Message sendText(String text){
        return new Message(text);
    }

}
