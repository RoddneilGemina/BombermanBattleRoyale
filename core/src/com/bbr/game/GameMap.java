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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bbr.game.Utils.Collider;
import jdk.tools.jmod.Main;

public class GameMap extends ApplicationAdapter {
    public static final int[][] map = new int[][]{
            {1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,1},
            {1,0,3,0,3,0,3,3,3,0,1},
            {1,0,3,0,3,0,0,0,3,0,1},
            {1,0,3,3,3,0,3,0,3,3,1},
            {1,0,0,0,0,0,0,0,0,0,1},
            {1,0,3,3,3,0,3,0,3,0,1},
            {1,0,0,0,3,0,0,0,3,0,1},
            {1,0,3,3,3,0,3,3,3,0,1},
            {1,0,0,0,0,0,3,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1}
    };
    private int mapW = 11, mapH = 11;
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

        layers.add(generateLayer());

        renderer= new OrthogonalTiledMapRenderer(tiledMap, (float)(5.0/1.0)* MainGame.SCALE/80f);
        camera = new OrthographicCamera();

        camera.setToOrtho(false, 640, 480);
        camera.zoom =0.25f;
        camera.position.x = 50;
        camera.position.y = 50;

        initMap();
    }

    @Override
    public void render () {
        if(bomber != null){
            camera.position.x = bomber.getBody().getPosition().x;
            camera.position.y = bomber.getBody().getPosition().y;
        }
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        renderer.setView(camera);
        renderer.render();


    }
    private TiledMapTileLayer generateLayer() {
        TiledMapTileLayer layer1 = new TiledMapTileLayer(mapW, mapH, 16, 16);
        for(int r=0; r<mapW; r++){
            for(int c=0; c<mapH; c++){
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                TextureRegion tr = new TextureRegion(img,map[r][c]*16,0,16,16);
                cell.setTile(new StaticTiledMapTile(tr));
                layer1.setCell(c, r, cell);
            }
        }
        return layer1;
    }
    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
    public static World initMap(){
        float SCALE = MainGame.SCALE;
        World newWorld = new World(new Vector2(0,0),true);
        for(int r=0; r<11; r++){
            for(int c=0;c<11;c++){
                if(GameMap.map[r][c]==0) continue;

                BodyDef bd = new BodyDef();
                bd.type = BodyDef.BodyType.StaticBody;
                bd.position.set((int)(c*SCALE + SCALE/2),(int)(r*SCALE + SCALE/2));

                Body body = newWorld.createBody(bd);
                PolygonShape ps = new PolygonShape();
                ps.setAsBox((int)(SCALE/2),(int)(SCALE/2));
                body.createFixture(ps, 0.0f);
            }
        }
        newWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Object collA = contact.getFixtureA().getBody().getUserData();
                Object collB = contact.getFixtureB().getBody().getUserData();
                if(collA instanceof Collider) ((Collider)collA).collide(collB);
                if(collB instanceof Collider) ((Collider)collB).collide(collA);
            }
            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
        return newWorld;
    }
}
