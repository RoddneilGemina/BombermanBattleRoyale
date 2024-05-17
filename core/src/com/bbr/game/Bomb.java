package com.bbr.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Bomb {
    private Body body;
    private Sprite sprite;
    public static SpriteBatch batch;
    private static final Texture texture = new Texture("bomb.png");
    private int time, posX,posY,spanX, spanY, explosionDelay;
    public Bomb(int posX, int posY,int time, int spanX, int spanY, int explosionDelay){
        this.posX = posX; this.posY = posY;
        this.spanX = spanX; this.spanY = spanY;
        this.time = time; this.explosionDelay = explosionDelay;

        System.out.println("Bomb : "+posX +", "+posY);
        sprite = new Sprite((texture));
        float SCALE = MainGame.SCALE;

        // set body type and position
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(posX,posY);

        // make a physical box2d body in the box2d world
        body = MainGame.world.createBody(bd);

        // make the hitbox for the body;
        PolygonShape ps = new PolygonShape();
        ps.setAsBox((int)(SCALE/2),(int)(SCALE/2));
        body.createFixture(ps, 0.0f);

        ps.dispose(); // dispose polyshape because it has overstayed its welcome
        if(batch == null){
            batch = new SpriteBatch();
        }
    }
    public Bomb(int posX, int posY){
        this(posX,posY,60,3,3,0);
    }
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
        batch.setProjectionMatrix(GameMap.camera.combined);

        float scaleFactor = MainGame.SCALE/80f;
        if(sprite!=null) batch.draw(sprite, (body.getPosition().x - MainGame.SCALE/2), (body.getPosition().y - MainGame.SCALE/2),
                80*scaleFactor, 80*scaleFactor);
    }
    private void explode(){
        posX = Math.round(body.getPosition().x);
        posY = Math.round(body.getPosition().y);
        int maxX = spanX>>1, maxY = spanY>>1;
        MainGame.bombsAndExplosions.add(new Explosion(posX,posY,20));
        float scale = MainGame.SCALE;
        for(int i=1;i <= maxX && i <= maxY; i++){
            if(i<=maxX){
                MainGame.bombsAndExplosions.add(new Explosion((int)(posX+i*scale),posY,20));
                MainGame.bombsAndExplosions.add(new Explosion((int)(posX-i*scale),posY,20));
            }
            if(i<=maxY){
                MainGame.bombsAndExplosions.add(new Explosion(posX,(int)(posY+i*scale),20));
                MainGame.bombsAndExplosions.add(new Explosion(posX,(int)(posY-i*scale),20));
            }
        }
        dispose();
    }
    public void dispose(){
        MainGame.world.destroyBody(body);
        MainGame.bombsAndExplosions.remove(this);
        sprite = null;
        body = null;
    }
}
class Explosion {
    private Body body;
    private static SpriteBatch batch;
    private static final Texture texture = new Texture("boom.png");
    private Sprite sprite;
    private int posX,posY,time;
    public Explosion(int posX, int posY, int time) {
        this.posX = posX;
        this.posY = posY;
        this.time = time;
        if(batch == null) batch = Bomb.batch;
        sprite = new Sprite(texture);
        sprite.setRegion(0,0,16,16);

        float SCALE = MainGame.SCALE;
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        //bd.position.set((int)(5*posX*SCALE + SCALE/2),(int)(5*posY*SCALE + SCALE/2));
        bd.position.set(posX,posY);
        body = MainGame.world.createBody(bd);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox((int)(SCALE/2),(int)(SCALE/2));
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.filter.categoryBits = MainGame.EXPLOSION_BITS;
        fd.filter.maskBits = 0b0010;
        body.createFixture(fd);
        ps.dispose();
        if(batch == null){
            batch = new SpriteBatch();
        }
    }
    int frames = 0;
    public void render(){
        if(frames++ == 10) sprite.setRegion(16,0,16,16);
        if(frames >= 30) dispose();
        batch.setProjectionMatrix(GameMap.camera.combined);
        if(sprite!=null) batch.draw(sprite, (body.getPosition().x - MainGame.SCALE/2), (body.getPosition().y - MainGame.SCALE/2),
                80* MainGame.SCALE/80f, 80* MainGame.SCALE/80f);
    }
    public void dispose(){
        MainGame.world.destroyBody(body);
        MainGame.bombsAndExplosions.remove(this);
        sprite = null;
        body = null;
    }

}
class Builder {
    private int posX,posY;
    private int time = 60;
    private int spanX = 3;
    private int spanY = 3;
    private int explosionDelay = 0;
    public Builder(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    public void setTime(int time){this.time=time;}
    public void setSpan(int span){spanX = span; spanY = span;}
    public void setSpanX(int spanX){this.spanX=spanX;}
    public void setSpanY(int spanY){this.spanY=spanY;}
    public void setExplosionDelay(int explosionDelay){this.explosionDelay=explosionDelay;}
    public Bomb build(){
        return new Bomb(posX,posY,time,spanX,spanY,explosionDelay);
    }
}
