package com.bbr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Controller {

    private static int[] controls = {Input.Keys.A,Input.Keys.D,Input.Keys.W,Input.Keys.S,Input.Keys.H,Input.Keys.J,Input.Keys.K};
    Bomber bomber;
    OrthographicCamera cam;


    public Controller(Bomber bomber){
        this.bomber = bomber;
        cam = GameMap.camera;
    }

    public void render(){
        if(Gdx.input.isKeyPressed(controls[0])) bomber.moveBody(-1,0);
        if(Gdx.input.isKeyPressed(controls[1])) bomber.moveBody(1,0);
        if(Gdx.input.isKeyPressed(controls[2])) bomber.moveBody(0,1);
        if(Gdx.input.isKeyPressed(controls[3])) bomber.moveBody(0,-1);
        if(Gdx.input.isKeyJustPressed(controls[4])) bomber.dropBomb();
    }
}
