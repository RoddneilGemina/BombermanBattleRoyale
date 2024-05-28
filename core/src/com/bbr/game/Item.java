package com.bbr.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.bbr.game.Utils.BodyBuilder;
import com.bbr.game.Utils.Collider;
import com.bbr.game.Utils.Renderer;
import com.bbr.game.Utils.WorldObj;

public abstract class Item extends WorldObj implements Collider {
    public Item(float posX, float posY){
        super(posX,posY,0.5f,0.5f);
        body.getWorld().destroyBody(body);
        body = new BodyBuilder(posX,posY).sensor(true).sizeX(0.5f).sizeY(0.5f).userData(this).maskBits((short) 0b1100)
                .categoryBits((short) 0b1010).build();
    }
}
class Collectible extends Item {
    PlayerAction playerAction;
    public Collectible(float posX, float posY) {
        super(posX, posY);
    }

    @Override
    public void collide(Object body) {
        //Console.print("COLLIDED WITH "+body.toString());
        if(body instanceof Bomber){
            if(((Bomber)body).addToInventory(playerAction)){
                super.dispose();
            }
        }
    }

    public void setAction(PlayerAction a){
        this.playerAction = a;
    }

}
class ItemSpawner {
    private static final Class<?>[] ITEMTYPES = new Class[]{MediumCrossBomb.class,Heal.class};
    private static final Texture txt = new Texture("items.png");
    public static void createItem(PlayerAction a, int posX, int posY){
        Collectible item = new Collectible(posX,posY);
        item.setAction(a);
        Sprite spr = new Sprite(txt);
        spr.setRegion(a.getRx()*16,a.getRy()*16,16,16);
        item.setSize(1f);
        item.setSprite(spr);
        Renderer.setToBatch(item,3);
    }
    public static void spawnItem(int num){
        int x,y;
        Class<?> itemClass;
        for(int i = 0; i < num; i++){
            do{
                x = ((int)(Math.random()*1000))%GameMap.MAP_W;
                y = ((int)(Math.random()*1000))%GameMap.MAP_H;
            }while(GameMap.map[y][x]!=0);
            x = (int)(x*MainGame.SCALE + MainGame.SCALE/2);
            y = (int)(y*MainGame.SCALE + MainGame.SCALE/2);
            itemClass = ITEMTYPES[((int)(Math.random()*1000))%ITEMTYPES.length];
            try {
                createItem((PlayerAction) itemClass.getConstructor().newInstance(),x,y);
            } catch (Exception e){
                Console.print("Ahh!");
            }
        }
    }
}
