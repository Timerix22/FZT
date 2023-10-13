package org.fzt.entities.items.weapons;

import com.almasb.fxgl.core.math.Vec2;
import org.fzt.entities.player.PlayerStats;
import org.jetbrains.annotations.NotNull;

public class DefaultWeapon implements Weapon {

    @NotNull
    @Override
    public PlayerStats getStats() {
        return new PlayerStats();
    }

    @Override
    public void attack(Vec2 destination) {

    }
}
