package com.kothead.ld40.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {

    public Vector2 velocity;

    public VelocityComponent() {
        this(0.0f, 0.0f);
    }

    public VelocityComponent(float x, float y) {
        velocity = new Vector2(x, y);
    }

    public VelocityComponent(Vector2 velocity) {
        this.velocity = new Vector2(velocity);
    }
}
