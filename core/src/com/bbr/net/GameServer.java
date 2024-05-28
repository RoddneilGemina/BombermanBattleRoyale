package com.bbr.net;

import com.badlogic.gdx.graphics.Color;
import com.bbr.game.Bomber;
import com.bbr.game.Console;
import com.bbr.game.MainGame;
import com.bbr.game.Utils.Renderer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameServer {
    Server server;
    BlockingQueue<Network.PlayerRep> updateQueue;
    BlockingQueue<Network.addBomb> updateQueueB;
    public GameServer(){
        updateQueue = new LinkedBlockingQueue<>();
        updateQueueB = new LinkedBlockingQueue<>();
        server = new Server();
        Network.register(server);
        try {
            server.addListener(new Listener.ThreadedListener(new Listener(){
                public void received(Connection c, Object object){
                    if(object instanceof Network.updateBomber){
                        Network.PlayerRep ub = ((Network.updateBomber)object).pr;
                        if(MainGame.bombers.containsKey(ub.bomberID)){
                            updateQueue.add(ub);
                        }
                    } else if(object instanceof Network.addBomber){
                        Network.addBomber ab = (Network.addBomber)object;
                        if(!MainGame.bombers.containsKey(ab.bomberID)){
                            MainGame.bombers.put(ab.bomberID,new Bomber(ab.posX,ab.posY,ab.bomberID));
                            announce(ab.name+" joined the game",Color.YELLOW);
                        } else {
                            if(!MainGame.isServer)
                                Console.print("Bomber with ID "+ab.bomberID+" tried to join the game but ID already taken!", Color.YELLOW);
                        }
                    } else if(object instanceof Network.addBomb){
                        Network.addBomb ab = (Network.addBomb) object;
                        updateQueueB.add(ab);
                    } else if(object instanceof Network.announcement){
                        Network.announcement a = (Network.announcement)object;
                        if(!MainGame.isPlaying) Console.print(a.msg,a.color);
                        announce(a.msg,a.color);
                    }
                }
            }));
            Console.print("Starting Server...",Color.YELLOW);
            server.bind(Network.PORT);
            server.start();
            Console.print("Successfully started server at "+Network.getIp()+" : "+Network.getPort(),Color.GREEN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void updatePlayers(){
        while(!updateQueue.isEmpty()){
            try {
                Network.PlayerRep pr = updateQueue.take();
                if(MainGame.mainBomber !=null && pr.bomberID==MainGame.mainBomber.id) continue;
                MainGame.bombers.get(pr.bomberID).unpack(pr);
            } catch (InterruptedException e) {
                Console.print("Huh!?", Color.RED);
            }
        }
        while(!updateQueueB.isEmpty()){
            try {
                Network.addBomb ab = updateQueueB.take();
                Renderer.setToBatch(ab.bb.buildNoNet(),3);
                newBombs.add(ab);
                //Console.print("bomb added to s que");
            } catch (InterruptedException e) {
                Console.print("Huh!?", Color.RED);
            }
        }
    }
    public ArrayList<Network.addBomb> newBombs = new ArrayList<>();
    public void update(Network.GameState gs){
        server.sendToAllTCP(gs);
    }
    public void announce(String msg, Color color){
        //Console.print(msg,color);
        server.sendToAllTCP(new Network.announcement(msg,color));
    }
}