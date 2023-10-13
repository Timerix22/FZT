package org.fzt.entities.items.weapons;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
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
        System.out.println("projectile mass: "+_physics.getBody().getMass());
    }

    public PhysicsComponent createPhysics(){
        var ph = new PhysicsComponent();
        var bd = new BodyDef();
        bd.setType(BodyType.DYNAMIC);
        bd.setBullet(true);
        var fd = new FixtureDef();
        fd.setDensity(0.01f);
        ph.setBodyDef(bd);
        ph.setFixtureDef(fd);
        return ph;
    }

    @Override
    public PhysicsComponent getPhysics() { return _physics; }

    public abstract float getBaseDamage();
}
