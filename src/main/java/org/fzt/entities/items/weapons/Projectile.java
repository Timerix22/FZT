package org.fzt.entities.items.weapons;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.fzt.entities.EntityType;
import org.fzt.entities.LifetimeComponent;
import org.fzt.entities.physics.Physical;
import org.fzt.entities.physics.PhysicsComponentBuilder;

public abstract class Projectile extends Entity implements Physical {

    private final PhysicsComponent _physics = createPhysics();

    /**
     * Creates new projectile with zero velocity;
     */
    public Projectile(Node view, HitBox hitBox){
        setType(EntityType.PROJECTILE);
        getViewComponent().addChild(view);
        getBoundingBoxComponent().addHitBox(hitBox);
        var collidable = new CollidableComponent(true);
        collidable.addIgnoredType(EntityType.PLAYER);
        addComponent(collidable);
        addComponent(_physics);
    }

    /**
     * Shoots the projectile
     * @param velocity in px/s
     * @param lifetime time in seconds before projectile self-destructs
     */
    public void shoot(Point2D velocity, double lifetime){
        _physics.setLinearVelocity(velocity);
        addComponent(new LifetimeComponent(lifetime));
    }

    public PhysicsComponent createPhysics(){
        return new PhysicsComponentBuilder()
                .type(BodyType.DYNAMIC)
                .bullet()
                .density(0.5f)
                .build();
    }

    @Override
    public PhysicsComponent getPhysics() { return _physics; }

    public abstract float getBaseDamage();
}
