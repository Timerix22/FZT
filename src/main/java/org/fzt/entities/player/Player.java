package org.fzt.entities.player;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.SensorCollisionHandler;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;
import org.fzt.Assets;
import org.fzt.entities.CharacterEntity;
import org.fzt.entities.CharacterStats;
import org.fzt.entities.EntityType;
import org.fzt.entities.items.Item;
import org.fzt.entities.items.weapons.DefaultWeapon;
import org.fzt.entities.items.weapons.Weapon;
import org.fzt.entities.npc.Mob;
import org.fzt.entities.physics.Physical;
import org.fzt.entities.physics.PhysicsComponentBuilder;
import org.jetbrains.annotations.Nullable;

public class Player extends CharacterEntity implements Physical {
    private final PhysicsComponent _physics = createPhysics();

    @Nullable
    public Weapon equippedWeapon = new DefaultWeapon("weapon-default.png", new CharacterStats());

    public PlayerInventory inventory = new PlayerInventory(16);

    private double _agroRadius = 64 * 16;

    public Player() {
        super(new CharacterStats(1, 1, 1, 0));
        setType(EntityType.PLAYER);
        getViewComponent().addChild(Assets.loadTexture64("player_stand.png"));
        // hitbox smaller than tile size to be able to go through 64px holes
        getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(56, 56)));
        addComponent(new CollidableComponent(true));
        addComponent(_physics);
        addComponent(new PlayerController(this, _physics));

        var thisPlayer = this;
        // agro collider
        _physics.addSensor(new HitBox(BoundingShape.Companion.circle(_agroRadius)), new SensorCollisionHandler() {
            @Override
            protected void onCollisionBegin(Entity other) {
                if (other.getType().toString().equals(EntityType.NPC.toString())) {
                    var mob = (Mob) other;
                    System.out.println("Player enters agro radius");
                    mob.getAI().follow(thisPlayer, other.getWidth() / 2, _agroRadius);
                }
            }
        });
    }

    public PhysicsComponent createPhysics() {
        return new PhysicsComponentBuilder()
                .fixedRotation()
                .type(BodyType.DYNAMIC)
                .wallFriction(0)
                .density(40f)
                .build();
    }

    @Override
    public PhysicsComponent getPhysics() {
        return _physics;
    }

    @Override
    public CharacterStats getStats() {
        // when getStats() is called during super class initialization, Player fields are all null
        if (inventory == null)
            return CharacterStats.sum(getBaseStats());

        // equippedWeapon is null when no weapon is equipped
        if(equippedWeapon == null)
            return CharacterStats.sum(getBaseStats(), inventory.getStatsSum());

        return CharacterStats.sum(getBaseStats(), equippedWeapon.getStats(), inventory.getStatsSum());
    }

    @Override
    public float dealDamage(float damage) {
        var hp = super.dealDamage(damage);
        System.out.println("player receives "+damage+" damage");
        return hp;
    }

    @Override
    public void kill() {
        super.kill();
        // TODO game over
    }

    /**
     * @return point where player projectiles should spawn
     */
    public Point2D getWeaponPos() {
        return getCenter();
    }

    public void pickUpItem(Item it) throws PlayerInventory.InventoryFullException {
        inventory.add(it);
        it.removeFromWorld();
        it.setRotation(0);
    }
}
