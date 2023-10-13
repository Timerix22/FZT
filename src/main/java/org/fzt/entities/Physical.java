package org.fzt.entities;

import com.almasb.fxgl.physics.PhysicsComponent;

public interface Physical {
    //TODO replace with new PhysicsComponentBuilder class and add density to dynamic entities
    PhysicsComponent createPhysics();

    PhysicsComponent getPhysics();
}
