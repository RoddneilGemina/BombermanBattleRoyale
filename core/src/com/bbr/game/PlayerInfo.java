package com.bbr.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.bbr.game.Utils.Renderer;
import com.bbr.game.Utils.WorldObj;

public class PlayerInfo extends WorldObj {
    Bomber bomber;
    private static final float WIDTH = 50;
    BitmapFont font;
    public PlayerInfo(Bomber bomber){
        super(320 - 3, 300,16,16);
        font = new BitmapFont();
        this.bomber = bomber;
        Renderer.setToBatch(this,4);
        font.getData().setScale(0.35f);
        centered = true;
        //Console.print("INFO");
    }

    public void render() {
        position.x = bomber.getPosX();
        position.y = bomber.getPosY();
        font.draw(
                batch,
                bomber.getName(),
                position.x-WIDTH/2,
                position.y-5,
                WIDTH,
                Align.center,
                false
        );
    }
    public void dispose(){
        super.dispose();
        font.dispose();
        Renderer.removeFromBatch(this,4);
    }
}

