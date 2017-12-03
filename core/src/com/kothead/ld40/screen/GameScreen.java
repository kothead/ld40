package com.kothead.ld40.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.kothead.ld40.LD40Game;

public class GameScreen extends BaseScreen {

    private static final int UNIT_SCALE = 2;
    private static final String PATH_TILEMAP = "map/level.tmx";

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private float tileWidth, tileHeight;
    private float mapWidth, mapHeight;

    public GameScreen(LD40Game game) {
        super(game);

        map = new TmxMapLoader().load(PATH_TILEMAP);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1);

        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileWidth = tileLayer.getTileWidth() * UNIT_SCALE;
        tileHeight = tileLayer.getTileHeight() * UNIT_SCALE;
        mapWidth = tileLayer.getWidth() * tileWidth;
        mapHeight = tileLayer.getHeight() * tileHeight;
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

        mapRenderer.setView(getCamera());
        mapRenderer.render();

//        mapRenderer.getBatch().begin();
//        mapRenderer.getBatch().end();
    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();

        super.dispose();
    }
}
