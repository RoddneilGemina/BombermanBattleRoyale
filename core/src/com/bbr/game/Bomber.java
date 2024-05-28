package com.bbr.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bbr.game.Utils.Collider;
import com.bbr.game.Utils.Controllable;
import com.bbr.game.Utils.Renderer;
import com.bbr.net.Network;
import jdk.tools.jmod.Main;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bomber implements Controllable, Collider {
    public int id;
    public int lives;
    private Body body;
    private String name;
    public void setName(String name){this.name=name;}
    private Sprite sprite;
    private Vector2 direction;
    private PlayerInfo info;
    private static Texture texture = null;
    public static SpriteBatch batch;
    private float posX;
    private float posY;
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
        inventory.add(new SmallBomb());
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

    }
    public void collide(Object o){
        if(o instanceof Explosion && canGetHit()){
            health -= ((Explosion)o).getDamage();
            addStatusEffect(new Hit(this));
            if(health <= 0){
                lives--;
                health = 100;

                Bomber killer = MainGame.bombers.get(((Explosion)o).getBomberID());
                if(killer == this){
                    Network.announce(getName()+" had an oopsie.");
                } else if(killer != null){
                    Network.announce(getName()+" was bombed by "+killer.getName());
                } else {
                    Network.announce(getName()+" fell out of the world.");
                }
            }
        }
    }

    public SpriteBatch getBatch(){return batch;}
    public Bomber(int id) {
        this((int)(MainGame.SCALE + MainGame.SCALE/2),(int)(MainGame.SCALE + MainGame.SCALE/2),id);
        isNPC = false;
        healthDisplay = new HealthDisplay(this);
        itemDisplay = new ItemDisplay(this);

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
    int framedex = 1;
    int dirindex = 1;
    int framectr = 0;
    int framedir = 1;
    private static final int FRAMEMAX = 7;
    public void render() {
        if(isNPC){
            if(info == null){
                info = new PlayerInfo(this);
            }
        } else {
            direction = body.getLinearVelocity();
            direction.nor();
        }
        if((isNPC) ||body.getLinearVelocity().len() > 0.2f){
            if(framectr++>=FRAMEMAX){
                framedex+=framedir;
                if(framedex!=1) framedir*=-1;
                framectr = 0;
            }
        } else {
            framedex = 1;
        }
        if(direction.y > 0.4f)
            dirindex = 0;
        else if(direction.x > 0.6f)
            dirindex = 3;
        else if(direction.x < -0.6f)
            dirindex = 2;
        else
            dirindex = 1;

        sprite.setRegion(16*framedex,25*dirindex,16,25);
        posX = Math.round(body.getPosition().x);
        posY = Math.round(body.getPosition().y);

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
        if(MainGame.gameClient!=null){
            MainGame.gameClient.updatePlayerBomber(this);
        }
    }
    public String getName(){
        if(name != null) return name;
        return "Bomber #"+id;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
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
    private final ArrayList<StatusEffect> effects;
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
    public Network.PlayerRep pack(){
        Network.PlayerRep pr = new Network.PlayerRep();
        pr.bomberID = id;
        pr.posX = body.getPosition().x;
        pr.posY = body.getPosition().y;
        pr.health = health;
        pr.dirX = direction.x;
        pr.dirY = direction.y;
        pr.name = name;
        return pr;
    }
    public void unpack(Network.PlayerRep pr){
        float x0 = body.getPosition().x;
        float y0 = body.getPosition().y;
        body.setTransform((pr.posX+x0)/2,(pr.posY+y0)/2,body.getAngle());
        direction.x = pr.dirX;
        direction.y = pr.dirY;
        health = pr.health;
        name = pr.name;
    }
    public void removeDisplays(){
        if(healthDisplay!=null) healthDisplay.dispose();
        if(itemDisplay!=null) itemDisplay.dispose();
    }
}
