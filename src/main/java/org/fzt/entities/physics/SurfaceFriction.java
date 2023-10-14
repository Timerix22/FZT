package org.fzt.entities.physics;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

/**
 * This component adds background surface friction force to the entity to stop it infinitely wly away.
 * There is no such thing by default in 2D physics engine because background surface is something from 3D world.
 */
public class SurfaceFriction extends Component {
    final static float g = 9.81f;
    final static float v_min = 0.3f;
    //TODO dynamically set friction coefficient on surface change
    float u = 3f;
    final PhysicsComponent physics;

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

        final Point2D v0 = physics.getLinearVelocity();
        final double v0_magnitude = v0.magnitude();

        // doesn't need to calculate friction if the body isn't moving
        if(v0_magnitude == 0)
            return;

        Point2D v1 = Point2D.ZERO;
        // just stops the body if it is moving too slow
        if(v0_magnitude >= v_min) {
            // F=u*m*g directed against velocity
            // a=F/m=u*g
            // vf=a*t  friction velocity
            // v1=v0-vf
            var vf_magnitude = u * g * tpf;
            var vf = v0.normalize().multiply(vf_magnitude);
            // if friction > v0Magnitude the body will accelerate in the opposite direction, (|v1| = |v0 - vf| > 0)
            // then negate gained velocity by friction and again accelerate by it infinitely
            if (vf_magnitude < v0_magnitude)
                v1 = v0.subtract(vf);
        }
        physics.setLinearVelocity(v1);
    }
}
