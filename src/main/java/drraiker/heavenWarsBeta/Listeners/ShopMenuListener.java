package drraiker.heavenWarsBeta.Listeners;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Items.Items;
import drraiker.heavenWarsBeta.Items.ShopItems;
import drraiker.heavenWarsBeta.Managers.ChatManager;
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

public class ShopMenuListener implements Listener {

    @EventHandler
    private void PlayerClickInventory(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (!(e.getView().getTopInventory().getHolder() instanceof MenuHolder)) return;
        MenuHolder menuHolder = (MenuHolder) e.getView().getTopInventory().getHolder();

        if (!menuHolder.getId().equals("shopMenu")) return;


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

        boolean isImpulse = NBT.get(clickedItem, nbt -> (boolean) nbt.getBoolean("isImpulse"));

        if (isImpulse) {

            if (!Util.hasItem(player, Material.WHITE_WOOL, 5)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eблоков&c для покупки")));
                e.setCancelled(true);
                return;
            }
            if (!Util.hasItem(player, Material.TNT, 5)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eдинамита&c для покупки")));
                e.setCancelled(true);
                return;
            }

            Util.removeItem(player, Material.WHITE_WOOL, 5);
            Util.removeItem(player, Material.TNT, 5);

            player.getWorld().dropItem(player.getLocation(), Items.ImpulseGrenade).setPickupDelay(0);
        }
        boolean isMine = NBT.get(clickedItem, nbt -> (boolean) nbt.getBoolean("isMine"));

        if (isMine) {

            if (!Util.hasItem(player, Material.WHITE_WOOL, 2)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eблоков&c для покупки")));
                e.setCancelled(true);
                return;
            }
            if (!Util.hasItem(player, Material.TNT, 2)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eдинамита&c для покупки")));
                e.setCancelled(true);
                return;
            }

            Util.removeItem(player, Material.WHITE_WOOL, 2);
            Util.removeItem(player, Material.TNT, 2);

            player.getWorld().dropItem(player.getLocation(), Items.Mine).setPickupDelay(0);
        }

        boolean isFlameMine = NBT.get(clickedItem, nbt -> (boolean) nbt.getBoolean("isFlameMine"));

        if (isFlameMine) {

            if (!Util.hasItem(player, Material.WHITE_WOOL, 3)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eблоков&c для покупки")));
                e.setCancelled(true);
                return;
            }
            if (!Util.hasItem(player, Material.TNT, 3)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eдинамита&c для покупки")));
                e.setCancelled(true);
                return;
            }

            Util.removeItem(player, Material.WHITE_WOOL, 3);
            Util.removeItem(player, Material.TNT, 3);

            player.getWorld().dropItem(player.getLocation(), Items.FlameMine).setPickupDelay(0);
        }
        boolean isWall = NBT.get(clickedItem, nbt -> (boolean) nbt.getBoolean("isWall"));

        if (isWall) {

            if (!Util.hasItem(player, Material.WHITE_WOOL, 6)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eблоков&c для покупки")));
                e.setCancelled(true);
                return;
            }
            if (!Util.hasItem(player, Material.TNT, 1)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eдинамита&c для покупки")));
                e.setCancelled(true);
                return;
            }

            Util.removeItem(player, Material.WHITE_WOOL, 6);
            Util.removeItem(player, Material.TNT, 1);

            player.getWorld().dropItem(player.getLocation(), Items.wall).setPickupDelay(0);
        }

        boolean isFloor = NBT.get(clickedItem, nbt -> (boolean) nbt.getBoolean("isFloor"));

        if (isFloor) {

            if (!Util.hasItem(player, Material.WHITE_WOOL, 4)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eблоков&c для покупки")));
                e.setCancelled(true);
                return;
            }
            if (!Util.hasItem(player, Material.TNT, 1)) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eдинамита&c для покупки")));
                e.setCancelled(true);
                return;
            }

            Util.removeItem(player, Material.WHITE_WOOL, 4);
            Util.removeItem(player, Material.TNT, 1);

            player.getWorld().dropItem(player.getLocation(), Items.floor).setPickupDelay(0);
        }





        e.setCancelled(true);

    }


    @EventHandler
    private void dragItems(InventoryDragEvent e) {
        if (!(e.getInventory().getHolder() instanceof MenuHolder)) return;
        MenuHolder menuHolder = (MenuHolder) e.getInventory().getHolder();

        if (!menuHolder.getId().equals("shopMenu")) return;
        e.setCancelled(true);
    }

}
