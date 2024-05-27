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
                new BombBuilder(b.getPosX(),b.getPosY()).build()
                ,3
        );
    }
}

class SmallCrossBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}

class SmallXBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}

class SmallBoxBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}

class MediumCrossBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}

class MediumXBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}

class MediumBoxBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}

class LargeCrossBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}

class LargeXBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}

class LargeBoxBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}