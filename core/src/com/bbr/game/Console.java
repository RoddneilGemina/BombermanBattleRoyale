package com.bbr.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.bbr.game.Utils.Renderer;
import com.bbr.game.Utils.ScreenObj;

import java.util.ArrayList;

public class Console extends ScreenObj{
    private static ArrayList<String> lines;
    private static ArrayList<Float> opacities;
    private static final float LINE_DIST = 15;
    private static BitmapFont font;
    private static Console instance;
    private Console(){
        lines = new ArrayList<>();
    }
    public static void init(){
        if(instance != null) return;
        instance = new Console();
        lines = new ArrayList<>();
        opacities = new ArrayList<>();
        font = new BitmapFont();
        font.getData().setScale(1.2f);
        Renderer.setToScreenBatch(instance);
    }
    @Override
    public void render(){
        int ctr = 1;
        float new_opa;
        for(int i= lines.size()-1; i>=0; --i){
            font.setColor(1,1,1, opacities.get(i));
            font.draw(
                    batch,
                    lines.get(i),
                    5,
                    5+ctr*LINE_DIST,
                    480,
                    Align.bottomLeft,
                    false
            );
            new_opa = opacities.get(i)- 0.005f;
            opacities.set(i,new_opa);
            ctr++;
        }
        while(!opacities.isEmpty() && (lines.size() > 32 || opacities.get(0) <= 0)){
            lines.remove(0);
            opacities.remove(0);
        }
    }
    public static void print(String text){
        lines.add(text);
        opacities.add(2.0f);
    }

}
