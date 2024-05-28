package com.bbr.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LobbyScreen implements Screen {
    private Stage stage;
    public tmp game;
    private TextButton.TextButtonStyle txtStyle;
    private BitmapFont black;
    private TextButton joinLobbyButton;
    private TextButton createLobbyButton;
    private TextButton backButton;
    public LobbyScreen(tmp game){
        this.game = game;
    }

    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        txtStyle = new TextButton.TextButtonStyle();
        black = new BitmapFont();
        txtStyle.font = black;



        backButton = new TextButton("Back", txtStyle);

        // Define your server names
        String[] serverNames = new String[]{"Host Game", "Join Game", "Host and Play"};


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popScreen();
            }
        });

        // Listens to the selection events
        for(final String serverName : serverNames) {
            TextButton serverButton = new TextButton(serverName, txtStyle);
            if(serverName.matches("Host Game"))
                serverButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Data.play = false;
                        Data.server = true;
                        Data.ip = "127.0.0.1";
                        if(GameScreen.game==null) game.pushScreen(new GameScreen(game));
                    }
                });
            else if(serverName.matches("Join Game"))
                serverButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Data.play = true;
                        Data.server = false;
                        if(GameScreen.game==null) game.pushScreen(new GameScreen(game));
                        else System.out.println("GAME ALREADY IN");
                    }
                });
            else if(serverName.matches("Host and Play"))
                serverButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Data.play = true;
                        Data.server = true;
                        Data.ip = "127.0.0.1";
                        if(GameScreen.game==null) game.pushScreen(new GameScreen(game));
                    }
                });
            table.add(serverButton).width(300).pad(10);
            table.row();
        }
        table.row();
        table.add(backButton);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        black.dispose();
    }
}
