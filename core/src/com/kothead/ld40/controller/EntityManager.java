package com.kothead.ld40.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.kothead.ld40.controller.state.PlayerState;
import com.kothead.ld40.controller.system.*;
import com.kothead.ld40.data.CollisionBoxes;
import com.kothead.ld40.model.component.*;
import com.kothead.ld40.screen.BaseScreen;
import com.kothead.ld40.util.EntityStateMachine;

public class EntityManager {

    private BaseScreen screen;

    public EntityManager(BaseScreen screen, TiledMap map) {
        this.screen = screen;

        engine().addSystem(new MovementSystem());
        engine().addSystem(new RenderSystem(screen, map));
        engine().addSystem(new PhysicsSystem(map));
        engine().addSystem(new AnimationSystem());
        engine().addSystem(new FSMSystem());

        InputSystem system = new InputSystem();
        engine().addSystem(system);
        screen.addInputProcessor(system);
    }

    public void createPlayer() {
        Entity entity = new Entity();
        entity.add(new PositionComponent(300.0f, 300.0f));
        entity.add(new VelocityComponent());
        entity.add(new PhysicsComponent());
        entity.add(new CollisionBoxComponent(CollisionBoxes.PLAYER));
        entity.add(new DirectionComponent());
        entity.add(new SpriteComponent());
        entity.add(new AnimationComponent());
        entity.add(new FSMComponent(new EntityStateMachine(entity, PlayerState.GHOST_STAND)));
        entity.add(new ControlComponent());
        entity.add(new HumanControlComponent());
        engine().addEntity(entity);
    }

    public void update(float deltaTime) {
        engine().update(deltaTime);
    }

    private Engine engine() {
        return screen.getGame().getEngine();
    }
}
