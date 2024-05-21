package com.bbr.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.bbr.game.Utils.NewGame;
import com.bbr.game.Utils.ScreenObj;
import com.bbr.game.Utils.WorldObj;

import static com.bbr.game.Bomber.batch;
import static com.bbr.game.GameMap.camera;

// This class is for health display above playersprite (temporary)
public class HealthDisplay extends ScreenObj {
    Bomber bomber;
    Integer hp = 100;
    private static final float WIDTH = 50;
    BitmapFont font = new BitmapFont();
    public HealthDisplay(Bomber bomber){
        super(320 - 3, 300,16,16);
        this.bomber = bomber;
        NewGame.setToScreenBatch(this);
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
}
