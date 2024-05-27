package com.bbr.game;

import com.bbr.game.Utils.Renderer;

public abstract class PlayerAction{
    int count = 1;
    public abstract void doAction(Bomber b);
}
class SpawnItem extends PlayerAction{
    public void doAction(Bomber b){
        float posX = b.getPosX();
        float posY = b.getPosY();
        posX += b.getDirection().x * 10;
        posY += b.getDirection().y * 10;
        Renderer.setToBatch(new SmallBombItem(posX,posY),3);
    }
}
