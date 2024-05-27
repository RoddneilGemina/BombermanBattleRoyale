package com.bbr.game;

public class BombBuilder {
    private int posX, posY;
    private int time = 60;
    private int spanX = 3;
    private int spanY = 3;
    private int explosionDelay = 10;
    private int damage = 10;
    private boolean snap = true;

    public BombBuilder(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public BombBuilder setTime(int time) {
        this.time = time;
        return this;
    }

    public BombBuilder setSpan(int span) {
        spanX = span;
        spanY = span;
        return this;
    }

    public BombBuilder setSpanX(int spanX) {
        this.spanX = spanX;
        return this;
    }

    public BombBuilder setSpanY(int spanY) {
        this.spanY = spanY;
        return this;
    }
    public BombBuilder setDamage(int damage){
        this.damage = damage;
        return this;
    }
    public int getDamage(){return damage;}

    public BombBuilder setExplosionDelay(int explosionDelay) {
        this.explosionDelay = explosionDelay;
        return this;

    }
    public BombBuilder setCenter(boolean snap){this.snap = snap;
        return this;
    }

    public Bomb build() {
        if(snap) {
            posX = (int) (10 * Math.round((posX - MainGame.SCALE + 4) / 10) + MainGame.SCALE / 2);
            posY = (int) (10 * Math.round((posY - MainGame.SCALE + 3) / 10) + MainGame.SCALE / 2);
        }
        return new Bomb(posX, posY, time, spanX, spanY, explosionDelay,damage);
    }
}
