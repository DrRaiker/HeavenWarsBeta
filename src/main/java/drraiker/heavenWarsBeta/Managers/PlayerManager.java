package drraiker.heavenWarsBeta.Managers;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Items.Equipment;
import drraiker.heavenWarsBeta.Items.ShopItems;
import drraiker.heavenWarsBeta.Util.Util;
import io.papermc.paper.entity.LookAnchor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerManager {
    
    private final GameManager gameManager;
    private final TeamsManager teamsManager;
    private final ValuesManager valuesManager;

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.teamsManager = gameManager.getTeamsManager();
        this.valuesManager = gameManager.getValuesManager();
    }

    public void checkCount() {

        new BukkitRunnable() {
            @Override
            public void run() {

                int playerCount = gameManager.getLobby().getPlayerCount();

                if (playerCount < 1) cancel();

                if (!gameManager.getGameState().equals(GameState.LOBBY)) cancel();

                if (playerCount < 2)
                    ChatManager.hotbarAll(gameManager.getLobby(), Util.stringToComponent("&4" + playerCount + " &f/ 2"));
            }
        }.runTaskTimer(HeavenWarsBeta.getPlugin(), 1L, 1L);

    }


    public void toSpawns() {

        for (Player player : teamsManager.inGame){

            playerToSpawn(player);

        }

    }

    public void playerToSpawn(Player player) {

        if (teamsManager.team1Scores.containsKey(player)) {

            player.teleport(valuesManager.getTeam1Spawn());

        } else if (teamsManager.team2Scores.containsKey(player)) {

            player.teleport(valuesManager.getTeam2Spawn());

        } else if (teamsManager.team3Scores.containsKey(player)) {

            player.teleport(valuesManager.getTeam3Spawn());

        } else if (teamsManager.team4Scores.containsKey(player)) {

            player.teleport(valuesManager.getTeam4Spawn());

        }

        player.lookAt(valuesManager.getGameCenter(), LookAnchor.EYES);

    }

    public void toSpectators(Player player) {

        teamsManager.inGame.remove(player);
        teamsManager.team1Scores.remove(player);
        teamsManager.team2Scores.remove(player);
        teamsManager.team3Scores.remove(player);
        teamsManager.team4Scores.remove(player);

        player.teleport(valuesManager.getGameCenter());

        teamsManager.spectators.add(player);
        player.getInventory().clear();
        new BukkitRunnable() {
            @Override
            public void run() {

                player.setGameMode(GameMode.SPECTATOR);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamemode spectator " + player.getName());
            }
        }.runTaskLater(HeavenWarsBeta.getPlugin(), 2L);


    }

    public void equip() {

        for (Player player : teamsManager.inGame){

            PlayerInventory inventory = player.getInventory();
            
            inventory.clear();

            if (teamsManager.team1Scores.containsKey(player)) {
                player.getInventory().setHelmet(Equipment.helmets.get(teamsManager.getTeam1Color()));
                player.getInventory().setChestplate(Equipment.chestplates.get(teamsManager.getTeam1Color()));
            } else if (teamsManager.team2Scores.containsKey(player)) {
                player.getInventory().setHelmet(Equipment.helmets.get(teamsManager.getTeam2Color()));
                player.getInventory().setChestplate(Equipment.chestplates.get(teamsManager.getTeam2Color()));
            } else if (teamsManager.team3Scores.containsKey(player)) {
                player.getInventory().setHelmet(Equipment.helmets.get(teamsManager.getTeam3Color()));
                player.getInventory().setChestplate(Equipment.chestplates.get(teamsManager.getTeam3Color()));
            } else if (teamsManager.team4Scores.containsKey(player)) {
                player.getInventory().setHelmet(Equipment.helmets.get(teamsManager.getTeam4Color()));
                player.getInventory().setChestplate(Equipment.chestplates.get(teamsManager.getTeam4Color()));
            }


            inventory.addItem(Equipment.Sword, Equipment.Bow, Equipment.FlintAndSteel);
            inventory.setItem(8, ShopItems.Shop);
            inventory.setItem(35, Equipment.Arrow);
            inventory.setItemInOffHand(Equipment.Shield);

        }

    }

}
