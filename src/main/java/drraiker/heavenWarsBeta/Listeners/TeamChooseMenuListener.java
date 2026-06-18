package drraiker.heavenWarsBeta.Listeners;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.TeamsManager;
import drraiker.heavenWarsBeta.Menus.ColorChooseMenu;
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

public class TeamChooseMenuListener implements Listener {
   @EventHandler
   private void PlayerClickInventory(InventoryClickEvent e) {
      Player player = (Player)e.getWhoClicked();
      if (e.getView().getTopInventory().getHolder() instanceof MenuHolder) {
         MenuHolder menuHolder = (MenuHolder)e.getView().getTopInventory().getHolder();
         if (menuHolder.getId().equals("teamChooseMenu")) {
            if (e.getClickedInventory() != null) {
               if (!e.getClickedInventory().getHolder().equals(e.getView().getTopInventory().getHolder())) {
                  e.setCancelled(true);
               } else {
                  ItemStack clickedItem = e.getCurrentItem();
                  if (clickedItem == null) {
                     e.setCancelled(true);
                  } else {
                     boolean isTeam = (Boolean)NBT.get(clickedItem, (nbt) -> {
                        return nbt.getBoolean("isTeam");
                     });
                     if (isTeam) {
                        String team = (String)NBT.get(clickedItem, (nbt) -> {
                           return nbt.getString("team");
                        });
                        GameManager gameManager = GameManager.getGameManagerByLobby(player.getWorld());
                        if (gameManager != null) {
                           TeamsManager teamsManager = gameManager.getTeamsManager();

                           String teamS = "";

                           if (teamsManager.team1Scores.containsKey(player)) {
                              teamS = "team1";
                           } else if (teamsManager.team2Scores.containsKey(player)) {
                              teamS = "team2";
                           } else if (teamsManager.team3Scores.containsKey(player)) {
                              teamS = "team3";
                           } else if (teamsManager.team4Scores.containsKey(player)) {
                              teamS = "team4";
                           }

                           teamsManager.resetPlayerTeam(player);

                           if (!teamS.isEmpty()) {
                              teamsManager.updateColor(teamS);
                           }

                           if (team.equals("team1")) {
                              teamsManager.team1Scores.put(player, 0);
                           } else if (team.equals("team2")) {
                              teamsManager.team2Scores.put(player, 0);
                           } else if (team.equals("team3")) {
                              teamsManager.team3Scores.put(player, 0);
                           } else if (team.equals("team4")) {
                              teamsManager.team4Scores.put(player, 0);
                           }


                           String name = Configs.data.getString("colors." + player.getName() + ".name");
                           if (name.equals("Белая")) {
                              if (team.equals("team1")) {
                                 if (teamsManager.getTeam1Name().equals("Первая")) {
                                    teamsManager.setRandomColor(team);
                                 }
                              } else if (team.equals("team2")) {
                                 if (teamsManager.getTeam2Name().equals("Вторая")) {
                                    teamsManager.setRandomColor(team);
                                 }
                              } else if (team.equals("team3")) {
                                 if (teamsManager.getTeam3Name().equals("Третья")) {
                                    teamsManager.setRandomColor(team);
                                 }
                              } else if (team.equals("team4")) {
                                 if (teamsManager.getTeam4Name().equals("Четвёртая")) {
                                    teamsManager.setRandomColor(team);
                                 }
                              }
                           } else {
                              String color = Configs.data.getString("colors." + player.getName() + ".color");
                              String material = Configs.data.getString("colors." + player.getName() + ".material").toUpperCase();

                              if (teamsManager.isColorAvailable(name, team)) {

                                 if (team.equals("team1")) {
                                    if (teamsManager.getTeam1Name().equals("Первая")) {
                                       teamsManager.setTeam1Name(name);
                                       teamsManager.setTeam1Color(color);
                                       teamsManager.setTeam1Material(Material.valueOf(material));
                                    }
                                 } else if (team.equals("team2")) {
                                    if (teamsManager.getTeam2Name().equals("Вторая")) {
                                       teamsManager.setTeam2Name(name);
                                       teamsManager.setTeam2Color(color);
                                       teamsManager.setTeam2Material(Material.valueOf(material));
                                    }
                                 } else if (team.equals("team3")) {
                                    if (teamsManager.getTeam3Name().equals("Третья")) {
                                       teamsManager.setTeam3Name(name);
                                       teamsManager.setTeam3Color(color);
                                       teamsManager.setTeam3Material(Material.valueOf(material));
                                    }
                                 } else if (team.equals("team4")) {
                                    if (teamsManager.getTeam4Name().equals("Четвёртая")) {
                                       teamsManager.setTeam4Name(name);
                                       teamsManager.setTeam4Color(color);
                                       teamsManager.setTeam4Material(Material.valueOf(material));
                                    }
                                 }

                              } else {
                                 if (team.equals("team1")) {
                                    if (teamsManager.getTeam1Name().equals("Первая")) {
                                       teamsManager.setRandomColor(team);
                                    }
                                 } else if (team.equals("team2")) {
                                    if (teamsManager.getTeam2Name().equals("Вторая")) {
                                       teamsManager.setRandomColor(team);
                                    }
                                 } else if (team.equals("team3")) {
                                    if (teamsManager.getTeam3Name().equals("Третья")) {
                                       teamsManager.setRandomColor(team);
                                    }
                                 } else if (team.equals("team4")) {
                                    if (teamsManager.getTeam4Name().equals("Четвёртая")) {
                                       teamsManager.setRandomColor(team);
                                    }
                                 }
                              }
                           }

                           TeamChooseMenu.openInventory(player);
                           e.setCancelled(true);
                        }
                     } else {
                        boolean isColor = (Boolean)NBT.get(clickedItem, (nbt) -> {
                           return nbt.getBoolean("isColor");
                        });
                        if (isColor) {
                           ColorChooseMenu.openInventory(player);
                           e.setCancelled(true);
                        } else {
                           e.setCancelled(true);
                        }
                     }
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
         if (menuHolder.getId().equals("teamChooseMenu")) {
            e.setCancelled(true);
         }
      }
   }
}
