package org.fzt.entities;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import org.fzt.Assets;
import org.fzt.entities.buildings.Floor;
import org.fzt.entities.buildings.Wall;
import org.fzt.entities.player.Player;

/**
 * Contains functions to deal with entities
 */
public class Entities {

    public static <TEntity extends Entity> TEntity spawnEntity(Point2D pos, TEntity e) {
        e.setPosition(pos);
        FXGL.getGameWorld().addEntity(e);
        return e;
    }

    public static Entity spawnPlayer(Point2D pos){
        return spawnEntity(pos, new Player());
    }

    public static Entity spawnWall(Point2D pos, String texture){
        return spawnEntity(pos, new Wall(Assets.loadTexture64(texture)));
    }

    public static Entity spawnFloor(Point2D pos, String texture) {
        return spawnEntity(pos, new Floor(Assets.loadTexture(texture, 512, 512)));
    }
}
