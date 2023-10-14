package org.fzt.entities.items.weapons;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import org.fzt.Assets;
import org.fzt.entities.Entities;
import org.fzt.entities.CharacterStats;
import org.jetbrains.annotations.NotNull;

public class DefaultWeapon implements Weapon {

    @NotNull
    @Override
    public CharacterStats getStats() {
        return new CharacterStats();
    }

    // it will not work if < then 1/fps
    public float cooldown = 0.2f;

    long lastShotTime = 0;

    @Override
    public void attack(Point2D pos, Point2D destination) {
        long now = System.nanoTime();
        // just returns if cooldown hasn't ended
        if(now < lastShotTime + (long) (cooldown*1000_000_000))
            return;

        lastShotTime = now;
        var projectile = new Projectile(
                Assets.loadTexture("default_projectile.png", 40, 40),
                new HitBox(BoundingShape.circle(20f))){
            @Override
            public float getBaseDamage() {return 10;}
        };
        Entities.spawnEntity(pos, projectile);
        projectile.shoot(destination.normalize().multiply(10*64), 5);
    }
}
