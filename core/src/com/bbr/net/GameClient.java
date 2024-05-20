package com.bbr.net;

import com.bbr.game.Bomb;
import com.bbr.game.Bomber;
import com.bbr.game.MainGame;
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
                System.out.println("Client connected");
            }
            public void received(Connection connection, Object object){

                if (object instanceof Network.GameState){
                    gameState = (Network.GameState) object;
                }
            }
            public void disconnected(Connection connection){
                System.out.println("Client disconnected");
            }
        }));

        try{
            client.connect(5000, Network.getIp(), Network.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addBomb(int posX, int posY, int bomberID){
        Network.addBomb ab = new Network.addBomb();
        ab.posX = posX;
        ab.posY = posY;
        ab.bomberID = bomberID;
        client.sendTCP(ab);
    }
    public void updatePlayerBomber(Bomber b){
        Network.updateBomber msg = new Network.updateBomber();
        msg.bomberID = b.id;
        msg.posX = b.getPosX();
        msg.posY = b.getPosY();
        client.sendTCP(msg);
    }
    public void updatePlayers(){
        if(gameState == null) return;
        ArrayList<Network.PlayerRep> apr = gameState.players;
        ArrayList<Network.addBomb> aab = gameState.newBombs;
        Map<Integer,Bomber> bombers = MainGame.bombers;
        for(int i = 0; i < apr.size(); i++){
            if(! bombers.containsKey(apr.get(i).bomberID)){
                System.out.println("Received bomber : ID->"+apr.get(i).bomberID + " PosX->"+apr.get(i).posX+ " PosY->"+apr.get(i).posY);
                Network.PlayerRep pr = apr.get(i);
                bombers.put(pr.bomberID,new Bomber(pr.posX,pr.posY,pr.bomberID));
            } else if (MainGame.mainBomber != null && MainGame.mainBomber.id != apr.get(i).bomberID){
                int x = apr.get(i).posX;
                int y = apr.get(i).posY;
                int id= apr.get(i).bomberID;
                bombers.get(id).teleport(x,y);
            }
        }
        while(!aab.isEmpty()){
            MainGame.bombsAndExplosions.add(new Bomb(aab.get(0).posX,aab.get(0).posY));
            aab.remove(0);
        }
    }
    public void joinBomber(Bomber b){
        Network.addBomber msg = new Network.addBomber();
        msg.bomberID = b.id;
        msg.posX = b.getPosX();
        msg.posY = b.getPosY();
        client.sendTCP(msg);
    }
}
