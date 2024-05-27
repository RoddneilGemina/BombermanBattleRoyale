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

    public Bomber(int posX, int posY, int id){
        lives = 3;
        inventory = new ArrayList<>();
        inventory.add(new SmallBomb());
        inventoryIndex = 1;
        skill = new Dash();
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        if(texture == null) texture = new Texture("bomberman.png");
        sprite = new Sprite(texture);
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
        if(o instanceof Explosion){
            health -= ((Explosion)o).getDamage();
            if(health <= 0){
                Console.print("I DEAD ! ! !");
                lives--;
                health = 100;
            }
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
        //Vector2 lastDir = direction.cpy();
        direction = body.getLinearVelocity();
        direction.nor();
        //if(direction.x == 0 && direction.y ==0) direction = lastDir;
        float width = 65;
        float height= width*(25.0f/16.0f);
        batch.draw(
                sprite,
                body.getPosition().x - MainGame.SCALE/2,
                body.getPosition().y - MainGame.SCALE/2 + 1,
                width* MainGame.SCALE/80f,
                height* MainGame.SCALE/80f
        );
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
    public void action3(){inventoryIndex = (inventory.size() + inventoryIndex-1)%inventory.size();}
    public void action4(){inventoryIndex = (inventoryIndex+1)%inventory.size();}
    public Vector2 getDirection(){return direction;}
    public void setDirection(Vector2 dir){this.direction = dir;}
    public boolean addToInventory(PlayerAction pa){
        if(inventory.size()<5) {
            inventory.add(pa);
            return true;
        }

        return false;
    }
    public ArrayList<PlayerAction> getInventory(){
        return inventory;
    }
    public int getSelectedIndex(){
        return inventoryIndex;
    }
}
