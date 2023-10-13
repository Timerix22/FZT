package org.fzt.entities.items.weapons;

import javafx.geometry.Point2D;
import org.fzt.entities.items.Item;

public interface Weapon extends Item {
    void attack(Point2D pos, Point2D destination);
}
