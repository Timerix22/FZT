package org.fzt.entities;

public interface Character extends Mortal {
    /**
     * @return final stats of the character after applying all buffs, equipment and other bonuses
     */
    CharacterStats getStats();

    /**
     * @return stats of the character itself
     */
    CharacterStats getBaseStats();
}
