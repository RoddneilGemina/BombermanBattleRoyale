package com.bbr.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class HomeScreen implements Screen {
    private Stage stage;
    public tmp game;

    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private BitmapFont white,black;
    private TextButton btnPlay, btnExit;
    private Label heading;
    private  TextButton.TextButtonStyle txtStyle;

    public HomeScreen(tmp game){
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
//        atlas = new TextureAtlas("ui/button.pack");


        table = new Table();
        table.setBounds(0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

         txtStyle = new TextButton.TextButtonStyle();
        black = new BitmapFont();
        txtStyle.font = black;

        btnPlay = new TextButton("PLAY", txtStyle);
        btnExit = new TextButton("EXIT", txtStyle);

        table.add(btnPlay);
        table.row();
        table.add(btnExit);

        stage.addActor(table);

        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new LobbyScreen(game));

            }
        });
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dispose();
                dispose();
            }
        });
    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
        stage.dispose();
        black.dispose();

    }
}
