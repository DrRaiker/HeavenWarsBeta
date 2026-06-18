package drraiker.heavenWarsBeta.Menus;

import de.tr7zw.nbtapi.NBT;
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

public class TeamChooseMenu {
   public static void openInventory(Player player) {
      Inventory inv = Bukkit.createInventory(new MenuHolder("teamChooseMenu"), 36, Util.stringToComponent(Configs.config.getString("teams-menu.name")));
      GameManager gameManager = GameManager.getGameManagerByLobby(player.getWorld());
      TeamsManager teamsManager = gameManager.getTeamsManager();
      ItemStack team1 = new ItemStack(teamsManager.getTeam1Material());
      NBT.modify(team1, (nbt) -> {
         nbt.setBoolean("isTeam", true);
         nbt.setString("team", "team1");
      });
      ItemMeta team1Meta = team1.getItemMeta();
      team1Meta.displayName(Util.stringToComponent(teamsManager.getTeam1Color() + teamsManager.getTeam1Name()));
      List<Component> lore1 = new ArrayList();
      if (!teamsManager.team1Scores.isEmpty()) {
         lore1.add(Util.stringToComponent("&7В команде:"));
         teamsManager.team1Scores.keySet().forEach((p) -> {
            lore1.add(Util.stringToComponent("&7- &f" + p.getName()));
         });
      }

      lore1.add(Util.stringToComponent(" "));
      if (!teamsManager.team1Scores.containsKey(player)) {
         lore1.add(Util.stringToComponent("&f&lПрисоединиться"));
      }

      team1Meta.lore(lore1);
      team1.setItemMeta(team1Meta);
      inv.setItem(10, team1);
      ItemStack team2 = new ItemStack(teamsManager.getTeam2Material());
      NBT.modify(team2, (nbt) -> {
         nbt.setBoolean("isTeam", true);
         nbt.setString("team", "team2");
      });
      ItemMeta team2Meta = team2.getItemMeta();
      team2Meta.displayName(Util.stringToComponent(teamsManager.getTeam2Color() + teamsManager.getTeam2Name()));
      List<Component> lore2 = new ArrayList();
      if (!teamsManager.team2Scores.isEmpty()) {
         lore2.add(Util.stringToComponent("&7В команде:"));
         teamsManager.team2Scores.keySet().forEach((p) -> {
            lore2.add(Util.stringToComponent("&7- &f" + p.getName()));
         });
      }

      lore2.add(Util.stringToComponent(" "));
      if (!teamsManager.team2Scores.containsKey(player)) {
         lore2.add(Util.stringToComponent("&f&lПрисоединиться"));
      }

      team2Meta.lore(lore2);
      team2.setItemMeta(team2Meta);
      inv.setItem(12, team2);
      ItemStack team3 = new ItemStack(teamsManager.getTeam3Material());
      NBT.modify(team3, (nbt) -> {
         nbt.setBoolean("isTeam", true);
         nbt.setString("team", "team3");
      });
      ItemMeta team3Meta = team3.getItemMeta();
      team3Meta.displayName(Util.stringToComponent(teamsManager.getTeam3Color() + teamsManager.getTeam3Name()));
      List<Component> lore3 = new ArrayList();
      if (!teamsManager.team3Scores.isEmpty()) {
         lore3.add(Util.stringToComponent("&7В команде:"));
         teamsManager.team3Scores.keySet().forEach((p) -> {
            lore3.add(Util.stringToComponent("&7- &f" + p.getName()));
         });
      }

      lore3.add(Util.stringToComponent(" "));
      if (!teamsManager.team3Scores.containsKey(player)) {
         lore3.add(Util.stringToComponent("&f&lПрисоединиться"));
      }

      team3Meta.lore(lore3);
      team3.setItemMeta(team3Meta);
      inv.setItem(14, team3);
      ItemStack team4 = new ItemStack(teamsManager.getTeam4Material());
      NBT.modify(team4, (nbt) -> {
         nbt.setBoolean("isTeam", true);
         nbt.setString("team", "team4");
      });
      ItemMeta team4Meta = team4.getItemMeta();
      team4Meta.displayName(Util.stringToComponent(teamsManager.getTeam4Color() + teamsManager.getTeam4Name()));
      List<Component> lore4 = new ArrayList();
      if (!teamsManager.team4Scores.isEmpty()) {
         lore4.add(Util.stringToComponent("&7В команде:"));
         teamsManager.team4Scores.keySet().forEach((p) -> {
            lore4.add(Util.stringToComponent("&7- &f" + p.getName()));
         });
      }

      lore4.add(Util.stringToComponent(" "));
      if (!teamsManager.team4Scores.containsKey(player)) {
         lore4.add(Util.stringToComponent("&f&lПрисоединиться"));
      }

      team4Meta.lore(lore4);
      team4.setItemMeta(team4Meta);
      inv.setItem(16, team4);
      ItemStack color = new ItemStack(Material.valueOf(Configs.data.getString("colors." + player.getName() + ".material", "light_gray_wool").toUpperCase()));
      NBT.modify(color, (nbt) -> {
         nbt.setBoolean("isColor", true);
      });
      ItemMeta colorMeta = color.getItemMeta();
      colorMeta.displayName(Util.stringToComponent("Выбрать цвет команды"));
      List<Component> colorLore = new ArrayList();
      colorLore.add(Util.stringToComponent(" "));
      colorLore.add(Util.stringToComponent("&7Выбранный цвет: "));
      String var10001 = Configs.data.getString("colors." + player.getName() + ".color", "");
      String name = Configs.data.getString("colors." + player.getName() + ".name", "Цвет не выбран");

      if (name.equals("Белая")) {
         colorLore.add(Util.stringToComponent("&f&lСлучайный"));
      } else {
         colorLore.add(Util.stringToComponent(var10001 + name));
      }
      colorMeta.lore(colorLore);
      color.setItemMeta(colorMeta);
      inv.setItem(31, color);
      player.openInventory(inv);
   }
}
