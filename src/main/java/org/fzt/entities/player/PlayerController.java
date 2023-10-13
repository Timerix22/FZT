package org.fzt.entities.player;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

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
        initInput();
    }

    public void attack(Point2D destination){
        player.equippedWeapon.attack(player.getWeaponPos(), destination);
    }

    static final float base_speed = 5*64;

    float speedWithStats(){
        return base_speed * ( 1 + player.getStats().dexterity / 100 + player.getStats().strength / 400);
    }

    // px/s
    Vec2 nextMove = new Vec2();

    public void moveLeft() {
        nextMove.x -= speedWithStats();
    }
    public void moveRight() {
        nextMove.x += speedWithStats();
    }
    public void moveUp() {
        nextMove.y -= speedWithStats();
    }
    public void moveDown() {
        nextMove.y += speedWithStats();
    }

    @Override
    public void onUpdate(double timePerFrame) {
        super.onUpdate(timePerFrame);
        final double normalTimePerFrame = 1.0/60;
        physics.setLinearVelocity(nextMove.mul(timePerFrame / normalTimePerFrame).toPoint2D());
        nextMove = new Vec2();
    }
    
    void initInput(){

        FXGL.onKey (KeyCode.A, "Move Left", this::moveLeft);
        FXGL.onKey(KeyCode.D, "Move Right", this::moveRight);
        FXGL.onKey(KeyCode.W, "Move Up", this::moveUp);
        FXGL.onKey(KeyCode.S, "Move Down", this::moveDown);

        FXGL.onBtn(MouseButton.PRIMARY, "Primary Attack", ()->
            attack(FXGL.getInput().getVectorToMouse(player.getWeaponPos())));
    }
}
