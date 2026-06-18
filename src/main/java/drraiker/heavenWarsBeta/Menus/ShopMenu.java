package drraiker.heavenWarsBeta.Menus;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Items.MenuItems;
import drraiker.heavenWarsBeta.Items.ShopItems;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.GameState;
import drraiker.heavenWarsBeta.Managers.ValuesManager;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.MenuHolder;
import drraiker.heavenWarsBeta.Util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShopMenu {
   public static void openInventory(Player player) {
      Inventory inv = Bukkit.createInventory(new MenuHolder("shopMenu"), 27, Util.stringToComponent(Configs.config.getString("worlds-menu.name")));

      int index;
      for(index = 0; index < 9; ++index) {
         inv.setItem(index, MenuItems.holder);
      }

      for(index = 18; index < 27; ++index) {
         inv.setItem(index, MenuItems.holder);
      }

      inv.setItem(9, MenuItems.holder);
      inv.setItem(17, MenuItems.holder);


      inv.addItem(ShopItems.Mine);
      inv.addItem(ShopItems.FlameMine);
      inv.addItem(ShopItems.ImpulseGrenade);
      inv.addItem(ShopItems.wall);
      inv.addItem(ShopItems.floor);


      player.openInventory(inv);
   }
}
