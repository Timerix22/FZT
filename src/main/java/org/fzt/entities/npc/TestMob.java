package org.fzt.entities.npc;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.fzt.Assets;

public class TestMob extends Mob {

    public TestMob() {
        super(Assets.loadTexture64("tutel.png"),
                new HitBox(BoundingShape.box(64, 64)),
                64 * 8);
    }
}
