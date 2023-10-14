package org.fzt.entities.npc;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.SensorCollisionHandler;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import org.fzt.Assets;
import org.fzt.entities.*;

public abstract class Mob extends MortalEntity implements NPC, Physical {
    final static float agro_radius=64*10;

    public Mob(String texture, HitBox hitBox) {
        super();
        setType(EntityType.NPC);
        addComponent(getAI());
        addComponent(new CollidableComponent(true));
        getViewComponent().addChild(Assets.loadTexture64(texture));
        getBoundingBoxComponent().addHitBox(hitBox);
        addComponent(new SurfaceFriction(_physics));
        addComponent(_physics);
        _physics.addSensor(new HitBox(BoundingShape.Companion.circle(agro_radius)), new SensorCollisionHandler() {
            @Override
            protected void onCollisionBegin(Entity other) {
                System.out.println("entity in agro radius: "+other.getType());
                if(other.getType().toString().equals(EntityType.PLAYER.toString())){
                    getAI().follow(other, other.getWidth()/2, agro_radius);
                }
            }
        });
    }

    final PhysicsComponent _physics = createPhysics();

    @Override
    public PhysicsComponent createPhysics(){
        var ph = new PhysicsComponent();
        var bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        ph.setBodyDef(bd);
        var fd = new FixtureDef();
        fd.setDensity(50);
        fd.setFriction(0);
        ph.setFixtureDef(fd);
        return ph;
    }

    @Override
    public PhysicsComponent getPhysics() { return _physics; }
}
