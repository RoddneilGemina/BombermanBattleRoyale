package com.bbr.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bbr.game.Utils.Controllable;
import com.bbr.game.Utils.WorldObj;

public class ExampleObj extends WorldObj implements Controllable {
    public ExampleObj(float posX, float posY, float sizeX, float sizeY){
        super(posX,posY,sizeX,sizeY);
    }
    public void moveBody(float x, float y){
        int speed = 500;
        body.applyLinearImpulse(
                new Vector2(
                        x*speed,
                        y*speed),
                body.getWorldCenter(),
                true
        );
    }

    public void actionUp(){moveBody(0,1);}
    public void actionDown(){moveBody(0,-1);}
    public void actionLeft(){moveBody(-1,0);}
    public void actionRight(){moveBody(1,0);}
}
