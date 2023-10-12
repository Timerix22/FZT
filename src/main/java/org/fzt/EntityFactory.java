package org.fzt;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

public class EntityFactory {

    public static Entity spawnPlayer(Vec2 pos){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setFixtureDef(new FixtureDef().friction(0).density(0.1f));
        BodyDef bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bd);
        return FXGL.entityBuilder()
                .at(pos)
                .type(EntityType.PLAYER)
                .view(Assets.loadTexture64("player.png"))
                // hitbox smaller than tile size to be able to go through 64px holes
                .bbox(BoundingShape.box(56, 56))
                .collidable()
                .with(physics)
                .with(new PlayerController(physics))
                .buildAndAttach();
    }

    public static Entity spawnWall(Vec2 pos){
        return FXGL.entityBuilder()
                .at(pos)
                .type(EntityType.WALL)
                .viewWithBBox(Assets.loadTexture64("wall.png"))
                .with(new PhysicsComponent())
                .collidable()
                .buildAndAttach();
    }
}
