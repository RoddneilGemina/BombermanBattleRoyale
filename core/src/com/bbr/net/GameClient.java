package com.bbr.net;

import com.badlogic.gdx.graphics.Color;
import com.bbr.game.*;
import com.bbr.game.Utils.Renderer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import jdk.tools.jmod.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class GameClient {
    public static Network.GameState gameState = null;
    private Client client;
    public GameClient(){
        client = new Client();
        client.start();
        Network.register(client);

        client.addListener(new Listener.ThreadedListener(new Listener(){
            public void connected(Connection connection){
            }
            public void received(Connection connection, Object object){

                if (object instanceof Network.GameState){
                    gameState = (Network.GameState) object;
                } else if(object instanceof Network.announcement){
                    Network.announcement a = (Network.announcement)object;
                    Console.print(a.msg,a.color);
                }
            }
            public void disconnected(Connection connection){
                Console.print("Disconnected from server.",Color.RED);
            }
        }));

        try{
            client.connect(12000, Network.getIp(), Network.PORT);
        } catch (IOException e) {
            Console.print("Could not connect to server.",Color.RED);
        }
    }
    public void addBomb(BombBuilder bb, int bomberID){
        Network.addBomb ab = new Network.addBomb();
        ab.bb = bb;
        ab.posX = (int) bb.getPosX();
        ab.posY = (int) bb.getPosY();
        ab.bomberID = bomberID;
        client.sendTCP(ab);
    }
    public void updatePlayerBomber(Bomber b){
        Network.PlayerRep msg = b.pack();
        client.sendTCP(new Network.updateBomber(msg));
    }
    public void updatePlayers(){
        if(gameState == null) return;
        ArrayList<Network.PlayerRep> apr = gameState.players;
        ArrayList<Network.addBomb> aab = gameState.newBombs;
        Map<Integer,Bomber> bombers = MainGame.bombers;
        for(int i = 0; i < apr.size(); i++){
            if(! bombers.containsKey(apr.get(i).bomberID)){
                //System.out.println("Received bomber : ID->"+apr.get(i).bomberID + " PosX->"+apr.get(i).posX+ " PosY->"+apr.get(i).posY);
                Network.PlayerRep pr = apr.get(i);
                bombers.put(pr.bomberID,new Bomber((int)pr.posX,(int)pr.posY,pr.bomberID));
            } else if (MainGame.mainBomber != null && MainGame.mainBomber.id != apr.get(i).bomberID) {
                int id = apr.get(i).bomberID;
                bombers.get(id).unpack(apr.get(i));
            }
        }
        while(!aab.isEmpty()){
            //Console.print("bomb received");
            Renderer.setToBatch(aab.get(0).bb.buildNoNet(),3);
            aab.remove(0);
        }
    }
    public void joinBomber(Bomber b){
        Network.addBomber msg = new Network.addBomber();
        msg.bomberID = b.id;
        msg.posX = (int)b.getPosX();
        msg.posY = (int)b.getPosY();
        client.sendTCP(msg);
    }
    public void announce(String msg, Color color){
        client.sendTCP(new Network.announcement(msg,color));
    }
}