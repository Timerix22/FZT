package org.fzt.level;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import org.fzt.entities.Entities;

import java.util.function.Supplier;

public class LevelBuilder {
    public void lineX(Supplier<Entity> entitySupplier, double x, double y, int length){
        for (int i = 0; i < length; i++) {
            var e = Entities.spawnEntity(new Point2D(x, y), entitySupplier.get());
            x += e.getWidth();
        }
    }

    public void lineY(Supplier<Entity> entitySupplier, double x, double y, int length){
        for (int i = 0; i < length; i++) {
            var e = Entities.spawnEntity(new Point2D(x, y), entitySupplier.get());
            y += e.getHeight();
        }
    }
}
