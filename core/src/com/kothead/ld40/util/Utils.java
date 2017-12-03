package com.kothead.ld40.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Utils {

    public static boolean isLandscape() {
        return Gdx.input.getNativeOrientation() == Input.Orientation.Landscape
                && (Gdx.input.getRotation() == 0 || Gdx.input.getRotation() == 180)
                || Gdx.input.getNativeOrientation() == Input.Orientation.Portrait
                && (Gdx.input.getRotation() == 90 || Gdx.input.getRotation() == 270);
    }

    public static <T> T choose(T... objects) {
        return objects[(int) (Math.random() * objects.length)];
    }
}
