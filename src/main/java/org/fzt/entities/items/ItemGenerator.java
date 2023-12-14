package org.fzt.entities.items;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import org.fzt.entities.CharacterStats;

import java.util.Random;

public class ItemGenerator {
    private final Random rng = new Random();
    static final String[] item_textures = {
            "artifact0.png",
            "artifact1.png",
            "artifact2.png",
            "artifact3.png",
    };

    private String getRandomTextureName(){
        return item_textures[rng.nextInt(0, item_textures.length)];
    }

    private  CharacterStats getRandomStats(){
        return new CharacterStats(
            rng.nextInt(0, 25),
            rng.nextInt(0, 25),
            rng.nextInt(0, 25),
            rng.nextInt(0, 25));
    }

    public Item dropRandomItem(Point2D position) {
        var it = new Item(getRandomTextureName(), getRandomStats());
        it.setPosition(position);
        it.setRotation(rng.nextDouble(0, 360));
        FXGL.getGameWorld().addEntity(it);
        return it;
    }
}
