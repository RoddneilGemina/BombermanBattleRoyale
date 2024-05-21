package com.bbr.game.Utils;

import com.badlogic.gdx.physics.box2d.*;
import com.bbr.game.MainGame;

import javax.crypto.BadPaddingException;

public class BoxUtils {
    public static World world;
    public static Body makeBox(float posX, float posY){
        return makeBox(posX,posY,1.0f);
    }
    public static Body makeBox(float posX, float posY, float size){
        return makeBox(posX,posY,size,size);
    }
    public static Body makeBox(float posX, float posY, float sizeX, float sizeY){
        float SCALE = MainGame.SCALE;
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(posX,posY);
        Body body = MainGame.world.createBody(bd);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(sizeX*SCALE/2,sizeY*SCALE/2);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.filter.categoryBits = MainGame.EXPLOSION_BITS;
        fd.filter.maskBits = 0b0010;
        body.createFixture(fd);

        ps.dispose();
        return body;
    }
}
class BoxBuilder {
    private float posX, posY, sizeX, sizeY, linearDamping, mass;
    private short categoryBits, maskBits;
    private BodyDef.BodyType type;
    private Object userData;
    public BoxBuilder(float posX, float posY){
        this.posX = posX;
        this.posY = posY;
    }
    public BoxBuilder type(BodyDef.BodyType type){this.type = type; return this;}
    public BoxBuilder posX(float posX){this.posX=posX; return this;}
    public BoxBuilder posY(float posY){this.posY=posY; return this;}
    public BoxBuilder sizeX(float sizeX){this.sizeX=sizeX; return this;}
    public BoxBuilder sizeY(float sizeY){this.sizeY=sizeY; return this;}
    public BoxBuilder linearDamping(float linearDamping){this.linearDamping=linearDamping; return this;}
    public BoxBuilder categoryBits(short categoryBits){this.categoryBits=categoryBits; return this;}
    public BoxBuilder maskBits(short maskBits){this.maskBits=maskBits; return this;}
    public BoxBuilder userData(Object userData){this.userData=userData; return this;}
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
        fd.shape = ps;
        fd.filter.categoryBits = categoryBits;
        fd.filter.maskBits = maskBits;
        body.createFixture(fd);

        ps.dispose();
        return body;
    }

}
