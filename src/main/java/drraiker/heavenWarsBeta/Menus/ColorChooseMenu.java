package drraiker.heavenWarsBeta.Menus;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Items.MenuItems;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.TeamsManager;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.MenuHolder;
import drraiker.heavenWarsBeta.Util.Util;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorChooseMenu {
   public static void openInventory(Player player) {
      Inventory inv = Bukkit.createInventory(new MenuHolder("colorChooseMenu"), 36, Util.stringToComponent(Configs.config.getString("colors-menu.name")));
      GameManager gameManager = GameManager.getGameManagerByLobby(player.getWorld());
      String playerName = player.getName();

      int i;
      for(i = 0; i < 9; ++i) {
         inv.setItem(i, MenuItems.holder);
      }

      for(i = 27; i < 36; ++i) {
         inv.setItem(i, MenuItems.holder);
      }

      inv.setItem(9, MenuItems.holder);
      inv.setItem(17, MenuItems.holder);
      inv.setItem(18, MenuItems.holder);
      inv.setItem(26, MenuItems.holder);
      inv.addItem(createColor(playerName, "&#f2e854&l", "Жёлтая", Material.YELLOW_WOOL));
      inv.addItem(createColor(playerName, "&#f9a84d&l", "Оранжевая", Material.ORANGE_WOOL));
      inv.addItem(createColor(playerName, "&#be403e&l", "Красная", Material.RED_WOOL));
      inv.addItem(createColor(playerName, "&#f5a6b5&l", "Розовая", Material.PINK_WOOL));
      inv.addItem(createColor(playerName, "&#d27dd4&l", "Пурпурная", Material.MAGENTA_WOOL));
      inv.addItem(createColor(playerName, "&#8a43b1&l", "Фиолетовая", Material.PURPLE_WOOL));
      inv.addItem(createColor(playerName, "&#295bbd&l", "Синяя", Material.BLUE_WOOL));
      inv.addItem(createColor(playerName, "&#3bb2c9&l", "Бирюзовая", Material.CYAN_WOOL));
      inv.addItem(createColor(playerName, "&#7dc3d2&l", "Голубая", Material.LIGHT_BLUE_WOOL));

      inv.addItem(createColor(playerName, "&#9ed74a&l", "Лаймовая", Material.LIME_WOOL));
      inv.addItem(createColor(playerName, "&#4a8337&l", "Зелёная", Material.GREEN_WOOL));

      inv.addItem(createColor(playerName, "&#7b4a27&l", "Коричневая", Material.BROWN_WOOL));
      inv.addItem(createColor(playerName, "&#7a7a7a&l", "Серая", Material.GRAY_WOOL));
      inv.addItem(createColor(playerName, "&#1d1d1d&l", "Чёрная", Material.BLACK_WOOL));



      ItemStack item = new ItemStack(Material.WHITE_WOOL);
      NBT.modify(item, (nbt) -> {
         nbt.setBoolean("isWhite", true);
      });
      ItemMeta itemMeta = item.getItemMeta();
      itemMeta.displayName(Util.stringToComponent("&f&lСлучайный цвет"));
      List<Component> lore = new ArrayList();
      lore.add(Util.stringToComponent(""));
      if (Configs.data.getString("colors." + playerName + ".name").equals("Белая")) {
         lore.add(Util.stringToComponent("&7 -&f Цвет выбран"));
      } else {
         lore.add(Util.stringToComponent("&7 -&b Выбрать цвет"));
      }

      lore.add(Util.stringToComponent(""));
      itemMeta.lore(lore);
      item.setItemMeta(itemMeta);

      inv.setItem(31, item);

      player.openInventory(inv);
   }

   private static ItemStack createColor(String playerName, String color, String name, Material material) {
      ItemStack item = new ItemStack(material);
      NBT.modify(item, (nbt) -> {
         nbt.setBoolean("isColor", true);
         nbt.setString("color", color);
         nbt.setString("name", name);
         nbt.setString("block", material.toString());
      });
      ItemMeta itemMeta = item.getItemMeta();
      itemMeta.displayName(Util.stringToComponent(color + name));
      List<Component> lore = new ArrayList();
      lore.add(Util.stringToComponent(""));
      if (Configs.data.getString("colors." + playerName + ".name").equals(name)) {
         lore.add(Util.stringToComponent("&7 -&f Цвет выбран"));
      } else {
         lore.add(Util.stringToComponent("&7 -&b Выбрать цвет"));
      }

      lore.add(Util.stringToComponent(""));
      itemMeta.lore(lore);
      item.setItemMeta(itemMeta);
      return item;
   }
}
