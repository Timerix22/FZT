package org.fzt.entities;

import com.almasb.fxgl.physics.PhysicsComponent;

public interface Physical {
    PhysicsComponent createPhysics();

    PhysicsComponent getPhysics();
}
