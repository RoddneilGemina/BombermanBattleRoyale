package com.bbr.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.bbr.game.Utils.BodyBuilder;
import com.bbr.game.Utils.Collider;
import com.bbr.game.Utils.GameObj;
import com.bbr.game.Utils.Renderer;

import java.util.ArrayList;

public class Explosion extends GameObj implements Collider {
    private Body body;
    private static final Texture texture = new Texture("boom.png");
    private Sprite sprite;
    private int posX, posY, time;
    public ArrayList<Object> container;
    ;

    public Explosion(int posX, int posY, int time) {
        this.posX = posX;
        this.posY = posY;
        this.time = time;

        sprite = new Sprite(texture);
        sprite.setRegion(0, 0, 16, 16);


        if (batch == null)
            batch = new SpriteBatch();
    }

    public void collide(Object o) {
        while (!MainGame.world.isLocked()) // only execute when world is not updating physics
            if (o instanceof Bomber)
                dispose();
    }

    int frames = 0;

    public void render() {
        if (time-- > 0) return;

        if (frames++ == 10) {
            sprite.setRegion(16, 0, 16, 16);
            body = new BodyBuilder(posX, posY)
                    .userData(this)
                    .type(BodyDef.BodyType.StaticBody)
                    .categoryBits((short) 0b1010)
                    .maskBits((short) 0b1100)
                    .sensor(true)
                    .build();
        }
        if (frames >= 30) dispose();
        batch.setProjectionMatrix(GameMap.camera.combined);
        if (sprite != null) batch.draw(sprite, (posX - MainGame.SCALE / 2), (posY - MainGame.SCALE / 2),
                80 * MainGame.SCALE / 80f, 80 * MainGame.SCALE / 80f);
    }

    public void dispose() {
        MainGame.world.destroyBody(body);
        Renderer.removeFromBatch(this, 3);
        sprite = null;
        body = null;
    }

}
