package com.bbr.menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bbr.net.SQLInterface;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;

public class SetUpUser implements Screen {

    private Stage stage;
    public tmp game; // Please replace tmp with your Game class
    private VisTextField usernameField, usernameField2;
    public SetUpUser(tmp game){
        this.game = game;
    }

    @Override
    public void show() {
        VisUI.load();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Create a text field for username input
        usernameField = new VisTextField();
        usernameField.setMessageText("Enter username");
        usernameField.setMaxLength(30);


        usernameField2 = new VisTextField();
        usernameField2.setMessageText("Enter IP Address");
        usernameField2.setText("127.0.0.1");
        usernameField2.setMaxLength(30);

        // Create a button for navigation
        VisTextButton goToHomeButton = new VisTextButton("Continue");

        // Add text field and button to table
        table.add(usernameField).width(300).pad(10);
        table.row();
        table.add(usernameField2).width(300).pad(10);
        table.row();
        table.add(goToHomeButton).width(200).pad(10);

        // Add the table to the stage
        stage.addActor(table);

        goToHomeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();

                if (username == null || username.trim().isEmpty()) {
                    System.out.println("Error: No Username Entered.");
                    return;
                }
                if(username.length() > 8) {
                    System.out.println("Maximum of 8 characters ONLY!.");
                    return;
                }
                tmp.UserName = usernameField.getText();
                System.out.println("Username: " + usernameField.getText()); // print the username for testing
                Data.name = usernameField.getText();
                if(usernameField2.getText() != null) Data.ip = usernameField2.getText();
                else Data.ip = "127.0.0.1";
                SQLInterface.loginSignUp(Data.name);
                game.setScreen(new HomeScreen(game)); // Navigate to your HomeScreen
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        VisUI.dispose();
    }

    // Implement other required methods here...
}