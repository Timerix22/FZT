package org.fzt.entities.items;

import org.fzt.entities.player.PlayerStats;
import org.jetbrains.annotations.NotNull;

public interface Item {
    @NotNull
    PlayerStats getStats();
}
