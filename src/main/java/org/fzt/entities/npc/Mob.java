package org.fzt.entities.npc;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.Node;
import org.fzt.GameApp;
import org.fzt.entities.CharacterEntity;
import org.fzt.entities.CharacterStats;
import org.fzt.entities.EntityType;
import org.fzt.entities.Mortal;
import org.fzt.entities.physics.Physical;
import org.fzt.entities.physics.PhysicsComponentBuilder;
import org.fzt.entities.physics.SurfaceFriction;

import java.util.Random;

public abstract class Mob extends CharacterEntity implements NPC, Physical {
    protected final PhysicsComponent _physics = createPhysics();
    AIComponent _ai;

    public Mob(CharacterStats baseStats,Node view, HitBox hitBox) {
        super(baseStats);
        setType(EntityType.NPC);
        addComponent(getAI());
        addComponent(new CollidableComponent(true));
        getViewComponent().addChild(view);
        getBoundingBoxComponent().addHitBox(hitBox);
        addComponent(new SurfaceFriction(_physics));
        addComponent(_physics);
    }

    @Override
    public PhysicsComponent createPhysics() {
        return new PhysicsComponentBuilder()
                .fixedRotation()
                .type(BodyType.DYNAMIC)
                .density(50)
                .wallFriction(0)
                .build();
    }

    @Override
    public PhysicsComponent getPhysics() {
        return _physics;
    }

    @Override
    public AIComponent getAI() {
        if (_ai == null)
            _ai = new MobAIComponent(this);
        return _ai;
    }

    // it will not work if < then 1/fps
    public float cooldown = 0.4f;

    long lastAttackTime = 0;
    public void attack(Entity target) {
        long now = System.nanoTime();
        // just returns if cooldown hasn't ended
        if(now < lastAttackTime + (long) (cooldown*1000_000_000))
            return;

        lastAttackTime = now;
        if(target instanceof Mortal targetMortal && target.getCenter().subtract(getCenter()).magnitude() < 96)
            targetMortal.dealDamage(getStats().getMiliDamage());
    }

    final private Random _rng = new Random();
    @Override
    public void kill() {
        if(_rng.nextInt(0, 3) == 1) {
            var item = GameApp.itemGenerator.dropRandomItem(getPosition());
            System.out.println("mob dropped item " + item);
        }
        super.kill();
    }
}
