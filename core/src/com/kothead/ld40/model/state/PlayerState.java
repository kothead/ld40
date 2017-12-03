package com.kothead.ld40.model.state;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.component.ControlComponent;
import com.kothead.ld40.model.component.PhysicsComponent;

public enum PlayerState implements State<Entity> {

    GHOST_STAND {
        @Override
        public void update(Entity entity) {
            ControlComponent control = Mappers.control.get(entity);
            PhysicsComponent physics = Mappers.physics.get(entity);
            StateMachine fsm = Mappers.fsm.get(entity).fsm;

            if (!physics.isStanding) {
                fsm.changeState(GHOST_FLY);
            } else if (control.jump) {
                fsm.changeState(GHOST_JUMP);
            } else if (control.direction != null) {
                fsm.changeState(GHOST_WALK);
            } else if (control.divide) {
                control.divide = false;
                fsm.changeState(GHOST_DIVIDE);
            } else if (control.die) {
                control.die = false;
                fsm.changeState(GHOST_DIE);
            }
        }
    },

    GHOST_WALK {
        @Override
        public void update(Entity entity) {
            ControlComponent control = Mappers.control.get(entity);
            PhysicsComponent physics = Mappers.physics.get(entity);
            StateMachine fsm = Mappers.fsm.get(entity).fsm;

            if (!physics.isStanding) {
                fsm.changeState(GHOST_FLY);
            } else if (control.jump) {
                fsm.changeState(GHOST_JUMP);
            } else if (control.direction == null) {
                fsm.changeState(GHOST_STAND);
            } else if (control.divide) {
                control.divide = false;
                fsm.changeState(GHOST_DIVIDE);
            } else if (control.die) {
                control.die = false;
                fsm.changeState(GHOST_DIE);
            }
        }
    },

    GHOST_JUMP {
        @Override
        public void update(Entity entity) {
        }
    },

    GHOST_FLY {
        @Override
        public void update(Entity entity) {
            PhysicsComponent physics = Mappers.physics.get(entity);
            StateMachine fsm = Mappers.fsm.get(entity).fsm;

            if (physics.isStanding) {
                fsm.changeState(GHOST_STAND);
            }
        }
    },

    GHOST_DIVIDE {
        @Override
        public void update(Entity entity) {
        }
    },

    GHOST_DIE {
        @Override
        public void update(Entity entity) {
        }
    };

    @Override
    public void enter(Entity entity) {

    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void exit(Entity entity) {

    }

    @Override
    public boolean onMessage(Entity entity, Telegram telegram) {
        return false;
    }
}
