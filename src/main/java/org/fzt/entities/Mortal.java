package org.fzt.entities;

public interface Mortal {
    /**
     * @return maximum amount of HP
     */
    float getMaxHP();

    /**
     * @return entity health points
     */
    float getHP();

    /**
     * Changes entity health points. Kills the entity if hp becomes < 0.
     *
     * @param damage amount of damage. Set negative to heal.
     * @return result entity health points
     */
    float dealDamage(float damage);

    /**
     * Kills the entity
     */
    void kill();
}
