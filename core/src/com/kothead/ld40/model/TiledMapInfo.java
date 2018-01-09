package com.kothead.ld40.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class TiledMapInfo {

    private TiledMap map;

    private float tileWidth, tileHeight;
    private float mapWidth, mapHeight;

    public TiledMapInfo(TiledMap map) {
        this.map = map;

        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileWidth = tileLayer.getTileWidth();
        tileHeight = tileLayer.getTileHeight();
        mapWidth = tileLayer.getWidth() * tileWidth;
        mapHeight = tileLayer.getHeight() * tileHeight;
    }

    public float getTileWidth() {
        return tileWidth;
    }

    public float getTileHeight() {
        return tileHeight;
    }

    public float getMapWidth() {
        return mapWidth;
    }

    public float getMapHeight() {
        return mapHeight;
    }
}
