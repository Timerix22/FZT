package org.fzt.entities.npc;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.fzt.Assets;
import org.fzt.entities.CharacterStats;

public class SlimeMob extends Mob {
    public SlimeMob() {
        super(new CharacterStats(),
            Assets.loadTexture64("slime.png"),
            new HitBox(BoundingShape.box(64, 64)));
    }
}
