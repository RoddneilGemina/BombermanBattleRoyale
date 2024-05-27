package com.bbr.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class StatusEffect extends Thread{
    protected Bomber bomber;
    private long duration;
    public StatusEffect(Bomber b, long duration){bomber=b;this.duration=duration;}
    public void run(){
        effectStart();
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        effectEnd();
        dispose();
    }
    public abstract void effectStart();
    public abstract void effectDuring();
    public abstract void effectEnd();
    private void dispose(){
        synchronized (bomber.getStatusEffects()) {
            bomber.getStatusEffects().remove(this);
        }
    }
}
class Invincible extends StatusEffect{
    public Invincible(Bomber b) {super(b, 1000);}

    @Override
    public void effectStart() {
        Console.print("I WILL BE INVINCIBLE");
    }

    @Override
    public void effectDuring() {
        Console.print("I AM INVINCIBLE");
    }

    @Override
    public void effectEnd() {
        Console.print("I HAVE BEEN INVINCIBLE");
    }
}
class Dashing extends StatusEffect {

    public Dashing(Bomber b) {
        super(b, 150);
    }

    @Override
    public void effectStart() {
        bomber.getSprite().setColor(0.8f,0.8f,1,1);
    }

    @Override
    public void effectDuring() {
        Sprite spr = bomber.getSprite();
        bomber.moveBody(bomber.getDirection().x * 600, bomber.getDirection().y * 600);
        Vector2 dir = bomber.getDirection();
        for(int i=5; i!=0; --i){
            spr.translateX(dir.x*-2);
            spr.translateY(dir.y*-2);
            spr.setAlpha(i/15.0f);
            spr.draw(bomber.getBatch());
        }
        spr.setAlpha(1);
    }

    @Override
    public void effectEnd() {
        bomber.getSprite().setColor(1,1,1,1);
    }
}
class Hit extends StatusEffect{

    public Hit(Bomber b) {
        super(b, 1000);
    }

    @Override
    public void effectStart() {

    }
    private float opacity = 1f;
    private float flashFactor = 0;
    @Override
    public void effectDuring() {
        opacity = (float)(0.5f+Math.sin(flashFactor));
        flashFactor+=0.1f;
        bomber.getSprite().setAlpha(opacity);
    }

    @Override
    public void effectEnd() {
        bomber.getSprite().setAlpha(1f);
    }
}
