package com.bbr.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.bbr.game.Utils.Renderer;

public abstract class PlayerAction{
    int count = 1;
    public int rX = 0, rY = 0;
    public int getRx(){return rX;}
    public int getRy(){return rY;}
    public static Texture texture = new Texture("items.png");
    protected Sprite sprite = new Sprite(texture);
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
