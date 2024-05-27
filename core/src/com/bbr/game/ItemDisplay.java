package com.bbr.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.bbr.game.Utils.Renderer;
import com.bbr.game.Utils.ScreenObj;

import java.util.ArrayList;

public class ItemDisplay extends ScreenObj {
    Bomber bomber;
    ArrayList<PlayerAction> items;
    Sprite boxSprite;
    private static Texture boxTexture = new Texture("itembox.png");

    public ItemDisplay(Bomber b){
        bomber = b;
        items = b.getInventory();
        boxSprite = new Sprite(boxTexture);
        Renderer.setToScreenBatch(this);
        texture = new Texture("items.png");
        sprite = new Sprite(texture);
    }

    public void render(){
        int index = bomber.getSelectedIndex();
        for(int i = 0; i < 5; i++){
            if(i == index){
                boxSprite.setRegion(0,0,16,16);
                boxSprite.setAlpha(1f);
            } else {
                boxSprite.setRegion(16, 0, 16, 16);
                boxSprite.setAlpha(0.1f);
            }
            batch.draw(
                    boxSprite,
                    10+16*i*5,
                    380,
                    16*5,
                    16*5
            );
            if(i>=items.size()){
                continue;
            }
            sprite.setRegion(16*items.get(i).getRx(),16*items.get(i).getRy(),16,16);
            batch.draw(
                    sprite,
                    10+16*i*5,
                    380,
                    16*5,
                    16*5
            );
        }
    }
}
