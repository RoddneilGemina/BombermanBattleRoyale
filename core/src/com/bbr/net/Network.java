package com.bbr.net;


import com.badlogic.gdx.graphics.Color;
import com.bbr.game.BombBuilder;
import com.bbr.game.MainGame;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import jdk.tools.jmod.Main;

import java.util.ArrayList;

public class Network {
    public static final int PORT = 42069;
    public static int getPort(){return PORT;}
    private static String ip = "127.0.0.1";
    public static String getIp(){return ip;}
    public static void setIp(String ip){Network.ip = ip;}
    public static void register(EndPoint endpoint) {
        Kryo kryo = endpoint.getKryo();
        kryo.register(GameState.class);
        kryo.register(PlayerRep.class);
        kryo.register(ArrayList.class);
        kryo.register(updateBomber.class);
        kryo.register(addBomber.class);
        kryo.register(BombBuilder.class);
        kryo.register(addBomb.class);
        kryo.register(Color.class);
        kryo.register(announcement.class);
    }
    public static class GameState{
        public ArrayList<PlayerRep> players;
        public ArrayList<Network.addBomb> newBombs;
        public GameState(){
            players = new ArrayList<>();
        }
        public GameState(ArrayList<PlayerRep> players){
            this.players = players;
        }
    }
    public static class PlayerRep{
        public int bomberID;
        public float posX, posY;
        public float dirX, dirY;
        public int health;
        public String name;
        public PlayerRep(){

        }
        public PlayerRep(int bomberID, float posX, float posY, String name){
            this.posX = posX;
            this.posY = posY;
            this.bomberID = bomberID;
            this.name = name;
        }
    }
    public static class updateBomber {
        public PlayerRep pr;
        public updateBomber(){}
        public updateBomber(PlayerRep pr){this.pr=pr;}
    }
    public static class addBomber {
        public int posX,posY,bomberID;
        public String name;
    }
    public static class addBomb {
        public int posX, posY, bomberID;
        public BombBuilder bb;
    }
    public static class announcement{
        public String msg; public Color color;
        public announcement(){}
        public announcement(String s, Color c){msg=s;color=c;}
    }
    public static void announce(String msg){announce(msg,new Color(1,1,1,1));}
    public static void announce(String msg, Color color){
        if(MainGame.gameClient!=null){
            MainGame.gameClient.announce(msg,color);
        } else if(MainGame.gameServer != null){
            MainGame.gameServer.announce(msg,color);
        }
    }
}