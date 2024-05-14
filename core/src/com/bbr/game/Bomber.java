package com.bbr.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import org.w3c.dom.Text;

public class Bomber {
    private Body body;
    private Sprite sprite;
    private Texture texture;
    private SpriteBatch batch;
    private int posX;
    private int posY;

    public Bomber()
    {
        posX = 0;
        posY = 0;

        batch = new SpriteBatch();
        texture = new Texture("Bomber.png");
        sprite = new Sprite(texture);
        this.sprite = sprite;
        this.sprite.setPosition(25, 50); // random

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(posX,posY);

        body = BombermanBattleRoyaleGame.world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.5f, 1.0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;

        body.createFixture(fixtureDef);

        boxShape.dispose();
    }

    public void update()
    {
    }

    public void render()
    {
        //sprite.draw(batch);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
