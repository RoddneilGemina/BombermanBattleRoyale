package com.bbr.net;

import com.badlogic.gdx.utils.Queue;
import com.bbr.game.MainGame;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameServer {
    Server server;
    BlockingQueue<Network.PlayerRep> updateQueue;
    public GameServer(){
        updateQueue = new LinkedBlockingQueue<>();
        server = new Server();
        Network.register(server);
        try {
            server.addListener(new Listener.ThreadedListener(new Listener(){
                public void received(Connection c, Object object){
                    if(object instanceof Network.updateBomber){
                        Network.updateBomber ub = (Network.updateBomber)object;
                        if(MainGame.bombers.containsKey(ub.bomberID)){
                            //MainGame.bombers.get(ub.bomberID).teleport(ub.posX,ub.posY);
                            updateQueue.add(new Network.PlayerRep(ub.bomberID,ub.posX,ub.posY));
                        }
                    } else if(object instanceof Network.addBomber){
                        Network.addBomber ab = (Network.addBomber)object;
                        if(!MainGame.bombers.containsKey(ab.bomberID)){
                            MainGame.addNewBomber(ab);
                        } else {
                            System.out.println("Bomber with ID "+ab.bomberID+" tried to join the game but ID already taken!");
                        }
                    }
                }
            }));
            server.bind(Network.PORT);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void updatePlayers(){
        while(!updateQueue.isEmpty()){
            try {
                Network.PlayerRep pr = updateQueue.take();
                MainGame.bombers.get(pr.bomberID).teleport(pr.posX,pr.posY);
            } catch (InterruptedException e) {
                continue;
            }
        }
    }
    public void update(Network.GameState gs){
        server.sendToAllTCP(gs);
    }
}
