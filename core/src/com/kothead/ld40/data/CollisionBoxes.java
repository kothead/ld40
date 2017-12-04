package com.kothead.ld40.data;

import com.badlogic.gdx.math.Polygon;

public class CollisionBoxes {

    public static final Polygon PLAYER_RIGHT = new Polygon(new float[] {
            14.0f, 3.0f, 54.0f, 3.0f, 58.0f, 49.0f, 48.0f, 62.0f, 22.0f, 62.0f, 10.0f, 31.0f
    });

    public static final Polygon PLAYER_LEFT = new Polygon(new float[] {
            54.0f, 31.0f, 42.0f, 62.0f, 16.0f, 62.0f, 6.0f, 49.0f, 10.0f, 2.0f, 50.0f, 3.0f
    });

    public static final Polygon DIVISION_RIGHT = new Polygon(new float[] {
            14.0f, 8.0f, 54.0f + 55.0f, 8.0f, 58.0f + 55.0f, 49.0f, 48.0f + 55.0f, 62.0f, 22.0f, 62.0f, 10.0f, 31.0f
    });

    public static final Polygon DIVISION_LEFT = new Polygon(new float[] {
            54.0f, 31.0f, 42.0f, 62.0f, 16.0f - 55.0f, 62.0f, 6.0f - 55.0f, 49.0f, 10.0f - 55.0f, 8.0f, 50.0f, 8.0f
    });
}
