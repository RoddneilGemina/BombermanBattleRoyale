package com.bbr.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
        posX = 120;
        posY = 120;

        batch = new SpriteBatch();
        texture = new Texture("bomber.png");
        sprite = new Sprite(texture);
        this.sprite.setPosition(0, 0);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX,posY);

        body = BombermanBattleRoyaleGame.world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(35f, 35f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = -40f;

        body.createFixture(fixtureDef);

        boxShape.dispose();
    }

    public void moveBody(float x, float y){
        //body.applyLinearImpulse(new Vector2(x,y), body.getWorldCenter(), true);
//        body.applyForceToCenter(x*100,y*100,true);
        body.setLinearVelocity(x,y);
        System.out.println("Player pos " + body.getPosition());
    }
    public void update()
    {
    }

    public void render()
    {
        batch = (SpriteBatch) GameMap.renderer.getBatch();
        batch.begin();
        batch.draw(texture, body.getPosition().x - 40, body.getPosition().y - 40, 75, 75);
        batch.end();
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
