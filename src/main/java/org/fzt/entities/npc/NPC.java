package org.fzt.entities.npc;

import com.almasb.fxgl.physics.PhysicsComponent;
import org.fzt.entities.Mortal;

public interface NPC extends Mortal {
    AIComponent getAI();
    PhysicsComponent getPhysics();
}
