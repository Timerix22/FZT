package org.fzt.entities.npc;

import org.fzt.entities.Character;
import org.fzt.entities.Mortal;

public interface NPC extends Mortal, Character {
    AIComponent getAI();
}
