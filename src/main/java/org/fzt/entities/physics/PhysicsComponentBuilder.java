package org.fzt.entities.physics;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

public class PhysicsComponentBuilder {
    final PhysicsComponent _ph;
    final BodyDef _bd;
    final FixtureDef _fd;

    public PhysicsComponentBuilder(PhysicsComponent ph) {
        _ph = ph;
        _bd = new BodyDef();
        _fd = new FixtureDef();
    }

    public PhysicsComponentBuilder() {
        this(new PhysicsComponent());
        _bd.setFixedRotation(true);
        _bd.setType(BodyType.DYNAMIC);
        _fd.setDensity(50);
        _fd.setFriction(0);
    }

    public PhysicsComponent build(){
        _ph.setBodyDef(_bd);
        _ph.setFixtureDef(_fd);
        return _ph;
    }

    public PhysicsComponentBuilder raycastIgnored(boolean ignored) { _ph.setRaycastIgnored(ignored); return this; }
    public PhysicsComponentBuilder raycastIgnored() { _ph.setRaycastIgnored(true); return this; }

    public PhysicsComponentBuilder type(BodyType t) { _bd.setType(t); return this; }
    public PhysicsComponentBuilder bullet() { _bd.setBullet(true); return this; }
    public PhysicsComponentBuilder fixedRotation(boolean fixed) { _bd.setFixedRotation(fixed); return this; }
    public PhysicsComponentBuilder fixedRotation() { return fixedRotation(true); }
    public PhysicsComponentBuilder linearVelocity(Vec2 v) { _bd.setLinearVelocity(v); return this; }
    public PhysicsComponentBuilder angularVelocity(float v) { _bd.setAngularVelocity(v); return this; }

    public PhysicsComponentBuilder wallFriction(float fr) { _fd.setFriction(fr); return this; }
    public PhysicsComponentBuilder density(float den) { _fd.setDensity(den); return this; }
    public PhysicsComponentBuilder restitution(float rest) { _fd.setRestitution(rest); return this; }
}
