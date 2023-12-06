package org.fzt.entities.items.weapons;

import javafx.geometry.Point2D;
import org.fzt.entities.CharacterStats;
import org.fzt.entities.items.Item;

public abstract class Weapon extends Item {
    public Weapon(String textureName, CharacterStats stats) {
        super(textureName, stats);
    }

    public abstract void attack(Point2D pos, Point2D destination);
}
