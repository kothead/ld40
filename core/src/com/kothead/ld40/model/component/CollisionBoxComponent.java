package com.kothead.ld40.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;

public class CollisionBoxComponent implements Component {

    public Polygon polygon;

    public CollisionBoxComponent(Polygon polygon) {
        this.polygon = polygon;
    }
}
