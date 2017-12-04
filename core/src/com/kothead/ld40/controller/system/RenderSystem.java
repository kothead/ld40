package com.kothead.ld40.controller.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.kothead.ld40.controller.SystemPriority;
import com.kothead.ld40.data.Mappers;
import com.kothead.ld40.model.Direction;
import com.kothead.ld40.model.component.CollisionBoxComponent;
import com.kothead.ld40.model.component.HumanControlComponent;
import com.kothead.ld40.model.component.PositionComponent;
import com.kothead.ld40.model.component.SpriteComponent;
import com.kothead.ld40.screen.BaseScreen;

public class RenderSystem extends EntitySystem {

    private static final float MAX_CAMERA_SPEED = 500f;

    private BaseScreen screen;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> humanEntities;

    private OrthogonalTiledMapRenderer mapRenderer;
    private ShapeRenderer shapes;

    private float tileWidth, tileHeight;
    private float mapWidth, mapHeight;

    public RenderSystem(BaseScreen screen, TiledMap map) {
        super(SystemPriority.RENDER_SYSTEM);
        this.screen = screen;

        mapRenderer = new OrthogonalTiledMapRenderer(map, 1);
        shapes = new ShapeRenderer();

        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileWidth = tileLayer.getTileWidth();
        tileHeight = tileLayer.getTileHeight();
        mapWidth = tileLayer.getWidth() * tileWidth;
        mapHeight = tileLayer.getHeight() * tileHeight;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(
                SpriteComponent.class,
                PositionComponent.class)
                .get());
        humanEntities = engine.getEntitiesFor(Family.all(
                PositionComponent.class,
                SpriteComponent.class,
                HumanControlComponent.class)
                .get());
    }

    @Override
    public void update(float deltaTime) {
        moveCamera(deltaTime);

        mapRenderer.setView(screen.getCamera());
        mapRenderer.render();

        mapRenderer.getBatch().begin();
        for (Entity entity: entities) {
            Vector2 position = Mappers.position.get(entity).position;

            Sprite sprite = Mappers.sprite.get(entity).sprite;

            // dirty workaround. As always
            if (sprite.getRegionWidth() > sprite.getRegionHeight()
                    && Mappers.direction.get(entity).direction == Direction.LEFT) {
                sprite.setPosition(position.x - sprite.getRegionHeight(), position.y);
            } else {
                sprite.setPosition(position.x, position.y);
            }

            sprite.draw(mapRenderer.getBatch());
        }
        mapRenderer.getBatch().end();

        // DEBUG
//        shapes.setColor(1f, 1f, 1f, 1f);
//        shapes.setProjectionMatrix(screen.getCamera().combined);
//        shapes.setAutoShapeType(true);
//        shapes.begin();
//        for (Entity entity: entities) {
//            Polygon polygon = Mappers.collisionBox.get(entity).polygon;
//            Vector2 position = Mappers.position.get(entity).position;
//            polygon = new Polygon(polygon.getVertices());
//            polygon.translate(position.x, position.y);
//            shapes.polygon(polygon.getTransformedVertices());
//
//            polygon = Mappers.collisionBox.get(entity).polygonDivide;
//            polygon = new Polygon(polygon.getVertices());
//            polygon.translate(position.x, position.y);
//            shapes.polygon(polygon.getTransformedVertices());
//        }
//        shapes.end();
    }

    private void moveCamera(float deltaTime) {
        if (humanEntities.size() == 0) return;

        Entity humanEntity = humanEntities.first();

        Vector2 position = Mappers.position.get(humanEntity).position;
        Sprite sprite = Mappers.sprite.get(humanEntity).sprite;
        Vector2 size = new Vector2(sprite.getWidth(), sprite.getHeight());

        float positionX = position.x + size.x / 2;
        float positionY = position.y + size.y / 2;

        if (positionX < screen.getWorldWidth() / 2f) {
            positionX = screen.getWorldWidth() / 2f;
        } else if (positionX > mapWidth - screen.getWorldWidth() / 2f ) {
            positionX = mapWidth - screen.getWorldWidth() / 2f;
        }

        if (positionY < screen.getWorldHeight() / 2f) {
            positionY = screen.getWorldHeight() / 2f;
        } else if (positionY > mapHeight - screen.getWorldHeight() / 2f) {
            positionY = mapHeight - screen.getWorldHeight() / 2f;
        }

        float maxCameraSpeed = MAX_CAMERA_SPEED * deltaTime;
        float dx = screen.getCamera().position.x - positionX;
        float dy = screen.getCamera().position.y - positionY;
        if (Math.abs(dx) > maxCameraSpeed) {
            positionX = screen.getCamera().position.x - Math.signum(dx) * maxCameraSpeed;
        }
        if (Math.abs(dy) > maxCameraSpeed) {
            positionY = screen.getCamera().position.y - Math.signum(dy) * maxCameraSpeed;
        }

        screen.getCamera().position.set(positionX, positionY, 0);
        screen.getCamera().update();

    }
}
