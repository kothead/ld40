package com.kothead.ld40.controller.state;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kothead.ld40.controller.EntityManager;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.data.Sounds;
import com.kothead.ld40.model.Direction;
import com.kothead.ld40.model.component.AnimationComponent;
import com.kothead.ld40.model.component.ControlComponent;
import com.kothead.ld40.model.component.PhysicsComponent;
import com.kothead.ld40.model.component.PositionComponent;

public enum PlayerState implements State<Entity> {

    GHOST_STAND {
        @Override
        public void update(Entity entity) {
            super.update(entity);

            ControlComponent control = Mappers.control.get(entity);
            PhysicsComponent physics = Mappers.physics.get(entity);
            StateMachine fsm = Mappers.fsm.get(entity).fsm;

            physics.fallTime = 0.0f;

            if (control.jump) {
                fsm.changeState(GHOST_JUMP);
            } else if (!physics.isStanding) {
                fsm.changeState(GHOST_FLY);
            } else if (control.direction != null) {
                fsm.changeState(GHOST_WALK);
            } else if (control.divide) {
                if (physics.canDivide) {
                    fsm.changeState(GHOST_DIVIDE);
                } else {
                    control.divide = false;
                }
            } else if (control.die) {
                fsm.changeState(GHOST_DIE);
            }
        }
    },

    GHOST_WALK {
        @Override
        public void enter(Entity entity) {
            super.enter(entity);

            Sounds.loop(Sounds.WALK);
        }

        @Override
        public void update(Entity entity) {
            super.update(entity);

            ControlComponent control = Mappers.control.get(entity);
            PhysicsComponent physics = Mappers.physics.get(entity);
            StateMachine fsm = Mappers.fsm.get(entity).fsm;

            physics.fallTime = 0.0f;

            if (control.jump) {
                fsm.changeState(GHOST_JUMP);
            } else if (!physics.isStanding) {
                fsm.changeState(GHOST_FLY);
            } else if (control.direction == null) {
                fsm.changeState(GHOST_STAND);
            } else if (control.divide) {
                if (physics.canDivide) {
                    fsm.changeState(GHOST_DIVIDE);
                } else {
                    control.divide = false;
                }
            } else if (control.die) {
                fsm.changeState(GHOST_DIE);
            }
        }

        @Override
        public void exit(Entity entity) {
            super.exit(entity);

            Sounds.stop(Sounds.WALK);
        }
    },

    GHOST_JUMP {
        @Override
        public void enter(Entity entity) {
            super.enter(entity);

            ControlComponent control = Mappers.control.get(entity);
            control.jump = false;
        }

        @Override
        public void update(Entity entity) {
            super.update(entity);

            AnimationComponent animation = Mappers.animation.get(entity);
            PhysicsComponent physics = Mappers.physics.get(entity);
            StateMachine fsm = Mappers.fsm.get(entity).fsm;

            if (animation.ended) {
                if (physics.isStanding) {
                    fsm.changeState(GHOST_STAND);
                } else {
                    fsm.changeState(GHOST_FLY);
                }
            }
        }
    },

    GHOST_FLY {
        @Override
        public void update(Entity entity) {
            super.update(entity);

            PhysicsComponent physics = Mappers.physics.get(entity);
            StateMachine fsm = Mappers.fsm.get(entity).fsm;
            ControlComponent control = Mappers.control.get(entity);

            if (control.die) {
                fsm.changeState(GHOST_DIE);
            } else if (physics.isStanding) {
                fsm.changeState(GHOST_STAND);
            }
        }

        @Override
        public void exit(Entity entity) {
            super.exit(entity);

            PhysicsComponent component = Mappers.physics.get(entity);
            if (component.fallTime > 0.4f) {
                Sounds.play(Sounds.FALL);
                component.fallTime = 0.0f;
            }
        }
    },

    GHOST_DIVIDE {
        @Override
        public void enter(Entity entity) {
            super.enter(entity);

            Sounds.play(Sounds.DIVIDE);
        }

        @Override
        public void update(Entity entity) {
            super.update(entity);

            AnimationComponent animation = Mappers.animation.get(entity);
            StateMachine fsm = Mappers.fsm.get(entity).fsm;

            if (animation.ended) {
                MessageManager.getInstance().dispatchMessage(EntityManager.MESSAGE_ADD_PLAYER, entity);

                fsm.changeState(GHOST_ARISE);
            }
        }

        @Override
        public void exit(Entity entity) {
            ControlComponent control = Mappers.control.get(entity);
            control.divide = false;
        }
    },

    GHOST_DIE {
        @Override
        public void enter(Entity entity) {
            super.enter(entity);

            Sounds.play(Sounds.DIE);
        }

        @Override
        public void update(Entity entity) {
            super.update(entity);

            AnimationComponent animation = Mappers.animation.get(entity);

            if (animation.ended) {
                MessageManager.getInstance().dispatchMessage(EntityManager.MESSAGE_DELETE_PLAYER, entity);
            }
        }
    },

    GHOST_ARISE {
        @Override
        public void update(Entity entity) {
            super.update(entity);

            AnimationComponent animation = Mappers.animation.get(entity);
            PhysicsComponent physics = Mappers.physics.get(entity);
            StateMachine fsm = Mappers.fsm.get(entity).fsm;

            if (animation.ended) {
                if (physics.isStanding) {
                    fsm.changeState(GHOST_STAND);
                } else {
                    fsm.changeState(GHOST_FLY);
                }
            }
        }
    };

    @Override
    public void enter(Entity entity) {
        AnimationComponent animation = Mappers.animation.get(entity);
        animation.ended = false;
        animation.time = 0.0f;
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
