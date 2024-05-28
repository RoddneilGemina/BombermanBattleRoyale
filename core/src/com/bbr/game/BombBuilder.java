package com.bbr.game;

public class BombBuilder {
    private float posX, posY;
    private int time = 60;
    private int spanX = 3;
    private int spanY = 3;
    private int explosionDelay = 10;
    private int damageAdder = 0;
    private float damageMultiplier = 1;
    private int damage = 10;
    private boolean snap = true;
    private int bomberID = -1;
    public BombBuilder(){
        posX = 100;
        posY = 100;
    }

    public BombBuilder(float posX, float posY) {
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
    public BombBuilder setDamageAdder(int damageAdd){this.damageAdder=damageAdd; return this;}
    public BombBuilder setDamageMultiplier(float multiplier){this.damageMultiplier=multiplier; return this;}
    public BombBuilder setBomberID(int id){bomberID=id;return this;}
    public float getPosX(){
        return posX;
    }
    public float getPosY(){
        return posY;
    }

    public Bomb build() {
        if(snap) {
            posX = (int) (10 * Math.round((posX - MainGame.SCALE + 4) / 10) + MainGame.SCALE / 2);
            posY = (int) (10 * Math.round((posY - MainGame.SCALE + 3) / 10) + MainGame.SCALE / 2);
        }
        Bomb bomb = new Bomb(bomberID,(int)posX, (int)posY, time, spanX, spanY, explosionDelay,(int)(damage*damageMultiplier+damageAdder));
        if(!MainGame.isServer) MainGame.gameClient.addBomb(this,0);
        return bomb;
    }
    public Bomb buildNoNet(){
        if(snap) {
            posX = (int) (10 * Math.round((posX - MainGame.SCALE + 4) / 10) + MainGame.SCALE / 2);
            posY = (int) (10 * Math.round((posY - MainGame.SCALE + 3) / 10) + MainGame.SCALE / 2);
        }
        return new Bomb(bomberID,(int)posX, (int)posY, time, spanX, spanY, explosionDelay,(int)(damage*damageMultiplier+damageAdder));
    }
}
