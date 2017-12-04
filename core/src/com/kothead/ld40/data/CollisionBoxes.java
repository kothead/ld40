package com.kothead.ld40.data;

import com.badlogic.gdx.math.Polygon;

public class CollisionBoxes {

    public static final Polygon PLAYER = new Polygon(new float[] {
            0.0f, 0.0f, 64.0f, 0.0f, 64.0f, 64.0f, 0.0f, 64.0f
    });

    public static final Polygon DIVISION = new Polygon(new float[] {
            0.0f, 0.0f, 128.0f, 0.0f, 128.0f, 128.0f, 0.0f, 128.0f
    });
}
