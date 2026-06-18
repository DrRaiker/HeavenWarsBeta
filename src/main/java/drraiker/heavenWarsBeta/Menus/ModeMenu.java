package drraiker.heavenWarsBeta.Menus;

import drraiker.heavenWarsBeta.Items.MenuItems;
import drraiker.heavenWarsBeta.Items.ShopItems;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.MenuHolder;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ModeMenu {
   public static void openInventory(Player player) {
      Inventory inv = Bukkit.createInventory(new MenuHolder("modeMenu"), 36, Util.stringToComponent(Configs.config.getString("mode-menu.name")));

      inv.setItem(9, MenuItems.classic);
      inv.setItem(10, MenuItems.classic);
      inv.setItem(11, MenuItems.classic);
      inv.setItem(12, MenuItems.classic);
      inv.setItem(18, MenuItems.classic);
      inv.setItem(19, MenuItems.classic);
      inv.setItem(20, MenuItems.classic);
      inv.setItem(21, MenuItems.classic);

      inv.setItem(14, MenuItems.beta);
      inv.setItem(15, MenuItems.beta);
      inv.setItem(16, MenuItems.beta);
      inv.setItem(17, MenuItems.beta);
      inv.setItem(23, MenuItems.beta);
      inv.setItem(24, MenuItems.beta);
      inv.setItem(25, MenuItems.beta);
      inv.setItem(26, MenuItems.beta);

      player.openInventory(inv);
   }
}
