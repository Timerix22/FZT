package org.fzt.level;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import java.util.Random;
import java.util.function.Function;

public class SpawnerComponent extends Component {
    float spawnCooldown;
    int spawnRadius;
    int spawnAmount;
    Function<Point2D, Entity> spawnFunc;
    Random rng = new Random();

    public SpawnerComponent(float spawnCooldown, int spawnRadius, int spawnAmount, Function<Point2D, Entity> spawnFunc) {
        this.spawnCooldown = spawnCooldown;
        this.spawnRadius = spawnRadius;
        this.spawnAmount = spawnAmount;
        this.spawnFunc = spawnFunc;
    }

    double timeSinceSpawn = 0;
    @Override
    public void onUpdate(double tpf) {
        if(timeSinceSpawn > spawnCooldown) {
            Spawn();
            timeSinceSpawn = 0;
        }
        timeSinceSpawn+=tpf;
    }

    public void Spawn(){
        for(int i=0; i < spawnAmount; i++) {
            var randomShift = new Point2D(rng.nextDouble(-spawnRadius, spawnRadius), rng.nextDouble(-spawnRadius, spawnRadius));
            var randomPoint = entity.getCenter().add(randomShift);
            spawnFunc.apply(randomPoint);
        }
    }
}
