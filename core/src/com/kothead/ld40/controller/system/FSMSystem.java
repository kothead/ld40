package com.kothead.ld40.controller.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.kothead.ld40.controller.SystemPriority;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.component.FSMComponent;

public class FSMSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    public FSMSystem() {
        super(SystemPriority.FSM_SYSTEM);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(FSMComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity: entities) {
            StateMachine fsm = Mappers.fsm.get(entity).fsm;
            fsm.update();
        }
    }
}
