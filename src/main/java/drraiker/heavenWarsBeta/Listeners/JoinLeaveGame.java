package drraiker.heavenWarsBeta.Listeners;

import drraiker.heavenWarsBeta.Items.Items;
import drraiker.heavenWarsBeta.Managers.*;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.Util;
import drraiker.heavenWarsBeta.timers.PreGameTimer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveGame implements Listener {


    @EventHandler
    private void JoinGame(PlayerChangedWorldEvent e){

        Player player = e.getPlayer();

        GameManager gameManager = GameManager.getGameManagerByLobby(player.getWorld());

        if (gameManager == null) return;

        JoinLogic(player, gameManager);

    }

    @EventHandler
    private void JoinServer(PlayerJoinEvent e){

        Player player = e.getPlayer();

        GameManager gameManager = GameManager.getGameManager(player.getWorld());

        if (gameManager == null) {
            gameManager = GameManager.getGameManagerByLobby(player.getWorld());
        }

        if (gameManager == null) return;

        JoinLogic(player, gameManager);


    }


    private void JoinLogic(Player player, GameManager gameManager) {

        TeamsManager teamsManager = gameManager.getTeamsManager();
        PlayerManager playerManager = gameManager.getPlayerManager();
        ValuesManager valuesManager = gameManager.getValuesManager();

        if (gameManager.getLobby().getPlayerCount() == 1 && teamsManager.inGame.isEmpty() && !gameManager.getGameState().equals(GameState.LOBBY)) {

            gameManager.setGameState(GameState.LOBBY);

        }

        if (gameManager.getGameState().equals(GameState.LOBBY) || gameManager.getGameState().equals(GameState.STARTING)) {

            if (gameManager.getLobby().getPlayerCount() == 1) playerManager.checkCount();

            player.teleport(valuesManager.getLobbySpawn());

            teamsManager.inGame.remove(player);
            teamsManager.team1Scores.remove(player);
            teamsManager.team2Scores.remove(player);
            teamsManager.team3Scores.remove(player);
            teamsManager.team4Scores.remove(player);
            teamsManager.spectators.remove(player);

            player.setExp(0);
            player.setTotalExperience(0);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.getInventory().clear();

            player.getInventory().setItem(4, Items.teamSelect);

            player.setGameMode(GameMode.ADVENTURE);
            ChatManager.sendAll(gameManager.getLobby(), ChatManager.prefix.append(Util.stringToComponent(player.getName() + " присоединился к игре")));

            if (Configs.data.get("colors." + player.getName()) == null) {
                Configs.data.set("colors." + player.getName() + ".name", "Белая");
                Configs.data.set("colors." + player.getName() + ".material", "light_gray_wool");
                Configs.data.set("colors." + player.getName() + ".color", "&f");
                Configs.saveData();
            }

            if (gameManager.getGameState().equals(GameState.LOBBY)) {
                if (gameManager.getLobby().getPlayerCount() > 1) {

                    PreGameTimer.startCountdown(gameManager);

                }
            }
        } else {

            playerManager.toSpectators(player);
        }
    }


    @EventHandler
    private void QuitGame(PlayerChangedWorldEvent e) {

        Player player = e.getPlayer();

        GameManager gameManager = GameManager.getGameManager(e.getFrom());

        if (gameManager == null) {
            gameManager = GameManager.getGameManagerByLobby(e.getFrom());
        }

        if (gameManager == null) return;

        if (e.getPlayer().getWorld().equals(gameManager.getWorld())) return;

        QuitLogic(player, gameManager);


    }

    @EventHandler
    private void QuitServer(PlayerQuitEvent e) {

        Player player = e.getPlayer();

        GameManager gameManager = GameManager.getGameManager(player.getWorld());

        if (gameManager == null) {
            gameManager = GameManager.getGameManagerByLobby(player.getWorld());
        }

        if (gameManager == null) return;

        QuitLogic(player, gameManager);


    }

    private void QuitLogic(Player player, GameManager gameManager) {

        TeamsManager teamsManager = gameManager.getTeamsManager();

        if (teamsManager.team1Scores.containsKey(player)) {
            teamsManager.updateColor("team1");
        } else if (teamsManager.team2Scores.containsKey(player)) {
            teamsManager.updateColor("team2");
        } else if (teamsManager.team3Scores.containsKey(player)) {
            teamsManager.updateColor("team3");
        } else if (teamsManager.team4Scores.containsKey(player)) {
            teamsManager.updateColor("team4");
        }

        teamsManager.resetPlayerTeam(player);

        teamsManager.inGame.remove(player);
        teamsManager.spectators.remove(player);

        player.setGameMode(GameMode.CREATIVE);

        if (teamsManager.inGame.size() < 2) {
            gameManager.setTimer_s(1);
            gameManager.setTimer_m(0);
        }
    }

}
