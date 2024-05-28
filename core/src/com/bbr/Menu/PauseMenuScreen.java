package com.bbr.Menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PauseMenuScreen implements Screen {

    private Stage stage;
    private VisTextButton myButton;
    private VisTextButton Resume;
    private VisTextButton Quit;
    private VisTextButton Settings;
    private VisTable table;
    @Override
    public void show() {
        VisUI.load();
        Texture backgroundTexture = new Texture(Gdx.files.internal("pauseScreen.png"));
        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(backgroundTexture));

        stage = new Stage(new ScreenViewport());
        table = new VisTable();

        table.setBackground(background);

        Resume = new VisTextButton("Resume");
        Quit = new VisTextButton("Quit");
        Settings = new VisTextButton("Settings");

        table.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);


        myButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Button Pressed!");
            }
        });
        Resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
            }
        });
        Quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Quit Button Pressed!");
            }
        });
        Settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Setting Button Pressed!");
            }
        });

        table.add(Resume).width(400).height(100).pad(30, 0, 20, 0);
        table.row();
        table.add(Settings).width(400).height(100).pad(0, 0, 20, 0);
        table.row();
        table.add(Quit).width(400).height(100);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        // Adjust your layout here according to width and height
    }
    @Override
    public void pause() {
        // What your game should do when it's paused (e.g., when a call is received on a mobile device)
    }

    @Override
    public void resume() {
        // Logic to resume the game from a paused state
    }

    @Override
    public void hide() {
        // What your game should do when it's paused and not the active screen
    }
    @Override
    public void dispose() {
        VisUI.dispose();
        stage.dispose();
    }

    // Rest of your methods...
}