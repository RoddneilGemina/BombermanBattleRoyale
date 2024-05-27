package com.bbr.game;

public abstract class PlayerActions{
    public abstract void doAction(Bomber b);
}
class Dash extends PlayerActions{
    public void doAction(Bomber b){
        b.getBody().setLinearDamping(0f);
        b.moveBody(b.getDirection().x*600,b.getDirection().y*600);
        b.getBody().setLinearDamping(30f);
    }
}
