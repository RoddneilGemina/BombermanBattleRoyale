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

    public void setTime(int time) {
        this.time = time;
    }

    public void setSpan(int span) {
        spanX = span;
        spanY = span;
    }

    public void setSpanX(int spanX) {
        this.spanX = spanX;
    }

    public void setSpanY(int spanY) {
        this.spanY = spanY;
    }
    public void setDamage(int damage){this.damage = damage;}
    public int getDamage(){return damage;}

    public void setExplosionDelay(int explosionDelay) {
        this.explosionDelay = explosionDelay;
    }
    public void setCenter(boolean snap){this.snap = snap;}

    public Bomb build() {
        if(snap) {
            posX = (int) (10 * Math.round((posX - MainGame.SCALE + 4) / 10) + MainGame.SCALE / 2);
            posY = (int) (10 * Math.round((posY - MainGame.SCALE + 3) / 10) + MainGame.SCALE / 2);
        }
        return new Bomb(posX, posY, time, spanX, spanY, explosionDelay,damage);
    }
}
