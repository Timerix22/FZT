package org.fzt.entities.player;

import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import org.fzt.Assets;
import org.fzt.entities.EntityType;
import org.fzt.entities.MortalEntity;
import org.fzt.entities.Physical;
import org.fzt.entities.items.weapons.DefaultWeapon;
import org.fzt.entities.items.weapons.Weapon;
import org.jetbrains.annotations.Nullable;

public class Player extends MortalEntity implements Physical {
    private final PhysicsComponent _physics = createPhysics();

    public PlayerStats base_stats = new PlayerStats(1, 1, 1, 0);

    @Nullable
    public Weapon equippedWeapon = new DefaultWeapon();

    public PlayerInventory inventory = new PlayerInventory(16);

    public Player() {
        super();
        setType(EntityType.PLAYER);
        getViewComponent().addChild(Assets.loadTexture64("player.png"));
        // hitbox smaller than tile size to be able to go through 64px holes
        getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(56, 56)));
        addComponent(new CollidableComponent(true));
        addComponent(_physics);
        addComponent(new PlayerController(this, _physics));
    }

    public PhysicsComponent createPhysics() {
        var ph = new PhysicsComponent();
        ph.setFixtureDef(new FixtureDef().friction(0).density(0.1f));
        var bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        ph.setBodyDef(bd);
        return ph;
    }

    @Override
    public PhysicsComponent getPhysics() { return _physics; }

    public PlayerStats getStats() {
        // sometimes equippedWeapon is null
        if (equippedWeapon != null) {
            return PlayerStats.sum(base_stats, equippedWeapon.getStats(), inventory.getStatsSum() );
        }
        return PlayerStats.sum(base_stats, inventory.getStatsSum() );
    }

    @Override
    public float getMaxHP(){
        // when player spawns MortalEntity initializes before base_stats and inventory,
        // so getMaxHP has to return 0 to not cause NullPointerException in getStats()
        if(base_stats == null || inventory == null)
            return 0;
        var s = getStats();
        return 100 * (s.constitution / 100 + s.strength / 400);
    }

    /**
     * @return point where player projectiles should spawn
     */
    public Point2D getWeaponPos() { return getCenter(); }
}
