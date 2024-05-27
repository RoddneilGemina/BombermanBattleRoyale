package com.bbr.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bbr.game.Utils.Collider;
import com.bbr.game.Utils.Controllable;
import com.bbr.game.Utils.Renderer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bomber implements Controllable, Collider {
    public int id;
    public int lives;
    private Body body;
    private Sprite sprite;
    private Vector2 direction;
    private static Texture texture = null;
    public static SpriteBatch batch;
    private int posX;
    private int posY;
    private boolean isNPC = true;
    private int health = 100;
    private HealthDisplay healthDisplay;
    private ItemDisplay itemDisplay;
    private ArrayList<PlayerAction> inventory;
    private int inventoryIndex = 0;
    private SkillAction skill;

    float width = 65;
    float height= width*(25.0f/16.0f);

    public Bomber(int posX, int posY, int id){
        effects = new ArrayList<>();
        lives = 3;
        inventory = new ArrayList<>();
        //linventory.add(new SmallBomb());
        skill = new Dash();
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        if(texture == null) texture = new Texture("bomberman.png");
        sprite = new Sprite(texture);

        sprite.setSize(width* MainGame.SCALE/80f,height* MainGame.SCALE/80f);
        direction = new Vector2(1,1);
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
        itemDisplay = new ItemDisplay(this);
    }
    public void collide(Object o){
        if(o instanceof Explosion && canGetHit()){
            health -= ((Explosion)o).getDamage();
            addStatusEffect(new Hit(this));
            if(health <= 0){
                Console.print("I DEAD ! ! !");
                lives--;
                health = 100;
            }
        }
    }

    public SpriteBatch getBatch(){return batch;}
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

        batch = (SpriteBatch) GameMap.renderer.getBatch();
    }
    public void addHealth(int h){this.health+=h;}
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
    int framedex = 1;
    int dirindex = 1;
    int framectr = 0;
    int framedir = 1;
    private static final int FRAMEMAX = 7;
    public void render()
    {
        if(direction.y > 0.4f)
            dirindex = 0;
        else if(direction.x > 0.6f)
            dirindex = 3;
        else if(direction.x < -0.6f)
            dirindex = 2;
        else
            dirindex = 1;
        if(body.getLinearVelocity().len() > 0.2f){
            if(framectr++>=FRAMEMAX){
                framedex+=framedir;
                if(framedex!=1) framedir*=-1;
                framectr = 0;
            }
        } else {
            framedex = 1;
        }
        sprite.setRegion(16*framedex,25*dirindex,16,25);
        posX = Math.round(body.getPosition().x);
        posY = Math.round(body.getPosition().y);
        direction = body.getLinearVelocity();
        direction.nor();
        effectsDuring();
        sprite.setCenter(body.getPosition().x,body.getPosition().y+2);
        sprite.draw(batch);
//        batch.draw(
//                sprite,
//                body.getPosition().x ,
//                body.getPosition().y - MainGame.SCALE/2 + 1,
//                width* MainGame.SCALE/80f,
//                height* MainGame.SCALE/80f
//        );
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
    public void action1(){
        if(!inventory.isEmpty()){
            inventoryIndex = Math.min(inventoryIndex,inventory.size()-1);
            if(inventoryIndex < 0) inventoryIndex = 0;
            inventory.get(inventoryIndex).doAction(this);
            if(!(inventory.get(inventoryIndex) instanceof SmallBomb)) {
                inventory.get(inventoryIndex).count--;
            }
            if(inventory.get(inventoryIndex).count==0){
                inventory.remove(inventoryIndex);
                inventoryIndex = Math.min(inventory.size()-1,inventoryIndex);
                if(inventoryIndex<0) inventoryIndex=0;
            }
        }
    }
    public void action2(){skill.doAction(this);}
    public void action3(){if(!inventory.isEmpty()) inventoryIndex = (inventory.size() + inventoryIndex-1)%inventory.size();}
    public void action4(){if(!inventory.isEmpty()) inventoryIndex = (inventoryIndex+1)%inventory.size();}
    public Vector2 getDirection(){return direction;}
    public void setDirection(Vector2 dir){this.direction = dir;}
    public static final int INVENTORY_MAX = 7;
    public boolean addToInventory(PlayerAction pa){
        if(inventory.size()<INVENTORY_MAX) {
            inventory.add(pa);
            return true;
        }

        return false;
    }
    private boolean canGetHit(){
        synchronized (effects){
            for(int i=0;i<effects.size(); i++){
                if(
                        effects.get(i) instanceof Hit ||
                                effects.get(i) instanceof Invincible
                )
                    return false;
            }
        }
        return true;
    }
    public ArrayList<PlayerAction> getInventory(){
        return inventory;
    }
    public int getSelectedIndex(){
        return inventoryIndex;
    }
    private ArrayList<StatusEffect> effects;
    public void addStatusEffect(StatusEffect se){
        synchronized (effects){
            effects.add(se);
            se.start();
        }
    }
    public ArrayList<StatusEffect> getStatusEffects(){return effects;}
    private void effectsDuring(){
        synchronized (effects) {
            for (int i = 0; i < effects.size(); i++) {
                effects.get(i).effectDuring();
            }
        }
    }
    public Sprite getSprite(){return sprite;}
}
