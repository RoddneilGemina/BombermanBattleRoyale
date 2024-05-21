package com.bbr.game.Utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.bbr.game.MainGame;

public abstract class GameObj {
    protected float posX, posY, sizeX, sizeY;
    protected boolean visible = true;
    protected SpriteBatch batch;
    protected Texture texture;
    protected Sprite sprite;
    protected float scaleFactor;
    public void setVisible(boolean visible) {this.visible = visible;}
    public GameObj(){
        scaleFactor = MainGame.SCALE;
        texture = new Texture("placeholdertile.png");
        sprite = new Sprite(texture);
        sprite.setRegion(0,0,16,16);
    }
    public GameObj(float posX, float posY, float sizeX, float sizeY){
        this();
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public abstract void render();
    public void create(){}
    public void dispose(){}
    public void setBatch(SpriteBatch spriteBatch){batch = spriteBatch;}
    public SpriteBatch getBatch() {return batch;}
    public void setTexture(Texture texture){ this.texture = texture;}
    public Texture getTexture() {return texture;}
    public void setSprite(Sprite sprite){this.sprite = sprite;}
    public Sprite getSprite() {return sprite;}
}
