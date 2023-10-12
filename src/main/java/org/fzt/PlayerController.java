package org.fzt;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.inventory.Inventory;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerController extends Component {
    public PlayerStats stats = new PlayerStats();

    public Inventory<Item> inventory = new Inventory<>(16);
    public IWeapon equippedWeapon;

    public PhysicsComponent physics;
    
    public PlayerController(PhysicsComponent playerPhysics) {
        super();
        physics = playerPhysics;
    }

    public void attack(Vec2 destination){
        equippedWeapon.attack(destination);
    }

    static final float base_speed = 5;

    float speedWithStats(){
        return base_speed * ( 1 + stats.dexterity / 100 + stats.strength / 400);
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
