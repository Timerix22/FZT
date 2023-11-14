package org.fzt.entities.npc;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.fzt.Assets;

public class RatkinMob extends Mob {

    public RatkinMob() {
        super(Assets.loadTexture64("ratkin.png"),
                new HitBox(BoundingShape.box(64, 64)),
                64 * 20);
    }
}
