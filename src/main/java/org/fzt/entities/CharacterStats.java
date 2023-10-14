package org.fzt.entities;

import org.fzt.entities.items.Item;

import java.util.Collection;

/**
 * Characteristics of player and items
 */
public class CharacterStats {
    static final float base_speed = 5 * 64;
    static final float base_damage = 5;
    public float strength;
    public float dexterity;
    public float constitution;
    public float tutelness;

    public CharacterStats(float strength, float dexterity, float constitution, float tutelness) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.tutelness = tutelness;
    }

    public CharacterStats() {
        this(0, 0, 0, 0);
    }

    public static CharacterStats sum(CharacterStats... stats) {
        var sum = new CharacterStats();
        for (var s : stats) {
            sum.strength += s.strength;
            sum.dexterity += s.dexterity;
            sum.constitution += s.constitution;
            sum.tutelness += s.tutelness;
        }
        return sum;
    }

    public static CharacterStats sum(Collection<Item> stats) {
        var sum = new CharacterStats();
        for (var i : stats) {
            var s = i.getStats();
            sum.strength += s.strength;
            sum.dexterity += s.dexterity;
            sum.constitution += s.constitution;
            sum.tutelness += s.tutelness;
        }
        return sum;
    }

    public float getMovingSpeed() {
        return base_speed * (1 + dexterity / 100 + strength / 400 + constitution / 400);
    }

    public float getMiliDamage() {
        return base_damage * (1 + strength / 50 + dexterity / 200);
    }

    public float getRangeDamage() {
        return base_damage * (1 + dexterity / 50 + strength / 200);
    }
}
