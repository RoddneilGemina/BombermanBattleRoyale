package com.bbr.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bbr.game.Utils.Collider;
import com.bbr.game.Utils.Controllable;
import com.bbr.game.Utils.Renderer;
import com.bbr.game.shared.Message;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Bomber implements Controllable, Collider {
    public int id;
    private Body body;
    private Sprite sprite;
    private static Texture texture = null;
    public static SpriteBatch batch;
    private int posX;
    private int posY;
    private boolean isNPC = true;
    private int health = 100;
    private HealthDisplay healthDisplay;
    public Bomber(int posX, int posY, int id){
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        if(texture == null) texture = new Texture("bomber.png");
        sprite = new Sprite(texture);
        if(true){
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(posX, posY);

            body = MainGame.world.createBody(bodyDef);
            body.setUserData(this);

            MassData md = new MassData();
            md.mass = 0.00001f;
            body.setMassData(md);
        }
        healthDisplay = new HealthDisplay(this);
    }
    public void collide(Object o){
        if(o instanceof Explosion){
            health -= 10;
            Console.print("OUCH!!!");
        }
    }

    public Bomber(int id) {
        this((int)(MainGame.SCALE + MainGame.SCALE/2),(int)(MainGame.SCALE + MainGame.SCALE/2),id);
        isNPC = false;

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.25f * MainGame.SCALE, 0.25f * MainGame.SCALE);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.filter.categoryBits = 0b1100;
        body.createFixture(fixtureDef);
        body.setLinearDamping(30f);
        boxShape.dispose();
    }
    public int getHealth(){return health;}
    public Body getBody(){return body;}
    int speed = 500;
    public void moveBody(float x, float y){
        body.applyLinearImpulse(
                new Vector2(
                        x*speed,
                        y*speed),
                body.getWorldCenter(),
                true
        );
    }
    public void teleport(float x, float y){
        body.setTransform(x,y,body.getAngle());
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
        batch.draw(
                sprite,
                body.getPosition().x - MainGame.SCALE/2,
                body.getPosition().y - MainGame.SCALE/2,
                75* MainGame.SCALE/80f,
                75* MainGame.SCALE/80f
        );

    }
    public void dropBomb(){
//        if(MainGame.gameService!=null && MainGame.gameService.getType().matches("client")){
//            MainGame.gameService.sendBomb((int) (10 * Math.round((posX - MainGame.SCALE + 4) / 10) + MainGame.SCALE / 2), (int) (10 * Math.round((posY - MainGame.SCALE + 3) / 10) + MainGame.SCALE / 2), new AsyncCallback<Bomb>() {
//                @Override
//                public void onFailure(Throwable throwable) {
//                    Console.print("BOMB NOT SENT");
//                }
//
//                @Override
//                public void onSuccess(Bomb bomb) {
//                    Console.print("BOMB SUCCESSFULLY SENT");
//                }
//            });
//        }

        Renderer.setToBatch(
                new Bomb(
                        (int)(10*Math.round((posX-MainGame.SCALE+4)/10)+MainGame.SCALE/2),
                        (int)(10*Math.round((posY-MainGame.SCALE+3)/10)+MainGame.SCALE/2)
                ),3
        );
//        MainGame.gameClient.addBomb((int)(10*Math.round((posX-MainGame.SCALE+4)/10)+MainGame.SCALE/2),(int)(10*Math.round((posY-MainGame.SCALE+3)/10)+MainGame.SCALE/2),id);
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
    public void actionUp(){moveBody(0,1);}
    public void actionDown(){moveBody(0,-1);}
    public void actionLeft(){moveBody(-1,0);}
    public void actionRight(){moveBody(1,0);}
    public void action1(){dropBomb();}
}
