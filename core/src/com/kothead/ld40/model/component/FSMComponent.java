package com.kothead.ld40.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.fsm.StateMachine;

public class FSMComponent implements Component {

    public StateMachine fsm;

    public FSMComponent(StateMachine fsm) {
        this.fsm = fsm;
    }
}
