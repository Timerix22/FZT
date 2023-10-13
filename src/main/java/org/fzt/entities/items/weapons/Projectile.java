package org.fzt.entities.items.weapons;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import org.fzt.entities.EntityType;
import org.fzt.entities.Physical;

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
     */
    public void shoot(Point2D velocity){
        _physics.setLinearVelocity(velocity);
    }

    public PhysicsComponent createPhysics(){
        var ph = new PhysicsComponent();
        var bd = new BodyDef();
        bd.setType(BodyType.KINEMATIC);
        ph.setBodyDef(bd);
        return ph;
    }

    @Override
    public PhysicsComponent getPhysics() { return _physics; }

    public abstract float getBaseDamage();
}
