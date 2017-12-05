package com.kothead.ld40.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kothead.ld40.model.Direction;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.g2d.Animation.PlayMode.*;
import static com.kothead.ld40.controller.state.PlayerState.*;

public class Animations {

    private static final float BASE_ANIMATION_SPEED = 0.05f;

    private static Map<State<Entity>, Builder> builders = new HashMap<State<Entity>, Builder>() {{
        put(GHOST_STAND, new Builder("ghost-stand").count(3).mode(LOOP_PINGPONG));
        put(GHOST_WALK, new Builder("ghost-walk").count(6).mode(LOOP));
        put(GHOST_JUMP, new Builder("ghost-jump").count(5).mode(NORMAL));
        put(GHOST_FLY, new Builder("ghost-fly").count(2).mode(LOOP));
        put(GHOST_DIVIDE, new Builder("ghost-divide").count(12).mode(NORMAL));
        put(GHOST_DIE, new Builder("ghost-die").count(14).mode(NORMAL));
        put(GHOST_ARISE, new Builder("ghost-arise").count(9).mode(NORMAL));
    }};

    private static Map<Direction, Map<State<Entity>, Animation>> cache = new HashMap<Direction, Map<State<Entity>, Animation>>() {{
        put(Direction.LEFT, new HashMap<State<Entity>, Animation>());
        put(Direction.RIGHT, new HashMap<State<Entity>, Animation>());
    }};

    public static Animation<TextureRegion> get(State<Entity> state, Direction direction) {
        Map<State<Entity>, Animation> cache = Animations.cache.get(direction);

        if (!cache.containsKey(state)) {
            cache.put(state, builders.get(state).flip(direction).build());
        }

        return cache.get(state);
    }

    private static class Builder {
        private String texture;
        private int count = 1;
        private float duration = BASE_ANIMATION_SPEED;
        private Animation.PlayMode mode;
        private boolean flipx = false;
        private boolean flipy = false;

        public Builder(String texture) {
            this.texture = texture;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder duration(float duration) {
            this.duration = duration;
            return this;
        }

        public Builder mode(Animation.PlayMode mode) {
            this.mode = mode;
            return this;
        }

        public Builder flip(boolean x, boolean y) {
            this.flipx = x;
            this.flipy = y;
            return this;
        }

        public Builder flip(Direction direction) {
            if (!direction.isOrthogonal()) {
                throw new IllegalArgumentException("Cannot flip in animation diagonal direction: " + direction);
            }

            flipx = direction.getDx() < 0;
            flipy = direction.getDy() < 0;

            return this;
        }

        public Animation<TextureRegion> build() {
            TextureRegion[] regions = !flipx && !flipy
                    ? Images.getFrames(texture, 1, count)
                    : Images.getFrames(texture, 1, count, flipx, flipy);
            Animation<TextureRegion> animation = new Animation<TextureRegion>(duration, regions);
            animation.setPlayMode(mode);
            return animation;
        }
    }
}
