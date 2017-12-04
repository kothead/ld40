package com.kothead.ld40.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.kothead.ld40.util.EntityStateMachine;

public class FSMComponent implements Component {

    public EntityStateMachine fsm;

    public FSMComponent(EntityStateMachine fsm) {
        this.fsm = fsm;
    }
}
