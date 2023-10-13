package org.fzt.entities;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

/**
 * This component adds background surface friction force to the entity to stop it infinitely wly away.
 * There is no such thing by default in 2D physics engine because background surface is something from 3D world.
 */
public class SurfaceFriction extends Component {
    final static float g = 9.81f;
    final static float v_min = 0.01f;
    //TODO dynamically set friction coefficient on surface change
    float u = 1f;
    final PhysicsComponent physics;

    /**
     * @param coefficient friction coefficient (should be 100 or more
     * @param physics
     */
    public SurfaceFriction(PhysicsComponent physics){
        super();
        this.physics = physics;
    }

    @Override
    public void onUpdate(double tpf) {
        // doesn't need to calculate friction force
        // if the friction coefficient is zero
        if(u == 0)
            return;

        var v = physics.getLinearVelocity();
        double vX = v.getX();
        double vY = v.getY();
        // just stops the body if it is moving too slow
        if(vX < v_min && vY < v_min) {
            physics.setLinearVelocity(new Point2D(0, 0));
            return;
        }

        var m = physics.getBody().getMass();
        var direction = new Point2D(-vX, -vY).normalize();
        // F=u*m*g directed against velocity
        var frictionForce = direction.multiply(u*m*g);
        physics.applyForceToCenter(frictionForce);
    }
}
