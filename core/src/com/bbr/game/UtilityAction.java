package com.bbr.game;

public abstract class UtilityAction extends PlayerAction{
    public void doAction() {
        doAction(null);
    }

    public abstract void doAction(Bomber b);
}

class Heal extends UtilityAction{
    int addHp = 10;
    public void doAction(Bomber b){
        b.addHealth(addHp);
    }
}
