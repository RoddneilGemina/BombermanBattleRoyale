package com.bbr.game.Utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class NewGame {
    private static final int NUMBER_OF_LAYERS = 5;
    private static ArrayList<SpriteBatch> batchLayers = new ArrayList<>();
    private static ArrayList<ArrayList<GameObj>> objectBatches = new ArrayList<>();
    private static OrthographicCamera camera;
    public static void init(){
        for(int i=0; i<NUMBER_OF_LAYERS; i++){
            batchLayers.add(new SpriteBatch());
            objectBatches.add(new ArrayList<>());
        }
    }
    public static void setCamera(OrthographicCamera camera) {NewGame.camera = camera;}
    public static void render(){
        for(int i=0; i<NUMBER_OF_LAYERS; i++){
            batchLayers.get(i).begin();
            batchLayers.get(i).setProjectionMatrix(camera.combined);
            for(int j=0; j<objectBatches.get(i).size(); j++){
                objectBatches.get(i).get(j).render();
            }
            batchLayers.get(i).end();
        }
    }
    public static void setToBatch(GameObj o, int layerIndex){
        addToBatch(o,layerIndex);
        o.setBatch(batchLayers.get(layerIndex));
    }
    public static void addToBatch(GameObj o, int layerIndex){
        objectBatches.get(layerIndex).add(o);
    }
    public static void removeFromBatch( GameObj o, int layerIndex){
        objectBatches.get(layerIndex).remove(o);
    }

}
