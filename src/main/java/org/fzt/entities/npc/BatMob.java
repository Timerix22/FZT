package org.fzt.entities.npc;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import org.fzt.Assets;
import org.fzt.entities.CharacterStats;

public class BatMob extends Mob {
    public BatMob() {
        super(new CharacterStats(),
            Assets.loadTexture64("bat_variant0.png"),
            new HitBox(BoundingShape.box(64, 64)));
    }
}
