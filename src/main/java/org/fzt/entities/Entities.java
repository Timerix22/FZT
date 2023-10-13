package org.fzt.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import org.fzt.Assets;
import org.fzt.entities.buildings.Wall;
import org.fzt.entities.player.Player;

/**
 * Contains functions to deal with entities
 */
public class Entities {
    public static Entity spawnEntity(Entity e, Point2D pos) {
        e.setPosition(pos);
        FXGL.getGameWorld().addEntity(e);
        return e;
    }
    
    public static Entity spawnPlayer(Point2D pos){
        return spawnEntity(new Player(), pos);
    }

    public static Entity spawnWall(Point2D pos, String texture){
        return spawnEntity(new Wall(Assets.loadTexture64(texture)), pos);
    }
}
