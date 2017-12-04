package com.kothead.ld40.util;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;

public class EntityStateMachine extends DefaultStateMachine<Entity, State<Entity>> {

    public EntityStateMachine(Entity owner, State<Entity> initialState) {
        super(owner, initialState);
    }

    public EntityStateMachine(Entity owner, State<Entity> initialState, State<Entity> globalState) {
        super(owner, initialState, globalState);
    }
}
