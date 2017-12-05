package com.kothead.ld40.controller.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.kothead.ld40.controller.SystemPriority;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.component.EnemyComponent;
import com.kothead.ld40.model.component.HumanControlComponent;
import com.kothead.ld40.model.component.PositionComponent;
import com.kothead.ld40.model.component.VelocityComponent;

public class EnemySystem extends EntitySystem {

    private static final float APPROXIMATION = 10f;
    private static final Vector2 RETREAT = new Vector2(1280, 1280);
    private static final float ATTACK_SPEED = 300f;
    private static final float RETREAT_SPEED = 300f;

    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> enemies;

    public EnemySystem() {
        super(SystemPriority.ENEMY_SYSTEM);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        players = engine.getEntitiesFor(Family.all(HumanControlComponent.class, PositionComponent.class).get());
        enemies = engine.getEntitiesFor(Family.all(EnemyComponent.class, PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        if (players.size() == 0) return;

        Entity player = players.first();
        Vector2 playerPos = Mappers.position.get(player).position;
//        if (playerPos.y < 800) return;

        for (Entity enemy: enemies) {
            Vector2 enemyPos = Mappers.position.get(enemy).position;
            EnemyComponent enemyComponent = Mappers.enemy.get(enemy);

            if (isApproximate(enemyPos, enemyComponent.target)) {
                if (enemyComponent.attack) {
                    enemyComponent.attack = false;
                    enemyComponent.target = new Vector2(RETREAT);
                } else {
                    enemyComponent.attack = true;
                    enemyComponent.target = new Vector2(playerPos);
                }
            }

            Mappers.velocity.get(enemy).velocity = new Vector2(enemyComponent.target.x - enemyPos.x,
                    enemyComponent.target.y - enemyPos.y)
                    .nor()
                    .scl(enemyComponent.attack ? ATTACK_SPEED: RETREAT_SPEED);
        }
    }

    public boolean isApproximate(Vector2 p1, Vector2 p2) {
        return Math.abs(p1.x - p2.x) < APPROXIMATION
                && Math.abs(p1.y - p2.y) < APPROXIMATION;
    }
}
