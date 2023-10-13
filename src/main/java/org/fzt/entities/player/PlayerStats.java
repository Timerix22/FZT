package org.fzt.entities.player;

import org.fzt.entities.items.Item;

import java.util.Collection;

/**
 * Characteristics of player and items
 */
public class PlayerStats {
    public float strength;
    public float dexterity;
    public float constitution;
    public float tutelness;

    public PlayerStats(float strength, float dexterity, float constitution, float tutelness) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.tutelness = tutelness;
    }

    public PlayerStats() {
        this(0,0,0,0);
    }

    public static PlayerStats sum(PlayerStats ... stats){
        var sum = new PlayerStats();
        for (var s : stats){
            sum.strength += s.strength;
            sum.dexterity += s.dexterity;
            sum.constitution += s.constitution;
            sum.tutelness += s.tutelness;
        }
        return sum;
    }


    public static PlayerStats sum(Collection<Item> stats){
        var sum = new PlayerStats();
        for (var i : stats){
            var s = i.getStats();
            sum.strength += s.strength;
            sum.dexterity += s.dexterity;
            sum.constitution += s.constitution;
            sum.tutelness += s.tutelness;
        }
        return sum;
    }
}
