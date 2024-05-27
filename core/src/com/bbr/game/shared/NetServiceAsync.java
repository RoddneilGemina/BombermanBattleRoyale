package com.bbr.game.shared;

import com.bbr.game.Bomb;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NetServiceAsync {
    //void sendBomb(float posX, float posY, AsyncCallback<Bomb> callback);
    void sendText(String text, AsyncCallback<Message> callback);
}
