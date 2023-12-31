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
    public Player _player;
    // px/s
    Vec2 nextMove = new Vec2();

    public PlayerController(Player p, PhysicsComponent playerPhysics) {
        super();
        _player = p;
        physics = playerPhysics;
        initInput();
    }

    public void attack(Point2D destination) {
        if (_player.equippedWeapon != null)
            _player.equippedWeapon.attack(_player.getPosition(), destination);
    }

    public void moveLeft() {
        nextMove.x -= _player.getStats().getMovingSpeed();
    }

    public void moveRight() {
        nextMove.x += _player.getStats().getMovingSpeed();
    }

    public void moveUp() {
        nextMove.y -= _player.getStats().getMovingSpeed();
    }

    public void moveDown() {
        nextMove.y += _player.getStats().getMovingSpeed();
    }

    @Override
    public void onUpdate(double timePerFrame) {
        super.onUpdate(timePerFrame);
        final double normalTimePerFrame = 1.0 / 60;
        physics.setLinearVelocity(nextMove.mul(timePerFrame / normalTimePerFrame).toPoint2D());
        nextMove = new Vec2();
    }

    void initInput() {

        FXGL.onKey(KeyCode.A, "Move Left", this::moveLeft);
        FXGL.onKey(KeyCode.D, "Move Right", this::moveRight);
        FXGL.onKey(KeyCode.W, "Move Up", this::moveUp);
        FXGL.onKey(KeyCode.S, "Move Down", this::moveDown);

        FXGL.onBtn(MouseButton.PRIMARY, "Primary Attack", () ->
            attack(FXGL.getInput().getVectorToMouse(_player.getPosition())));
    }
}
