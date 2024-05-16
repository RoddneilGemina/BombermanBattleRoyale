package com.bbr.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.w3c.dom.Text;

public class Bomber {
    private Body body;
    private Sprite sprite;
    private Texture texture;
    private SpriteBatch batch;
    private int posX;
    private int posY;

    public Bomber()
    {
        posX = (int)(BombermanBattleRoyaleGame.SCALE + BombermanBattleRoyaleGame.SCALE/2);
        posY = (int)(BombermanBattleRoyaleGame.SCALE + BombermanBattleRoyaleGame.SCALE/2);

        batch = new SpriteBatch();
        texture = new Texture("bomber.png");
        sprite = new Sprite(texture);
        this.sprite.setPosition(0, 0);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX,posY);

        body = BombermanBattleRoyaleGame.world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.25f*BombermanBattleRoyaleGame.SCALE,0.25f*BombermanBattleRoyaleGame.SCALE);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        MassData md = new MassData();
        md.mass = 0.00001f;
        body.setMassData(md);

        body.createFixture(fixtureDef);
        body.setLinearDamping(30f);

        boxShape.dispose();
    }
    public Body getBody(){return body;}
    int speed = 500;
    public void moveBody(float x, float y){
        body.applyLinearImpulse(new Vector2(x*speed,y*speed), body.getWorldCenter(), true);
//        body.applyForceToCenter(x*100,y*100,true);
        //body.setLinearVelocity(x*speed,y*speed);
        //body.applyForceToCenter(x*speed,y*speed,true);
//        System.out.println("Player pos " + body.getPosition());
    }
    int flipframe = 0;
    private static final int FLIPFRAMEMAX = 20;
    public void render()
    {
        flipframe++;
        if(flipframe>=FLIPFRAMEMAX){
            flipframe = 0;
            sprite.flip(true,false);
        }
        posX = Math.round(body.getPosition().x);
        posY = Math.round(body.getPosition().y);
        batch = (SpriteBatch) GameMap.renderer.getBatch();
        batch.begin();
        batch.draw(sprite, body.getPosition().x - BombermanBattleRoyaleGame.SCALE/2, body.getPosition().y - BombermanBattleRoyaleGame.SCALE/2, 75*BombermanBattleRoyaleGame.SCALE/80f, 75*BombermanBattleRoyaleGame.SCALE/80f);
        batch.end();
    }
    public void dropBomb(){
        BombermanBattleRoyaleGame.bombsAndExplosions.add(new Bomb(posX,posY));
        System.out.println("Player world pos : " +body.getPosition().x + ",  " +body.getPosition().y);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
