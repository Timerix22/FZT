package org.fzt.entities.buildings;

import com.almasb.fxgl.physics.HitBox;
import javafx.scene.Node;
import org.fzt.entities.EntityType;

public class Wall extends Building {
    public Wall(Node view, HitBox hitbox) {
        super(view, hitbox);
    }

    public Wall(Node view) {
        super(view);
        setType(EntityType.WALL);
    }
}
