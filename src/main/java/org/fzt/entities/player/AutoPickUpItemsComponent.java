package org.fzt.entities.player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import org.fzt.entities.items.Item;

public class AutoPickUpItemsComponent extends Component {
    final Player _player;

    public AutoPickUpItemsComponent(Player player) {
        _player = player;
    }

    @Override
    public void onUpdate(double tpf) {
        tryPickUpItems();
    }

    private void tryPickUpItems() {
        Point2D pos = _player.getPosition();
        var nearestEntities = FXGL.getGameScene().getGameWorld()
                .getEntitiesInRange(
                        new Rectangle2D(pos.getX() - 32, // corner_x
                                pos.getY() - 32, // corner_y
                                128, // width
                                128)); // height
        for (final Entity e : nearestEntities) {
            if (e instanceof Item item) {
                try {
                    _player.pickUpItem(item);
                } catch (PlayerInventory.InventoryFullException ex) {
                    System.out.println("can't pick up an item, inventory is full!");
                }
            }
        }
    }
}
