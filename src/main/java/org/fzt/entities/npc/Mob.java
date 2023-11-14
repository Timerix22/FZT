package org.fzt.entities.npc;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.Node;
import org.fzt.entities.CharacterEntity;
import org.fzt.entities.CharacterStats;
import org.fzt.entities.EntityType;
import org.fzt.entities.Mortal;
import org.fzt.entities.physics.Physical;
import org.fzt.entities.physics.PhysicsComponentBuilder;
import org.fzt.entities.physics.SurfaceFriction;

public abstract class Mob extends CharacterEntity implements NPC, Physical {
    protected final PhysicsComponent _physics = createPhysics();
    protected double _agroRadius;
    AIComponent _ai;

    public Mob(Node view, HitBox hitBox, double agroRadius) {
        super(new CharacterStats());
        _agroRadius = agroRadius;
        setType(EntityType.NPC);
        addComponent(getAI());
        addComponent(new CollidableComponent(true));
        getViewComponent().addChild(view);
        getBoundingBoxComponent().addHitBox(hitBox);
        addComponent(new SurfaceFriction(_physics));
        addComponent(_physics);
    }

    @Override
    public PhysicsComponent createPhysics() {
        return new PhysicsComponentBuilder()
                .fixedRotation()
                .type(BodyType.DYNAMIC)
                .density(50)
                .wallFriction(0)
                .build();
    }

    @Override
    public PhysicsComponent getPhysics() {
        return _physics;
    }

    @Override
    public AIComponent getAI() {
        if (_ai == null)
            _ai = new MobAIComponent(this);
        return _ai;
    }

    public double getAgroRadius() {
        return _agroRadius;
    }

    public void attack(Entity target) {
        if(target instanceof Mortal targetMortal && target.getCenter().subtract(getCenter()).magnitude() < 96) {
            targetMortal.dealDamage(getStats().getMiliDamage());
        }
    }
}
