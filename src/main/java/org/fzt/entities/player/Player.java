package org.fzt.entities.player;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import org.fzt.Assets;
import org.fzt.entities.EntityType;
import org.fzt.entities.items.weapons.DefaultWeapon;
import org.fzt.entities.items.weapons.Weapon;

public class Player extends Entity {

    private PlayerStats base_stats = new PlayerStats(1, 1, 1, 0);

    public Weapon equippedWeapon = new DefaultWeapon();

    public PlayerInventory inventory = new PlayerInventory(16);

    public PlayerStats getStats() {
        return PlayerStats.sum(base_stats, equippedWeapon.getStats(), inventory.getStatsSum() );
    }


    public Player() {
        super();
        setType(EntityType.PLAYER);
        getViewComponent().addChild(Assets.loadTexture64("player.png"));
        // hitbox smaller than tile size to be able to go through 64px holes
        getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(56, 56)));
        addComponent(new CollidableComponent(true));
        var physics = createPlayerPhysics();
        addComponent(physics);
        addComponent(new PlayerController(this, physics));
    }

    static PhysicsComponent createPlayerPhysics() {
        var ph = new PhysicsComponent();
        ph.setFixtureDef(new FixtureDef().friction(0).density(0.1f));
        var bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        ph.setBodyDef(bd);
        return ph;
    }
}
