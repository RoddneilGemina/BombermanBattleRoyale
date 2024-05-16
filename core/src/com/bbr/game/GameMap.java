package com.bbr.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMap extends ApplicationAdapter {
    public static final int[][] map = new int[][]{
            {1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,1},
            {1,0,3,0,3,0,3,0,3,0,1},
            {1,0,0,0,0,0,0,0,0,0,1},
            {1,0,3,0,3,0,3,0,3,0,1},
            {1,0,0,0,0,0,0,0,0,0,1},
            {1,0,3,0,3,0,3,0,3,0,1},
            {1,0,0,0,0,0,0,0,0,0,1},
            {1,0,3,0,3,0,3,0,3,0,1},
            {1,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1}
    };
    SpriteBatch batch;
    Texture img;
    TiledMap tiledMap;
    private static Bomber bomber;
    public static OrthographicCamera camera;
    public static OrthogonalTiledMapRenderer renderer;

    public static void setBomber(Bomber bomber) {
        GameMap.bomber = bomber;
        camera.zoom = 0.125f;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("tiles.png");
        tiledMap = new TiledMap();
        MapLayers layers = tiledMap.getLayers();
        int mapW = 11, mapH = 11;
        TiledMapTileLayer layer1 = new TiledMapTileLayer(mapW, mapH, 16, 16);
        for(int r=0; r<mapW; r++){
            for(int c=0; c<mapH; c++){
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                TextureRegion tr = new TextureRegion(img,map[r][c]*16,0,16,16);
                cell.setTile(new StaticTiledMapTile(tr));
                layer1.setCell(c, r, cell);
            }
        }
        layers.add(layer1);
        renderer= new OrthogonalTiledMapRenderer(tiledMap, (float)(5.0/1.0)* MainGame.SCALE/80f);
        camera = new OrthographicCamera();

        camera.setToOrtho(false, 640, 480);
        camera.zoom =0.25f;
        camera.position.x = 50;
        camera.position.y = 50;
    }

    @Override
    public void render () {
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        if(bomber != null){
            camera.position.x = bomber.getBody().getPosition().x;
            camera.position.y = bomber.getBody().getPosition().y;
        }

    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }

}
