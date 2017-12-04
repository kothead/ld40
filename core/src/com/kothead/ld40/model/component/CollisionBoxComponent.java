package com.kothead.ld40.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;
import com.kothead.ld40.data.CollisionBoxes;

public class CollisionBoxComponent implements Component {

    public Polygon polygon = CollisionBoxes.PLAYER_RIGHT;
    public Polygon polygonDivide = CollisionBoxes.DIVISION_RIGHT;

}
