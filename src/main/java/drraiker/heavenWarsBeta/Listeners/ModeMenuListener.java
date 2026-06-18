package drraiker.heavenWarsBeta.Listeners;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Items.ShopItems;
import drraiker.heavenWarsBeta.Managers.ChatManager;
import drraiker.heavenWarsBeta.Menus.ChooseWorldMenu;
import drraiker.heavenWarsBeta.Util.MenuHolder;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class ModeMenuListener implements Listener {

    @EventHandler
    private void PlayerClickInventory(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (!(e.getView().getTopInventory().getHolder() instanceof MenuHolder)) return;
        MenuHolder menuHolder = (MenuHolder) e.getView().getTopInventory().getHolder();

        if (!menuHolder.getId().equals("modeMenu")) return;


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

        boolean isClassic = NBT.get(clickedItem, nbt -> (boolean) nbt.getBoolean("isClassic"));

        if (isClassic) {

            Location location = new Location(Bukkit.getWorld("HeavenWars"),0, 50, 0);
            player.teleport(location);
            player.closeInventory();
        }

        boolean isBeta = NBT.get(clickedItem, nbt -> (boolean) nbt.getBoolean("isBeta"));

        if (isBeta) {
            ChooseWorldMenu.openInventory(player);
        }


        e.setCancelled(true);

    }


    @EventHandler
    private void dragItems(InventoryDragEvent e) {
        if (!(e.getInventory().getHolder() instanceof MenuHolder)) return;
        MenuHolder menuHolder = (MenuHolder) e.getInventory().getHolder();

        if (!menuHolder.getId().equals("modeMenu")) return;
        e.setCancelled(true);
    }

}
