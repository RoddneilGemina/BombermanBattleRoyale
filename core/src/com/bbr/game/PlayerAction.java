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