package com.kothead.ld40.controller.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kothead.ld40.controller.EntityManager;
import com.kothead.ld40.controller.SystemPriority;
import com.kothead.ld40.data.CollisionBoxes;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.Direction;
import com.kothead.ld40.model.component.*;

// Collision resolution from https://www.defold.com/tutorials/runner/
public class PhysicsSystem extends EntitySystem {

    private static final String LAYER_COLLISION = "collision";
    private static final float G = 500f;
    private static final float GROUND_CONTACT = 0.7f;
    private static final float STAND_OFF_DELAY = 0.05f;

    private Array<Polygon> mapObjects;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> collisionEntities;
    private Polygon finish;

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

        objects = map.getLayers().get("finish").getObjects();
        finish = ((PolygonMapObject) objects.get(0)).getPolygon();
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ControlComponent.class, PositionComponent.class, VelocityComponent.class, CollisionBoxComponent.class).get());
        collisionEntities = engine.getEntitiesFor(Family.all(PositionComponent.class, CollisionBoxComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity: entities) {
            PhysicsComponent physics = Mappers.physics.get(entity);
            physics.canDivide = true;
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

            Polygon polygonDiv = Mappers.collisionBox.get(entity).polygonDivide;
            polygonDiv = new Polygon(polygonDiv.getVertices());
            polygonDiv.translate(position.x, position.y);

            Polygon polygon1 = Mappers.collisionBox.get(entity).polygon;
            polygon1 = new Polygon(polygon1.getTransformedVertices());
            polygon1.translate(position.x, position.y);

            for (Entity collisionEntity: collisionEntities) {
                if (entity == collisionEntity) continue;

                Vector2 position2 = Mappers.position.get(collisionEntity).position;
                Polygon polygon2 = Mappers.collisionBox.get(collisionEntity).polygon;
                polygon2 = new Polygon(polygon2.getTransformedVertices());
                polygon2.translate(position2.x, position2.y);
                isStanding |= handleContact(polygon1, polygon2, position, velocity, correction);

                physics.canDivide &= !intersects(polygonDiv, polygon2);
            }

            for (Polygon polygon2: mapObjects) {
                isStanding |= handleContact(polygon1, polygon2, position, velocity, correction);
                physics.canDivide &= !intersects(polygonDiv, polygon2);
            }

            if (isStanding) {
                physics.isStanding = true;
                physics.standTime = 0.0f;
            } else {
                physics.fallTime += deltaTime;
            }

            if (Mappers.humanControl.has(entity) && intersects(polygon1, finish)) {
                MessageManager.getInstance().dispatchMessage(EntityManager.MESSAGE_WIN);
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

    private boolean intersects(Polygon polygon1, Polygon polygon2) {
        Intersector.MinimumTranslationVector vector = new Intersector.MinimumTranslationVector();
        Intersector.overlapConvexPolygons(polygon1, polygon2, vector);
        return vector.depth != 0;
    }
}
