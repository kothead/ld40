package com.kothead.ld40.data;

import com.badlogic.gdx.math.Polygon;

public class CollisionBoxes {

    public static final Polygon PLAYER_RIGHT = new Polygon(new float[] {
            14.0f, 3.0f, 54.0f, 3.0f, 58.0f, 49.0f, 48.0f, 62.0f, 15.0f, 62.0f, 10.0f, 31.0f
    });

    public static final Polygon PLAYER_LEFT = new Polygon(new float[] {
            54.0f, 31.0f, 49.0f, 62.0f, 16.0f, 62.0f, 6.0f, 49.0f, 10.0f, 3.0f, 50.0f, 3.0f
    });

    public static final Polygon DIVISION_RIGHT = new Polygon(new float[] {
            14.0f, 8.0f, 54.0f + 55.0f, 8.0f, 58.0f + 55.0f, 49.0f, 48.0f + 55.0f, 62.0f, 15.0f, 62.0f, 10.0f, 31.0f
    });

    public static final Polygon DIVISION_LEFT = new Polygon(new float[] {
            54.0f, 31.0f, 49.0f, 62.0f, 16.0f - 55.0f, 62.0f, 6.0f - 55.0f, 49.0f, 10.0f - 55.0f, 8.0f, 50.0f, 8.0f
    });

    public static final Polygon HAND = new Polygon(new float[] {
            1.0f, 137.0f, 169.0f, 101.0f, 257.0f, 54.0f, 237.0f, 28.0f, 153.0f, 21.0f
    });
}
