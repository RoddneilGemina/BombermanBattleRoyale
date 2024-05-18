package com.bbr.net;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;

public class Network {
    public static final int PORT = 42069;
    public static void register(EndPoint endpoint) {
        Kryo kryo = endpoint.getKryo();
        kryo.register(GameState.class);
        kryo.register(PlayerRep.class);
        kryo.register(ArrayList.class);
        kryo.register(updateBomber.class);
        kryo.register(addBomber.class);
        kryo.register(addBomb.class);
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
        public int posX, posY, bomberID;
        public PlayerRep(){

        }
        public PlayerRep(int bomberID, int posX, int posY){
            this.posX = posX;
            this.posY = posY;
            this.bomberID = bomberID;
        }
    }
    public static class updateBomber {
        public int posX, posY, bomberID;
    }
    public static class addBomber {
        public int posX,posY,bomberID;
    }
    public static class addBomb {
        public int posX, posY, bomberID;
    }
}
