package com.bbr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Controller {

    private static int[] controls = {Input.Keys.A,Input.Keys.D,Input.Keys.W,Input.Keys.S,Input.Keys.H,Input.Keys.J,Input.Keys.K};
    Bomber bomber;

    public Controller(Bomber bomber){
        this.bomber = bomber;
    }

    public void render(){
        if(Gdx.input.isKeyPressed(controls[0]));
        if(Gdx.input.isKeyPressed(controls[1]));
        if(Gdx.input.isKeyPressed(controls[2]));
        if(Gdx.input.isKeyPressed(controls[3]));
        if(Gdx.input.isKeyPressed(controls[4]));
    }
}
