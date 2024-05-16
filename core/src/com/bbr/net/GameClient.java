package com.bbr.net;

import com.bbr.game.Bomber;
import com.bbr.game.MainGame;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import jdk.tools.jmod.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GameClient {
    public static Network.GameState gameState = null;
    private Client client;
    public GameClient(){
        client = new Client();
        client.start();
        Network.register(client);

        client.addListener(new Listener.ThreadedListener(new Listener(){
            public void connected(Connection connection){
                System.out.println("Client connected");
            }
            public void received(Connection connection, Object object){

                if (object instanceof Network.GameState){
                    gameState = (Network.GameState) object;
                    ArrayList<Network.PlayerRep> apr = gameState.players;
                    Map<Integer,Bomber> bombers = MainGame.bombers;
                    for(int i = 0; i < apr.size(); i++){
                        if(!bombers.containsKey(apr.get(i).bomberID)){
                            Network.PlayerRep pr = apr.get(i);
                            bombers.put(pr.bomberID,new Bomber(pr.posX,pr.posY,pr.bomberID));
                        }
                    }
                }
            }
            public void disconnected(Connection connection){
                System.out.println("Client disconnected");
            }
        }));

        try{
            client.connect(5000, "127.0.0.1", Network.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updatePlayerBomber(Bomber b){
        Network.updateBomber msg = new Network.updateBomber();
        msg.bomberID = b.id;
        msg.posX = b.getPosX();
        msg.posY = b.getPosY();
        client.sendTCP(msg);
    }
    public void joinBomber(Bomber b){
        Network.addBomber msg = new Network.addBomber();
        msg.bomberID = b.id;
        msg.posX = b.getPosX();
        msg.posY = b.getPosY();
        client.sendTCP(msg);
    }
}
