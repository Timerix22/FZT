package org.fzt.entities.npc;

import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import org.fzt.Assets;
import org.fzt.entities.*;

public abstract class Mob extends MortalEntity implements NPC, Physical {
    public Mob(String texture, HitBox hitBox) {
        super();
        setType(EntityType.NPC);
        addComponent(getAI());
        addComponent(new CollidableComponent(true));
        addComponent(_physics);
        getViewComponent().addChild(Assets.loadTexture64(texture));
        getBoundingBoxComponent().addHitBox(hitBox);
    }

    final PhysicsComponent _physics = createPhysics();

    @Override
    public PhysicsComponent createPhysics(){
        var ph = new PhysicsComponent();
        var bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        ph.setBodyDef(bd);
        return ph;
    }

    @Override
    public PhysicsComponent getPhysics() { return _physics; }
}
