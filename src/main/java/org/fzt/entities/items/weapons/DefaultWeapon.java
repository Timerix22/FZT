package org.fzt.entities.items.weapons;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import org.fzt.Assets;
import org.fzt.entities.CharacterStats;
import org.fzt.entities.Entities;

public class DefaultWeapon extends Weapon {

    // it will not work if < then 1/fps
    public float cooldown = 0.2f;

    long lastAttackTime = 0;

    public DefaultWeapon(String textureName, CharacterStats stats) {
        super(textureName, stats);
    }

    @Override
    public void attack(Point2D pos, Point2D destination) {
        long now = System.nanoTime();
        // just returns if cooldown hasn't ended
        if(now < lastAttackTime + (long) (cooldown*1000_000_000))
            return;

        lastAttackTime = now;

        var projectileTexture = Assets.loadTexture("default_projectile.png", 32, 32);
        var destNorm = destination.normalize();
        var angle = Math.atan2(destNorm.getX(), destNorm.getY()*-1);
        projectileTexture.setRotate(angle/Math.PI*180);
        var projectile = new Projectile(
                projectileTexture,
                new HitBox(BoundingShape.circle(20f))){
            @Override
            public float getBaseDamage() {return 10;}
        };
        Entities.spawnEntity(pos, projectile);
        projectile.shoot(destNorm.multiply(10.0*64), 5);
    }
}
