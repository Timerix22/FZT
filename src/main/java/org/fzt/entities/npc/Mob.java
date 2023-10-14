package org.fzt.entities.npc;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.SensorCollisionHandler;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.Node;
import org.fzt.entities.EntityType;
import org.fzt.entities.MortalEntity;
import org.fzt.entities.physics.Physical;
import org.fzt.entities.physics.PhysicsComponentBuilder;
import org.fzt.entities.physics.SurfaceFriction;

public abstract class Mob extends MortalEntity implements NPC, Physical {
    final static float agro_radius=64*10;

    public Mob(Node view, HitBox hitBox) {
        super();
        setType(EntityType.NPC);
        addComponent(getAI());
        addComponent(new CollidableComponent(true));
        getViewComponent().addChild(view);
        getBoundingBoxComponent().addHitBox(hitBox);
        addComponent(new SurfaceFriction(_physics));
        addComponent(_physics);
        _physics.addSensor(new HitBox(BoundingShape.Companion.circle(agro_radius)), new SensorCollisionHandler() {
            @Override
            protected void onCollisionBegin(Entity other) {
                if(other.getType().toString().equals(EntityType.PLAYER.toString())){
                    System.out.println("Player enters agro radius");
                    getAI().follow(other, other.getWidth()/2, agro_radius);
                }
            }
        });
    }

    final PhysicsComponent _physics = createPhysics();

    @Override
    public PhysicsComponent createPhysics(){
        return new PhysicsComponentBuilder()
            .fixedRotation()
            .type(BodyType.DYNAMIC)
            .density(50)
            .wallFriction(0)
            .build();
    }

    @Override
    public PhysicsComponent getPhysics() { return _physics; }
}
