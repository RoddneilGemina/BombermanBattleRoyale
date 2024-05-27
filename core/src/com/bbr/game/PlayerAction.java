package com.bbr.game;

public abstract class PlayerAction{
    int count = 1;
    public abstract void doAction(Bomber b);
    public PlayerAction(int count){this.count = count;}
}
