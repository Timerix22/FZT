package org.fzt.entities.items;

import org.fzt.entities.CharacterStats;
import org.jetbrains.annotations.NotNull;

public interface Item {
    @NotNull
    CharacterStats getStats();
}
