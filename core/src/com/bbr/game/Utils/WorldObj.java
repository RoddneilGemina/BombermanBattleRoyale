package com.bbr.game.Utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.bbr.game.MainGame;

public class WorldObj extends GameObj {
    protected Body body;
    public WorldObj(){this(0,0,1f,1f,0,0);}
    public WorldObj(float posX, float posY){this(posX,posY,1,1,0,0);}
    public WorldObj(float posX, float posY, float sizeX, float sizeY){this(posX,posY,sizeX,sizeY,0,0);}

    public WorldObj(float posX, float posY, float sizeX, float sizeY, float offsetX, float offsetY){
        super(posX,posY,sizeX,sizeY,offsetX,offsetY);
        centered=true;
        body = BoxUtils.makeBox(position.x,position.y,size.x,size.y);
    }
    public void render(){
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;
        if(sprite!=null && visible)
            if(centered)
                batch.draw(
                    sprite,
                    position.x - size.x*scaleFactor/2,
                    position.y - size.y*scaleFactor/2,
                    size.x*scaleFactor,
                    size.y*scaleFactor
                );
            else
                batch.draw(
                        sprite,
                        position.x,
                        position.y,
                        size.x*scaleFactor,
                        size.y*scaleFactor
                );
    }
    public void dispose(){
        if(body!=null){
            body.getWorld().destroyBody(body);
        }
        super.dispose();
    }
}
