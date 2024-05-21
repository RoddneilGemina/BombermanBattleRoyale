package com.bbr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bbr.game.Utils.Controllable;
import com.bbr.net.GameClient;

public class Controller {

    private static final int[] controls = {
            Input.Keys.A,
            Input.Keys.D,
            Input.Keys.W,
            Input.Keys.S,
            Input.Keys.H,
            Input.Keys.J,
            Input.Keys.K
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

        if(controled instanceof Bomber) MainGame.gameClient.updatePlayerBomber((Bomber) controled);
    }
}
