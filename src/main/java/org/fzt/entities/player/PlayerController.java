package org.fzt.entities.player;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

/**
 * Defines player movement and other interactions with the world
 */
public class PlayerController extends Component {

    public PhysicsComponent physics;
    public Player player;
    
    public PlayerController(Player p, PhysicsComponent playerPhysics) {
        super();
        player = p;
        physics = playerPhysics;
    }

    public void attack(Vec2 destination){
        player.equippedWeapon.attack(destination);
    }

    static final float base_speed = 5;

    float speedWithStats(){
        return base_speed * ( 1 + player.getStats().dexterity / 100 + player.getStats().strength / 400);
    }

    // m/s
    Vec2 nextMove = new Vec2();

    public void moveLeft() {
        nextMove.x -= speedWithStats();
    }
    public void moveRight() {
        nextMove.x += speedWithStats();
    }
    public void moveUp() {
        nextMove.y += speedWithStats();
    }
    public void moveDown() {
        nextMove.y -= speedWithStats();
    }

    @Override
    public void onUpdate(double timePerFrame) {
        super.onUpdate(timePerFrame);
        final double normalTimePerFrame = 1.0/60;
        physics.setBodyLinearVelocity(nextMove.mul(timePerFrame / normalTimePerFrame)); // m/s
        nextMove = new Vec2();
    }
}
