package com.bbr.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bbr.game.Utils.GameObj;
import com.bbr.game.Utils.Renderer;
import com.bbr.game.Explosion;

import java.io.Serializable;
import java.util.ArrayList;

public class Bomb extends GameObj implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id = -1;
    private transient Body body;
    private transient Sprite sprite;
    private transient static final Texture texture = new Texture("bomb.png");
    private int time, posX,posY,spanX, spanY, explosionDelay, damage, bomberID;
    public transient ArrayList<Object> container;
    public Bomb(int bomberID, int posX, int posY,int time, int spanX, int spanY, int explosionDelay, int damage){
        this.bomberID = bomberID;
        this.posX = posX; this.posY = posY;
        this.spanX = spanX; this.spanY = spanY;
        this.time = time; this.explosionDelay = explosionDelay;
        this.damage = damage;
        // TEMPORARY ! ! !
        id = (int)Math.round(Math.random()*2000);

        sprite = new Sprite((texture));
        float SCALE = MainGame.SCALE;

        // set body type and position
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(posX,posY);

        // make a physical box2d body in the box2d world
        body = MainGame.world.createBody(bd);
        body.setUserData(this);

        // make the hitbox for the body;
        PolygonShape ps = new PolygonShape();
        ps.setAsBox((int)(SCALE/2),(int)(SCALE/2));
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.filter.categoryBits = MainGame.EXPLOSION_BITS;
        fd.filter.maskBits = 0b0010;
        body.createFixture(fd);

        // dispose polyshape because it has overstayed its welcome
        ps.dispose();
        //Console.print("bomb dropped!");
    }
//    public Bomb(int posX, int posY){
//        this(posX,posY,60,5,5,10,10);
//    }
    private int animFrame = 0;
    private static final int ANIMAX = 10;
    private boolean isFrame2 = false;
    public void render(){
        if(++animFrame >= ANIMAX){
            animFrame = 0;
            isFrame2 = !isFrame2;
        }
        if(--time <= 0){
            explode();
        }
        //batch.draw(sprite,)
        int i = 0;
        if(isFrame2) i++;
        if(sprite!=null) sprite.setRegion(i*16,0,16,16);
        //batch.setProjectionMatrix(GameMap.camera.combined);

        float scaleFactor = MainGame.SCALE/80f;
        if(sprite!=null) batch.draw(sprite, (body.getPosition().x - MainGame.SCALE/2), (body.getPosition().y - MainGame.SCALE/2),
                80*scaleFactor, 80*scaleFactor);
    }
    private void explode(){
        posX = Math.round(body.getPosition().x);
        posY = Math.round(body.getPosition().y);
        int maxX = spanX>>1, maxY = spanY>>1;
        int cx = GameMap.posToCoord(posX);
        int cy = GameMap.posToCoord(posY);
        //Console.print("EXPLOSION: "+cx+" "+cy);
        Renderer.setToBatch(new Explosion(bomberID,posX,posY,0,damage),3);
        float scale = MainGame.SCALE;
        for(int i=1;i <= maxX; i++){
            try{ if(GameMap.map[cy][cx+i] == 0){
                Renderer.setToBatch(new Explosion(bomberID,(int)(posX+i*scale),posY,explosionDelay*i,damage),3);
            } else break; } catch (Exception e){ break; }
        }
        for(int i=1;i <= maxX; i++){
            try{ if(GameMap.map[cy][cx-i] == 0){
                    Renderer.setToBatch(new Explosion(bomberID,(int)(posX-i*scale),posY,explosionDelay*i,damage),3);
                } else break; } catch (Exception e){ break; }
        }
        for(int i=1;i <= maxY; i++){
            try{ if(GameMap.map[cy+i][cx] == 0){
                Renderer.setToBatch(new Explosion(bomberID,posX,(int)(posY+i*scale),explosionDelay*i,damage),3);
            } else break; } catch (Exception e){ break; }
        }
        for(int i=1;i <= maxY; i++){
            try{ if(GameMap.map[cy-i][cx] == 0){
                Renderer.setToBatch(new Explosion(bomberID,posX,(int)(posY-i*scale),explosionDelay*i,damage),3);
            } else break; } catch (Exception e){ break; }
        }
        dispose();
    }
    public void dispose(){
        MainGame.world.destroyBody(body);
        Renderer.removeFromBatch(this,3);
        sprite = null;
        body = null;
    }
}
enum BombPattern{
    CROSS,
    X,
    BOX,
    RANDOM
}
