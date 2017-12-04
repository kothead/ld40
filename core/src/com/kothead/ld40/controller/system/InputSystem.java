package com.kothead.ld40.controller.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.kothead.ld40.controller.SystemPriority;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.Direction;
import com.kothead.ld40.model.component.*;

public class InputSystem extends EntitySystem implements InputProcessor {

    private static final float VELOCITY_JUMP = 300.0f;
    private static final float VELOCITY_WALK = 100.0f;

    private ImmutableArray<Entity> entities;

    private boolean left = false;
    private boolean right = false;
    private boolean jump = false;
    private boolean divideRight = false;
    private boolean divideLeft = false;
    private boolean die = false;

    public InputSystem() {
        super(SystemPriority.INPUT_SYSTEM);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(
                HumanControlComponent.class,
                ControlComponent.class,
                VelocityComponent.class,
                FSMComponent.class,
                PhysicsComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity: entities) {
            ControlComponent control = Mappers.control.get(entity);
            if (control.divide || control.die) continue;

            Vector2 velocity = Mappers.velocity.get(entity).velocity;

            if (right && !left) {
                velocity.x = VELOCITY_WALK;
                control.direction = Direction.RIGHT;
            } else if (left && !right) {
                velocity.x = -VELOCITY_WALK;
                control.direction = Direction.LEFT;
            } else {
                velocity.x = 0.0f;
                control.direction = null;
            }

            if (die && !divideLeft && !divideRight) {
                velocity.x = 0.0f;
                velocity.y = 0.0f;
                control.die = true;
                die = false;
                return;
            }

            if (Mappers.physics.get(entity).isStanding) {
                if (jump) {
                    control.jump = true;
                    velocity.y = Direction.DOWN.getVector().y * VELOCITY_JUMP;
                    jump = false;
                }

                if ((divideLeft || divideRight) && !die) {
                    velocity.x = 0.0f;
                    velocity.y = 0.0f;
                    if (divideRight) {
                        Mappers.direction.get(entity).direction = Direction.RIGHT;
                    } else {
                        Mappers.direction.get(entity).direction = Direction.LEFT;
                    }
                    control.divide = true;
                    divideLeft = false;
                    divideRight = false;
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                left = true;
                return true;

            case Input.Keys.D:
                right = true;
                return true;

            case Input.Keys.W:
                jump = true;
                return true;

            case Input.Keys.LEFT:
                divideLeft = true;
                return true;

            case Input.Keys.RIGHT:
                divideRight = true;
                return true;

            case Input.Keys.SPACE:
                die = true;
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                left = false;
                return true;

            case Input.Keys.D:
                right = false;
                return true;

            case Input.Keys.W:
                jump = false;
                return true;

            case Input.Keys.RIGHT:
                divideRight = false;
                return true;

            case Input.Keys.LEFT:
                divideLeft = false;
                return true;

            case Input.Keys.SPACE:
                die = false;
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
