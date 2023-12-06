package org.fzt.entities;

import com.almasb.fxgl.entity.Entity;

public abstract class MortalEntity extends Entity implements Mortal {
    protected float _hp = getMaxHP();

    @Override
    public float getHP() {
        return _hp;
    }

    @Override
    public float dealDamage(float damage) {
        _hp -= damage;
        if(_hp < 0)
            kill();
        else {
            var maxHP = getMaxHP();
            if(_hp > maxHP)
                _hp = maxHP;
        }
        return _hp;
    }

    @Override
    public void kill(){
        removeFromWorld();
    }
}
