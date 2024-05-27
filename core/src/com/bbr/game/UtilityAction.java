package com.bbr.game;

import jdk.internal.classfile.impl.Util;

public abstract class UtilityAction extends PlayerAction{
    public void doAction() {
        doAction(null);
    }

    public abstract void doAction(Bomber b);
}

class Heal extends UtilityAction{
    int hpValue = 10;
    public void doAction(Bomber b){
        b.addHealth(hpValue);
    }
}

class Shield extends UtilityAction {
    public int rX = 1, rY=0;
    int shieldValue = 50;
    public void doAction(Bomber b){
        //add shield to player
    }
}

class Empower extends UtilityAction {
    int dmgValue = 20;
    public void doAction(Bomber b){
        //add Empowered effect to effect list
    }
}

class Mimic extends UtilityAction {
    public void doAction(Bomber b) {
        //get bomber location and place mimic on said location
        //mimic is a collectible item with custom effect when interacted with
    }
}

class BombMine extends UtilityAction{
    public void doAction(Bomber b){
        //create mine activator on player location
    }
}

class GlueTrap extends UtilityAction {
    public void doAction(Bomber b){
        //create mine activator on player location (instant slow area)
    }
}

class IceTrap extends UtilityAction {
    public void doAction(Bomber b) {
        //create mine activator on player location (instant freeze area)
    }
}



