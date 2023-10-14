package org.fzt.entities.physics;

import com.almasb.fxgl.physics.PhysicsComponent;

public interface Physical {
    /**
     * Creates physics for an entity. Usually is called in PhysicsComponent field initialization.
     * P.S. You can use PhysicsBuilder to create PhysicsComponent
     */
    PhysicsComponent createPhysics();

    PhysicsComponent getPhysics();
}
