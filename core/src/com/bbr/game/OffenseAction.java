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
    public MediumCrossBomb(){
        count = 1;
        this.rX = 2;
        this.rY = 0;
    }
    public void doAction(Bomber b){
        Renderer.setToBatch(
                new BombBuilder(b.getPosX(),b.getPosY()).setSpan(5).build()
                ,3
        );
    }
}

class MediumXBomb extends OffenseAction {
    public void doAction(Bomber b) {

    }
}

class MediumBoxBomb extends OffenseAction {
    public MediumBoxBomb(){
        count = 1;
        this.rX = 2;
        this.rY = 0;
    }
    public void doAction(Bomber b){
        Renderer.setToBatch(
                new BombBuilder(b.getPosX(),b.getPosY()).build()
                ,3
        );
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