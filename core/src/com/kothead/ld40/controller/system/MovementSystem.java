package com.kothead.ld40.controller.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.kothead.ld40.controller.SystemPriority;
import com.kothead.ld40.data.CollisionBoxes;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.Direction;
import com.kothead.ld40.model.component.CollisionBoxComponent;
import com.kothead.ld40.model.component.DirectionComponent;
import com.kothead.ld40.model.component.PositionComponent;
import com.kothead.ld40.model.component.VelocityComponent;

public class MovementSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    public MovementSystem() {
        super(SystemPriority.MOVEMENT_SYSTEM);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity: entities) {
            Vector2 position = Mappers.position.get(entity).position;
            Vector2 velocity = Mappers.velocity.get(entity).velocity;

            if (Mappers.direction.has(entity)) {
                DirectionComponent directionComponent = Mappers.direction.get(entity);
                directionComponent.direction = velocity.x > 0f
                        ? Direction.RIGHT
                        : (velocity.x < 0
                        ? Direction.LEFT
                        : directionComponent.direction);

                CollisionBoxComponent component = Mappers.collisionBox.get(entity);
                if (directionComponent.direction == Direction.RIGHT) {
                    component.polygon = CollisionBoxes.PLAYER_RIGHT;
                    component.polygonDivide = CollisionBoxes.DIVISION_RIGHT;
                } else {
                    component.polygon = CollisionBoxes.PLAYER_LEFT;
                    component.polygonDivide = CollisionBoxes.DIVISION_LEFT;
                }
            }

            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;

            if (position.y < 58) {
                velocity.y = 0;
                position.y = 58;
            }

            if (position.x < 58) {
                velocity.x = 0;
                position.x= 58;
            }

            velocity.x = 0;
        }
    }
}
