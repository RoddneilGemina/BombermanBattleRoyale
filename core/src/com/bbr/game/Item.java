package com.bbr.game;

import com.bbr.game.Utils.BodyBuilder;
import com.bbr.game.Utils.Collider;
import com.bbr.game.Utils.WorldObj;

public abstract class Item extends WorldObj implements Collider {
    public Item(float posX, float posY){
        super(posX,posY,0.5f,0.5f);
        body.getWorld().destroyBody(body);
        body = new BodyBuilder(posX,posY).sizeX(0.5f).sizeY(0.5f).userData(this).maskBits((short) 0b1100)
                .categoryBits((short) 0b1010).build();
    }
}
abstract class Collectible extends Item {
    PlayerAction playerAction;
    public Collectible(float posX, float posY) {
        super(posX, posY);
    }

    @Override
    public void collide(Object body) {
        //Console.print("COLLIDED WITH "+body.toString());
        if(body instanceof Bomber){
            ((Bomber)body).addToInventory(playerAction);
        }

        super.dispose();
    }

}
class SmallBombItem extends Collectible {
    public SmallBombItem(float posX, float posY) {
        super(posX, posY);
        playerAction = new SmallBomb();
    }
}
