package com.bbr.game.Utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bbr.game.MainGame;

public class BodyBuilder {
    private float posX, posY, sizeX = 1f, sizeY = 1f, linearDamping, mass;
    private boolean sensor;
    private short categoryBits, maskBits;
    private BodyDef.BodyType type = BodyDef.BodyType.DynamicBody;
    private Object userData;
    public BodyBuilder(float posX, float posY){
        this.posX = posX;
        this.posY = posY;
    }
    public BodyBuilder sensor(boolean isSensor){sensor=isSensor; return this;}
    public BodyBuilder type(BodyDef.BodyType type){this.type = type; return this;}
    public BodyBuilder posX(float posX){this.posX=posX; return this;}
    public BodyBuilder posY(float posY){this.posY=posY; return this;}
    public BodyBuilder sizeX(float sizeX){this.sizeX=sizeX; return this;}
    public BodyBuilder sizeY(float sizeY){this.sizeY=sizeY; return this;}
    public BodyBuilder linearDamping(float linearDamping){this.linearDamping=linearDamping; return this;}
    public BodyBuilder categoryBits(short categoryBits){this.categoryBits=categoryBits; return this;}
    public BodyBuilder maskBits(short maskBits){this.maskBits=maskBits; return this;}
    public BodyBuilder userData(Object userData){this.userData=userData; return this;}
    public Body build(){
        float SCALE = MainGame.SCALE;
        BodyDef bd = new BodyDef();
        bd.type = type;
        bd.position.set(posX,posY);
        Body body = MainGame.world.createBody(bd);
        body.setLinearDamping(linearDamping);
        body.setUserData(userData);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(sizeX*SCALE/2,sizeY*SCALE/2);

        FixtureDef fd = new FixtureDef();
        fd.isSensor = sensor;
        fd.shape = ps;
        fd.filter.categoryBits = categoryBits;
        fd.filter.maskBits = maskBits;
        body.createFixture(fd);

        ps.dispose();
        return body;
    }

}