package com.kothead.ld40.controller.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.ld40.controller.SystemPriority;
import com.kothead.ld40.data.Animations;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.Direction;
import com.kothead.ld40.model.component.AnimationComponent;
import com.kothead.ld40.model.component.FSMComponent;
import com.kothead.ld40.model.component.SpriteComponent;

public class AnimationSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    public AnimationSystem() {
        super(SystemPriority.ANIMATION_SYSTEM);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SpriteComponent.class, AnimationComponent.class, FSMComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity: entities) {
            Sprite sprite = Mappers.sprite.get(entity).sprite;
            State<Entity> state = Mappers.fsm.get(entity).fsm.getCurrentState();
            AnimationComponent animationComponent = Mappers.animation.get(entity);
            animationComponent.time += deltaTime;

            Direction direction = Mappers.direction.has(entity)
                    ? Mappers.direction.get(entity).direction
                    : Direction.RIGHT;

            Animation<TextureRegion> animation = Animations.get(state, direction);
            float time = Mappers.animation.get(entity).time;
            TextureRegion region = animation.getKeyFrame(time);
            animationComponent.ended = animation.isAnimationFinished(time);

            sprite.setRegion(region);
            sprite.setSize(region.getRegionWidth(), region.getRegionHeight());
        }
    }
}
