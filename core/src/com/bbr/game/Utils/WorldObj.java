package com.bbr.game.Utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.bbr.game.Common;
import com.bbr.game.MainGame;

public class WorldObj extends GameObj {
    protected Body body;
    public WorldObj(){this(0,0,1f,1f);}

    public WorldObj(float posX, float posY, float sizeX, float sizeY){
        super(posX,posY,sizeX,sizeY);
        body = Common.makeBox(this.posX,this.posY,this.sizeX,this.sizeY);
    }
    public void render(){
        posX = body.getPosition().x;
        posY = body.getPosition().y;
        if(sprite!=null && visible) batch.draw(
                sprite,
                (posX - sizeX*scaleFactor/2),
                (posY - sizeX*scaleFactor/2),
                sizeX*scaleFactor,
                sizeY*scaleFactor
        );
    }
}
