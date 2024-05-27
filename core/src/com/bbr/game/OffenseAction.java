package com.bbr.game;

import com.bbr.game.Utils.Renderer;

public abstract class OffenseAction extends PlayerAction {
    public abstract void doAction(Bomber b);
}
class SmallBomb extends OffenseAction{
    public SmallBomb(){
        count = 999;
        this.rX = 1;
        this.rY = 0;
    }
    public void doAction(Bomber b){
        Renderer.setToBatch(
                new Bomb(
                        (int)(10*Math.round((b.getPosX()-MainGame.SCALE+4)/10)+MainGame.SCALE/2),
                        (int)(10*Math.round((b.getPosY()-MainGame.SCALE+3)/10)+MainGame.SCALE/2)
                ),3
        );
    }
}


