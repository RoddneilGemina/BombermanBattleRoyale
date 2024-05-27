package com.bbr.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.bbr.game.Utils.Renderer;
import com.bbr.game.Utils.ScreenObj;

// This class is for health display above playersprite (temporary)
public class HealthDisplay extends ScreenObj {
    Bomber bomber;
    Integer hp = 100;
    private static final float WIDTH = 50;
    BitmapFont font;
    public HealthDisplay(Bomber bomber){
        super(320 - 3, 300,16,16);
        font = new BitmapFont();
        this.bomber = bomber;
        Renderer.setToScreenBatch(this);
        font.getData().setScale(2f);
        centered = true;
    }

    public void render() {
        hp = bomber.getHealth();
        font.draw(
                batch,
                hp.toString(),
                position.x-WIDTH/2,
                position.y,
                WIDTH,
                Align.center,
                false
        );
    }
    public void dispose(){
        super.dispose();
        font.dispose();
        Renderer.removeFromBatches(this);
    }
}
