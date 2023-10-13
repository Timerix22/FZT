package org.fzt.entities.items.weapons;

import com.almasb.fxgl.core.math.Vec2;
import org.fzt.entities.items.Item;

public interface Weapon extends Item {
    void attack(Vec2 destination);
}
