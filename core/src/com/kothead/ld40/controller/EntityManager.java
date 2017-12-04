package com.kothead.ld40.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.kothead.ld40.controller.state.PlayerState;
import com.kothead.ld40.controller.system.*;
import com.kothead.ld40.data.CollisionBoxes;
import com.kothead.ld40.data.Configuration;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.Direction;
import com.kothead.ld40.model.component.*;
import com.kothead.ld40.screen.BaseScreen;
import com.kothead.ld40.util.EntityStateMachine;

public class EntityManager implements Telegraph {

    public static final int MESSAGE_DELETE_PLAYER = 101;
    public static final int MESSAGE_ADD_PLAYER = 102;
    public static final int MESSAGE_SELECT_PLAYER = 103;

    private BaseScreen screen;
    private TiledMap map;

    public EntityManager(BaseScreen screen, TiledMap map) {
        this.screen = screen;
        this.map = map;

        engine().addSystem(new MovementSystem());
        engine().addSystem(new RenderSystem(screen, map));
        engine().addSystem(new PhysicsSystem(map));
        engine().addSystem(new AnimationSystem());
        engine().addSystem(new FSMSystem());

        InputSystem system = new InputSystem();
        engine().addSystem(system);
        screen.addInputProcessor(system);

        MessageManager.getInstance().addListeners(this,
                MESSAGE_DELETE_PLAYER,
                MESSAGE_ADD_PLAYER,
                MESSAGE_SELECT_PLAYER);
    }

    public Entity createPlayer(float x, float y, boolean isHuman) {
        Entity entity = new Entity();
        entity.add(new PositionComponent(x, y));
        entity.add(new VelocityComponent());
        entity.add(new PhysicsComponent());
        entity.add(new CollisionBoxComponent(CollisionBoxes.PLAYER));
        entity.add(new DirectionComponent());
        entity.add(new SpriteComponent());
        entity.add(new AnimationComponent());
        entity.add(new FSMComponent(new EntityStateMachine(entity, PlayerState.GHOST_ARISE)));
        entity.add(new ControlComponent());
        if (isHuman) {
            entity.add(new HumanControlComponent());
        }
        engine().addEntity(entity);
        return entity;
    }

    public void update(float deltaTime) {
        engine().update(deltaTime);
    }

    private Engine engine() {
        return screen.getGame().getEngine();
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message) {
            case MESSAGE_DELETE_PLAYER:
                Entity entity = (Entity) msg.extraInfo;
                engine().removeEntity(entity);
                entity = engine().getEntitiesFor(Family
                        .exclude(HumanControlComponent.class)
                        .one(ControlComponent.class).get())
                        .random();
                if (entity != null) {
                    entity.add(new HumanControlComponent());
                } else {
                    // TODO: message "Why?"
                }
                return true;

            case MESSAGE_ADD_PLAYER:
                entity = (Entity) msg.extraInfo;
                Vector2 position = Mappers.position.get(entity).position;
                Direction direction = Mappers.direction.get(entity).direction;
                int width = (int) Mappers.sprite.get(entity).sprite.getWidth();
                Entity newPlayer = createPlayer(position.x + width / 2f, position.y, false);
                Mappers.direction.get(newPlayer).direction = direction;
                return true;

            case MESSAGE_SELECT_PLAYER:
                direction = (Direction) msg.extraInfo;
                return true;
        }

        return false;
    }

    public Entity findPlayer(Entity human, Direction direction) {
        return null;
    }
}
