package com.bbr.game;

import com.badlogic.gdx.physics.box2d.*;

public class Common {
    public static World world;
    public static Body makeBox(int posX, int posY){
        float SCALE = MainGame.SCALE;
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(posX,posY);
        Body body = MainGame.world.createBody(bd);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox((int)(SCALE/2),(int)(SCALE/2));
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.filter.categoryBits = MainGame.EXPLOSION_BITS;
        fd.filter.maskBits = 0b0010;
        body.createFixture(fd);
        ps.dispose();
        return body;
    }
}
