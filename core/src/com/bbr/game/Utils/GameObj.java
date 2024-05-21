package com.bbr.game.Utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bbr.game.MainGame;

public abstract class GameObj {
    protected Vector2 position, size, offset;
    protected boolean visible = true;
    protected boolean centered = false;
    protected SpriteBatch batch;
    protected Texture texture;
    protected Sprite sprite;
    protected float scaleFactor;
    public GameObj(){this(0,0);}
    public GameObj(float posX, float posY){this(posX,posY,1,1);}
    public GameObj(float posX, float posY, float sizeX, float sizeY){this(posX,posY,sizeX,sizeY,0,0);}
    public GameObj(float posX, float posY, float sizeX, float sizeY, float offsetX, float offsetY){
        position = new Vector2(posX, posY);
        size = new Vector2(sizeX, sizeY);
        offset = new Vector2(offsetX, offsetY);
        scaleFactor = MainGame.SCALE;
        texture = new Texture("placeholdertile.png");
        sprite = new Sprite(texture);
        sprite.setRegion(0,0,16,16);
    }

    public abstract void render();
    public void create(){}
    public void dispose(){
        texture.dispose();
        NewGame.removeFromBatches(this);
    }
    public void setBatch(SpriteBatch spriteBatch){batch = spriteBatch;}
    public SpriteBatch getBatch() {return batch;}
    public void setTexture(Texture texture){ this.texture = texture;}
    public Texture getTexture() {return texture;}
    public void setSprite(Sprite sprite){this.sprite = sprite;}
    public Sprite getSprite() {return sprite;}
    public void setPosition(float posX, float posY){position.x=posX;position.y=posY;}
    public void setPositionX(float posX){position.x=posX;}
    public void setPositionY(float posY){position.y=posY;}
    public Vector2 getPosition() {return position.cpy();}
    public void setSize(float width, float height){size.x=width;size.y=height;}
    public void setSize(float size){this.size.x=size;this.size.y=size;}
    public void setWidth(float width){size.x=width;}
    public void setHeight(float height){size.y=height;}
    public Vector2 getSize(){return size.cpy();}
    public void setOffset(float offsetX, float offsetY){offset.x=offsetX; offset.y=offsetY;}
    public void setOffsetX(float offsetX){offset.x=offsetX;}
    public void setOffsetY(float offsetY){offset.y=offsetY;}
    public Vector2 getOffset() {return offset.cpy();}
    public void setVisible(boolean visible) {this.visible = visible;}
    public boolean isVisible(){return visible;}
    public void setCentered(boolean centered){this.centered=centered;}
    public boolean isCentered(){return centered;}
}
