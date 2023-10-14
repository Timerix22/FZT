package org.fzt.entities.buildings;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.Node;
import org.fzt.entities.physics.Physical;
import org.fzt.entities.physics.PhysicsComponentBuilder;

/**
 * An entity with static physics
 */
public abstract class Building extends Entity implements Physical {

    private final PhysicsComponent _ph = createPhysics();

    public Building(Node view, HitBox  hitbox){
        getViewComponent().addChild(view);
        getBoundingBoxComponent().addHitBox(hitbox);
        addComponent(new CollidableComponent(true));
        addComponent(getPhysics());
    }

    public Building(Node view){
        this(view, new HitBox(
            BoundingShape.box(
                view.getLayoutBounds().getWidth(),
                view.getLayoutBounds().getHeight())));
    }

    public PhysicsComponent createPhysics(){
        return new PhysicsComponentBuilder()
                .type(BodyType.STATIC)
                .build();
    }

    @Override
    public PhysicsComponent getPhysics() {
        return _ph;
    }
}
