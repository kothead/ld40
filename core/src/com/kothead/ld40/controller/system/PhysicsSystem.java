package com.kothead.ld40.controller.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kothead.ld40.controller.SystemPriority;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.Direction;
import com.kothead.ld40.model.component.CollisionBoxComponent;
import com.kothead.ld40.model.component.PhysicsComponent;
import com.kothead.ld40.model.component.PositionComponent;
import com.kothead.ld40.model.component.VelocityComponent;

// Collision resolution from https://www.defold.com/tutorials/runner/
public class PhysicsSystem extends EntitySystem {

    private static final String LAYER_COLLISION = "collision";
    private static final float G = 500f;
    private static final float GROUND_CONTACT = 0.7f;
    private static final float STAND_OFF_DELAY = 0.05f;

    private Array<Polygon> mapObjects;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> collisionEntities;

    public PhysicsSystem(TiledMap map) {
        super(SystemPriority.PHYSICS_SYSTEM);

        mapObjects = new Array<Polygon>();
        MapObjects objects = map.getLayers().get(LAYER_COLLISION).getObjects();
        for (MapObject mapObject: objects) {
            if (mapObject instanceof PolygonMapObject) {
                PolygonMapObject polygonMapObject = (PolygonMapObject) mapObject;
                mapObjects.add(polygonMapObject.getPolygon());
            }
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class, CollisionBoxComponent.class).get());
        collisionEntities = engine.getEntitiesFor(Family.all(PositionComponent.class, CollisionBoxComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity: entities) {
            PhysicsComponent physics = Mappers.physics.get(entity);
            if (physics.isStanding) {
                physics.standTime += deltaTime;
                if (physics.standTime > STAND_OFF_DELAY) {
                    physics.isStanding = false;
                    physics.standTime = 0.0f;
                }
            }

            boolean isStanding = false;

            Vector2 position = Mappers.position.get(entity).position;
            Vector2 velocity = Mappers.velocity.get(entity).velocity;
            Vector2 correction = new Vector2();

            velocity.mulAdd(Direction.UP.getVector(), G * deltaTime);

            Polygon polygon1 = Mappers.collisionBox.get(entity).polygon;
            polygon1 = new Polygon(polygon1.getVertices());
            polygon1.translate(position.x, position.y);

            for (Polygon polygon2: mapObjects) {
                isStanding |= handleContact(polygon1, polygon2, position, velocity, correction);
            }

            for (Entity collisionEntity: collisionEntities) {
                if (entity == collisionEntity) continue;

                Vector2 position2 = Mappers.position.get(collisionEntity).position;
                Polygon polygon2 = Mappers.collisionBox.get(collisionEntity).polygon;
                polygon2 = new Polygon(polygon2.getVertices());
                polygon2.translate(position2.x, position2.y);
                isStanding |= handleContact(polygon1, polygon2, position, velocity, correction);
            }

            if (isStanding) {
                physics.isStanding = true;
                physics.standTime = 0.0f;
            }
        }
    }

    /**
     * Handles contact between two polygons. Updates position, velocity and correction vectors
     * @param polygon1 collision box of first object
     * @param polygon2 collision box of second object
     * @param position of first object
     * @param velocity of first object
     * @param correction of collision translations
     * @return
     */
    private boolean handleContact(Polygon polygon1, Polygon polygon2,
                               Vector2 position, Vector2 velocity,
                               Vector2 correction) {
        boolean isStanding = false;

        Intersector.MinimumTranslationVector translation = new Intersector.MinimumTranslationVector();
        Intersector.overlapConvexPolygons(polygon1, polygon2, translation);
        if (translation.normal.isZero()) return false;

        float projection = correction.dot(translation.normal);

        float difference = translation.depth - projection;
        Vector2 compensation = new Vector2(
                translation.normal.x * difference,
                translation.normal.y * difference);
        correction.add(compensation);
        position.add(compensation);
        if (translation.normal.y > GROUND_CONTACT) {
            isStanding = true;
        }

        projection = velocity.dot(translation.normal);
        if (projection < 0) {
            compensation = new Vector2(
                    translation.normal.x * projection,
                    translation.normal.y * projection);
            velocity.sub(compensation);
        }

        return isStanding;
    }
}
