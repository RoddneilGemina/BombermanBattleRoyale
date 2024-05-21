package com.bbr.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.bbr.game.Utils.NewGame;
import com.bbr.game.Utils.WorldObj;

import static com.bbr.game.Bomber.batch;
import static com.bbr.game.GameMap.camera;

// This class is for health display above playersprite (temporary)
public class HealthDisplay extends WorldObj {
    Bomber bomber;
    Integer hp = 100;
    BitmapFont font = new BitmapFont();
    public HealthDisplay(Bomber bomber){
        super(bomber.getPosX(), bomber.getPosY(), 1f,1f);
        this.bomber = bomber;
        NewGame.setToBatch(this,3);
        font.getData().setScale(0.2f);
    }

    public void render() {
        hp = bomber.getHealth();
        font.draw(batch, hp.toString(), bomber.getPosX() - 3, bomber.getPosY() + 7);
    }
}
