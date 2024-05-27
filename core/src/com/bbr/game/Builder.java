package com.bbr.game;

public class Builder {
    private int posX, posY;
    private int time = 60;
    private int spanX = 3;
    private int spanY = 3;
    private int explosionDelay = 0;

    public Builder(int posX, int posY) {
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

    public void setExplosionDelay(int explosionDelay) {
        this.explosionDelay = explosionDelay;
    }

    public Bomb build() {
        return new Bomb(posX, posY, time, spanX, spanY, explosionDelay);
    }
}
