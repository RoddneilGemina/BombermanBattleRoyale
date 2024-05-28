package com.bbr.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.Stack;

public class tmp extends Game {

    private Stack<Screen> screens;
    public static String UserName;


    @Override
    public void create() {

        screens = new Stack<>();
        pushScreen(new SplashScreen(this));
//        setScreen(new PauseMenuScreen()); // Here, possibly add an instance of menuscreen to MyGame
    }

    public void pushScreen(Screen screen) {
        screens.push(screen);
        setScreen(screen);
    }

    // Remove the current screen from the stack and set the previous screen as the current screen
    public void popScreen() {
        Screen currentScreen = screens.pop();
        currentScreen.dispose();
        setScreen(screens.peek());
    }

    @Override
    public void render() {
        super.render(); // Keep this it calls the `render` method of the current screen.
    }


    @Override
    public void dispose() {
        super.dispose();
    }
}