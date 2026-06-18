package drraiker.heavenWarsBeta.Listeners;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.TeamsManager;
import drraiker.heavenWarsBeta.Menus.TeamChooseMenu;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.MenuHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class ColorChooseMenuListener implements Listener {
   @EventHandler
   private void PlayerClickInventory(InventoryClickEvent e) {
      Player player = (Player)e.getWhoClicked();
      if (e.getView().getTopInventory().getHolder() instanceof MenuHolder) {
         MenuHolder menuHolder = (MenuHolder)e.getView().getTopInventory().getHolder();
         if (menuHolder.getId().equals("colorChooseMenu")) {
            if (e.getClickedInventory() != null) {
               if (!e.getClickedInventory().getHolder().equals(e.getView().getTopInventory().getHolder())) {
                  e.setCancelled(true);
               } else {
                  ItemStack clickedItem = e.getCurrentItem();
                  if (clickedItem == null) {
                     e.setCancelled(true);
                  } else {
                     boolean isColor = (Boolean)NBT.get(clickedItem, (nbt) -> {
                        return nbt.getBoolean("isColor");
                     });
                     if (isColor) {
                        String color = (String)NBT.get(clickedItem, (nbt) -> {
                           return nbt.getString("color");
                        });
                        String name = (String)NBT.get(clickedItem, (nbt) -> {
                           return nbt.getString("name");
                        });
                        String material = (String)NBT.get(clickedItem, (nbt) -> {
                           return nbt.getString("block");
                        });
                        Configs.data.set("colors." + player.getName() + ".name", name);
                        Configs.data.set("colors." + player.getName() + ".material", material);
                        Configs.data.set("colors." + player.getName() + ".color", color);
                        Configs.saveData();

                        GameManager gameManager = GameManager.getGameManagerByLobby(player.getWorld());
                        TeamsManager teamsManager = gameManager.getTeamsManager();

                        if (teamsManager.team1Scores.containsKey(player)) {
                           teamsManager.setTeam1Name(name);
                           teamsManager.setTeam1Color(color);
                           teamsManager.setTeam1Material(Material.valueOf(material.toUpperCase()));
                        } else if (teamsManager.team2Scores.containsKey(player)) {
                           teamsManager.setTeam2Name(name);
                           teamsManager.setTeam2Color(color);
                           teamsManager.setTeam2Material(Material.valueOf(material.toUpperCase()));
                        } else if (teamsManager.team3Scores.containsKey(player)) {
                           teamsManager.setTeam3Name(name);
                           teamsManager.setTeam3Color(color);
                           teamsManager.setTeam3Material(Material.valueOf(material.toUpperCase()));
                        } else if (teamsManager.team4Scores.containsKey(player)) {
                           teamsManager.setTeam4Name(name);
                           teamsManager.setTeam4Color(color);
                           teamsManager.setTeam4Material(Material.valueOf(material.toUpperCase()));
                        }

                        TeamChooseMenu.openInventory(player);
                        e.setCancelled(true);
                     }


                     boolean isWhite = (Boolean)NBT.get(clickedItem, (nbt) -> {
                        return nbt.getBoolean("isWhite");
                     });
                     if (isWhite) {

                        Configs.data.set("colors." + player.getName() + ".name", "Белая");
                        Configs.data.set("colors." + player.getName() + ".material", "light_gray_wool");
                        Configs.data.set("colors." + player.getName() + ".color", "&f");
                        Configs.saveData();
                        TeamChooseMenu.openInventory(player);
                        e.setCancelled(true);
                     }

                     e.setCancelled(true);

                  }
               }
            }
         }
      }
   }

   @EventHandler
   private void dragItems(InventoryDragEvent e) {
      if (e.getInventory().getHolder() instanceof MenuHolder) {
         MenuHolder menuHolder = (MenuHolder)e.getInventory().getHolder();
         if (menuHolder.getId().equals("colorChooseMenu")) {
            e.setCancelled(true);
         }
      }
   }
}
