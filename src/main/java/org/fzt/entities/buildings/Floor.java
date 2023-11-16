package org.fzt.entities.buildings;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.ViewComponent;
import javafx.scene.Node;

public class Floor extends Entity {
    public Floor(Node view){
        ViewComponent v = getViewComponent();
        v.addChild(view);
        v.setZIndex(-99999);
    }
}
