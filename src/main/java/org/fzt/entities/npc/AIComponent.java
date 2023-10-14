package org.fzt.entities.npc;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.Nullable;

public abstract class AIComponent extends Component {
    @Nullable
    protected Entity _targetEntity;
    protected double minFollowDistance;
    protected double maxFollowDistance;

    @Override
    public void onUpdate(double tpf){
        if(_targetEntity != null){
            var targetPos = _targetEntity.getCenter();
            var distance = targetPos.subtract(entity.getCenter()).magnitude();
            // Stops AI if the target is too far.
            if(distance > maxFollowDistance) {
                stopMoving();
                // FXGL sensors work strange, they don't handle re-entering agro range properly.
                // That's why it's better to save _targetEntity until it becomes closer to the AI.
                //_targetEntity = null;
                return;
            }

            if(distance >= minFollowDistance)
                moveTo(targetPos);
        }
    }

    public abstract void moveTo(Point2D pos);

    public abstract void stopMoving();

    void follow(Entity target, double minDistance, double maxDistance){
        _targetEntity = target;
        minFollowDistance = minDistance;
        maxFollowDistance = maxDistance;
    }
}
