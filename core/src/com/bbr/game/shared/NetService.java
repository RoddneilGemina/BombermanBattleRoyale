package com.bbr.game.shared;

import com.bbr.game.Bomb;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("bomb")
public interface NetService extends RemoteService {
    //Bomb sendBomb(float posX, float posY);
    Message sendText(String message);
}
