package com.kothead.ld40.data;

import com.badlogic.ashley.core.ComponentMapper;
import com.kothead.ld40.model.component.*;

public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<FSMComponent> fsm = ComponentMapper.getFor(FSMComponent.class);
    public static final ComponentMapper<ControlComponent> control = ComponentMapper.getFor(ControlComponent.class);
    public static final ComponentMapper<DirectionComponent> direction = ComponentMapper.getFor(DirectionComponent.class);
    public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
    public static final ComponentMapper<AnimationComponent> animation = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<CollisionBoxComponent> collisionBox = ComponentMapper.getFor(CollisionBoxComponent.class);
    public static final ComponentMapper<HumanControlComponent> humanControl = ComponentMapper.getFor(HumanControlComponent.class);
}
