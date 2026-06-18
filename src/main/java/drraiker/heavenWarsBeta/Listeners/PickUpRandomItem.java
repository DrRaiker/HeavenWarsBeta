package drraiker.heavenWarsBeta.Listeners;

import drraiker.heavenWarsBeta.timers.MainTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class PickUpRandomItem implements Listener {

    @EventHandler
    private void pickUp(EntityPickupItemEvent e) {

        ItemStack itemStack = e.getItem().getItemStack();

        ItemMeta meta = itemStack.getItemMeta();

        PersistentDataContainer data = meta.getPersistentDataContainer();

        if(data.has(MainTimer.key)) {
            data.remove(MainTimer.key);
        }

        itemStack.setItemMeta(meta);

    }

}
