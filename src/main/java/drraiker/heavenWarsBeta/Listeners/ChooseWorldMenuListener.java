package drraiker.heavenWarsBeta.Listeners;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Util.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class ChooseWorldMenuListener implements Listener {

    @EventHandler
    private void PlayerClickInventory(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (!(e.getView().getTopInventory().getHolder() instanceof MenuHolder)) return;
        MenuHolder menuHolder = (MenuHolder) e.getView().getTopInventory().getHolder();

        if (!menuHolder.getId().equals("worldsMenu")) return;


        if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().getHolder().equals(e.getView().getTopInventory().getHolder())) {
            e.setCancelled(true);
            return;
        }

        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null) {
            e.setCancelled(true);
            return;
        }

        boolean isButton = NBT.get(clickedItem, nbt -> (boolean) nbt.getBoolean("isButton"));

        if (!isButton) {
            e.setCancelled(true);
            return;
        }
        String world = NBT.get(clickedItem, nbt -> (String) nbt.getString("world"));
        double x = NBT.get(clickedItem, nbt -> (double) nbt.getDouble("x"));
        double y = NBT.get(clickedItem, nbt -> (double) nbt.getDouble("y"));
        double z = NBT.get(clickedItem, nbt -> (double) nbt.getDouble("z"));


        Location location = new Location(Bukkit.getWorld(world),x,y,z);

        player.teleport(location);

        player.closeInventory();

        e.setCancelled(true);

    }


    @EventHandler
    private void dragItems(InventoryDragEvent e) {
        if (!(e.getInventory().getHolder() instanceof MenuHolder)) return;
        MenuHolder menuHolder = (MenuHolder) e.getInventory().getHolder();

        if (!menuHolder.getId().equals("worldsMenu")) return;
        e.setCancelled(true);
    }

}
