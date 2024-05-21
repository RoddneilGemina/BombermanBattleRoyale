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
