package com.kothead.ld40.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.kothead.ld40.LD40Game;
import com.kothead.ld40.controller.EntityManager;

public class GameScreen extends BaseScreen {

    private static final String PATH_TILEMAP = "map/level.tmx";

    private EntityManager manager;

    private TiledMap map;

    public GameScreen(LD40Game game) {
        super(game);

        map = new TmxMapLoader().load(PATH_TILEMAP);

        manager = new EntityManager(this, map);
        manager.createPlayer();
    }

    @Override
    protected void layoutViewsLandscape(int width, int height) {

    }

    @Override
    protected void layoutViewsPortrait(int width, int height) {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0.0235f, 0.0157f, 0.0196f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        manager.update(delta);
//        mapRenderer.getBatch().begin();
//        mapRenderer.getBatch().end();
    }

    @Override
    public void dispose() {
        map.dispose();

        super.dispose();
    }
}
