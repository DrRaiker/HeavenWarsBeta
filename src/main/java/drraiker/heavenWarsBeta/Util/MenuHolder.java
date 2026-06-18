package drraiker.heavenWarsBeta.Util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class MenuHolder implements InventoryHolder {

    private final String id;

    public MenuHolder(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
