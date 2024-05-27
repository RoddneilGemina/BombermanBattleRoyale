package com.bbr.game;

public abstract class SkillAction {

    public abstract void doAction(Bomber b);
}

class Dash extends SkillAction {
    public void doAction(Bomber b){
        b.getBody().setLinearDamping(0f);
        b.moveBody(b.getDirection().x*600,b.getDirection().y*600);
        b.getBody().setLinearDamping(30f);
    }
}

class Stasis extends UtilityAction {
    int shieldValue = 100;
    public void doAction(Bomber b){
        //go stasis mode
    }
}


