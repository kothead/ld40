package com.kothead.ld40.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class EnemyComponent implements Component {

    public Vector2 target = new Vector2(1280, 1280);
    public boolean attack = false;
}
