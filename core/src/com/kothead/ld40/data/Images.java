package com.kothead.ld40.data;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Images {
    
    private static final String DATA_DIR = "image/";
    private static final String DATA_FILE = "pack.atlas";
    
    private static TextureAtlas atlas;

    public static void load() {
        atlas = new TextureAtlas(DATA_DIR + DATA_FILE);
    }

    public static TextureRegion getTexture(String name) {
        return atlas.findRegion(name);
    }

    public static TextureRegion getFrame(String name, int index) {
        return atlas.findRegion(name, index);
    }

    public static TextureRegion[] getFrames(String name, int offset, int count, boolean flipx, boolean flipy) {
        TextureRegion[] regions = new TextureRegion[count];
        for (int i = offset, j = 0; i < count + offset; i++, j++) {
            regions[j] = getFrame(name, i);
            if (flipx || flipy) {
                regions[j] = new TextureRegion(regions[j]);
                regions[j].flip(flipx, flipy);
            }
        }
        return regions;
    }

    public static TextureRegion[] getFrames(String name, int offset, int count) {
        return getFrames(name, offset, count, false, false);
    }

    public static TextureAtlas getAtlas() {
        return atlas;
    }
}
