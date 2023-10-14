package org.fzt.entities.npc;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.SensorCollisionHandler;
import javafx.geometry.Point2D;
import org.fzt.entities.EntityType;
import org.jetbrains.annotations.Nullable;

public class MobAIComponent extends AIComponent {
    final Mob _mob;
    @Nullable
    Point2D _targetPoint;

    public MobAIComponent(Mob mob) {
        _mob = mob;
        var agroRadius = _mob.getAgroRadius();
        _mob._physics.addSensor(new HitBox(BoundingShape.Companion.circle(agroRadius)), new SensorCollisionHandler() {
            @Override
            protected void onCollisionBegin(Entity other) {
                if (other.getType().toString().equals(EntityType.PLAYER.toString())) {
                    System.out.println("Player enters agro radius");
                    follow(other, other.getWidth() / 2, agroRadius);
                }
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        // stops the mob if it has no target to move
        if (_targetPoint == null) {
            return;
        }

        Point2D targetVector = _targetPoint.subtract(entity.getPosition());
        if (targetVector.magnitude() < 2f) {
            stopMoving();
            return;
        }
        var velocity = targetVector.normalize().multiply(_mob.getStats().getMovingSpeed());
        _mob._physics.setLinearVelocity(velocity);
    }

    @Override
    public void moveTo(Point2D pos) {
        _targetPoint = pos;
    }

    @Override
    public void stopMoving() {
        _targetPoint = null;
        _mob._physics.setLinearVelocity(Point2D.ZERO);
    }

    @Override
    public void attack(Entity target) {

    }
}
