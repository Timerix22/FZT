package org.fzt.entities.npc;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import org.fzt.Assets;
import org.jetbrains.annotations.Nullable;

public class TestMob extends Mob {
    AIComponent _ai;

    float speed = 3*64;

    public TestMob() {
        super(Assets.loadTexture64("tutel.png"),
            new HitBox(BoundingShape.box(64, 64)));
    }

    @Override
    public float getMaxHP() {
        return 200;
    }

    @Override
    public AIComponent getAI() {
        if(_ai == null)
            _ai = new AIComponent() {
                @Nullable
                Point2D _targetPoint;

                @Override
                public void onUpdate(double tpf) {
                    super.onUpdate(tpf);
                    // stops the mob if it has no target to move
                    if (_targetPoint == null) {
                        return;
                    }

                    Point2D targetVector = _targetPoint.subtract(getPosition());
                    if(targetVector.magnitude() < 2f) {
                        stopMoving();
                        return;
                    }
                    var velocity = targetVector.normalize().multiply(speed);
                    getPhysics().setLinearVelocity(velocity);
                }

                @Override
                public void moveTo(Point2D pos) {
                    _targetPoint = pos;
                }

                @Override
                public void stopMoving() {
                    _targetPoint = null;
                    getPhysics().setLinearVelocity(Point2D.ZERO);
                }
            };
        return _ai;
    }
}
