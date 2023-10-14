package org.fzt.entities.player;

import org.fzt.entities.CharacterStats;
import org.fzt.entities.items.Item;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventory {
    public final List<Item> items;
    public int capacity;

    public PlayerInventory(int capacity) {
        this.capacity = capacity;
        items = new ArrayList<>(capacity);
    }

    public CharacterStats getStatsSum() {
        return CharacterStats.sum(items);
    }

    public class InventoryFullException extends Exception {
        public InventoryFullException(){
            super("Inventory (capacity "+capacity+") is full");
        }
    }

    public void add(Item i) throws InventoryFullException {
        if(items.size() < capacity)
            items.add(i);
        else throw new InventoryFullException();
    }

    public Item get(int index) {
        return items.get(index);
    }

    public void remove(int index) {
        items.remove(index);
    }
}
