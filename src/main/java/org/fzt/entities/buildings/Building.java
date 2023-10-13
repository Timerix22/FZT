package org.fzt.entities.buildings;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.Node;

/**
 * An entity with static physics
 */
public abstract class Building extends Entity {
    public Building(Node view, HitBox  hitbox){
        getViewComponent().addChild(view);
        getBoundingBoxComponent().addHitBox(hitbox);
        addComponent(new CollidableComponent(true));
        addComponent(createBuildingPhysics());
    }

    public Building(Node view){
        this(view, new HitBox(
            BoundingShape.box(
                view.getLayoutBounds().getWidth(),
                view.getLayoutBounds().getHeight())));
    }

    public PhysicsComponent createBuildingPhysics(){
        var ph = new PhysicsComponent();
        return ph;
    }
}
