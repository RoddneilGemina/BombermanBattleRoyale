package com.bbr.Menu;

import com.badlogic.gdx.Screen;
import com.bbr.game.MainGame;

public class GameScreen implements Screen {
    public static MainGame game = null;
    public static tmp t;
    public GameScreen(tmp t){
        this.t=t;
        game = new MainGame(Data.ip,Data.server,Data.play);
        game.create();
        System.out.println(" CREATED A GAME ");
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        game.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
