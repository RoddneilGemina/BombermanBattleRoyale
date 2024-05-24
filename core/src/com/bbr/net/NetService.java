package com.bbr.net;

import com.bbr.game.Bomb;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("message")
public interface NetService extends RemoteService {
    Bomb sendBomb(String input);
}
