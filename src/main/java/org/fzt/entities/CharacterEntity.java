package org.fzt.entities;

public abstract class CharacterEntity extends MortalEntity implements Character {
    private final CharacterStats _base_stats;
    private final float _base_hp = 50;

    public CharacterEntity(CharacterStats baseStats) {
        _base_stats = baseStats;
        // _hp is initialized as 0 when MortalEntity calls getMaxHP(),
        // so there _hp gets value calculated from stats
        _hp = getMaxHP();
    }

    @Override
    public CharacterStats getBaseStats() {
        return _base_stats;
    }

    @Override
    public CharacterStats getStats() {
        return getBaseStats();
    }

    @Override
    public float getMaxHP() {
        // MortalEntity calls getMaxHP() in field initialization before _base_stats are initialized,
        // so getMaxHP has to return 0 to not cause NullPointerException in MortalEntity.
        if (_base_stats == null)
            return 0;
        var s = getStats();
        return _base_hp * (1 + s.constitution / 20 + s.strength / 80);
    }
}
