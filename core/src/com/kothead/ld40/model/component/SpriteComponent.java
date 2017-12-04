package com.kothead.ld40.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component {

    public Sprite sprite;

    public SpriteComponent() {
        sprite = new Sprite();
    }

    public SpriteComponent(Sprite sprite) {
        this.sprite = sprite;
    }
}
