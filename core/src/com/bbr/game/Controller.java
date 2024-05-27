package com.bbr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.bbr.game.Utils.Controllable;

public class Controller {

    private static final int[] controls = {
            Input.Keys.A,// LEFT
            Input.Keys.D,// RIGHT
            Input.Keys.W,// UP
            Input.Keys.S,// DOWN
            Input.Keys.H,// ACTION 1
            Input.Keys.J,// ACTION 2
            Input.Keys.K,// ACTION 3
            Input.Keys.L // ACTION 4
    };
    Controllable controled;


    public Controller(Controllable controllable){
        controled = controllable;
        if(controllable instanceof Bomber) GameMap.setBomber((Bomber) controllable);
    }

    public void render(){
        if(Gdx.input.isKeyPressed(controls[0])) controled.actionLeft();
        if(Gdx.input.isKeyPressed(controls[1])) controled.actionRight();
        if(Gdx.input.isKeyPressed(controls[2])) controled.actionUp();
        if(Gdx.input.isKeyPressed(controls[3])) controled.actionDown();
        if(Gdx.input.isKeyJustPressed(controls[4])) controled.action1();
        if(Gdx.input.isKeyJustPressed(controls[5])) controled.action2();
        if(Gdx.input.isKeyJustPressed(controls[6])) controled.action3();
        if(Gdx.input.isKeyJustPressed(controls[7])) controled.action4();

        //if(controled instanceof Bomber) MainGame.gameClient.updatePlayerBomber((Bomber) controled);
    }
}
