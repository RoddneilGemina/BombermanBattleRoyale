package com.bbr.game.Utils;

import com.badlogic.gdx.math.Vector2;

public class ScreenObj extends GameObj{
    protected Vector2 align;
    public ScreenObj(){this(0,0,16,16,0,0);}
    public ScreenObj(float posX, float posY){this(posX,posY,16,16,0,0);}
    public ScreenObj(float posX, float posY, float sizeX, float sizeY){this(posX,posY,sizeX,sizeY,0,0);}

    public ScreenObj(float posX, float posY, float sizeX, float sizeY, float offsetX, float offsetY){
        super(posX,posY,sizeX,sizeY,offsetX,offsetY);
    }
    public void render(){
        if(sprite!=null && visible)
            if(centered)
                batch.draw(
                    sprite,
                    position.x - size.x/2,
                    position.y - size.y/2,
                    size.x,
                    size.y
                );
            else
                batch.draw(
                        sprite,
                        position.x,
                        position.y,
                        size.x,
                        size.y
                );
    }

}
