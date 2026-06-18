package drraiker.heavenWarsBeta.timers;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Managers.*;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class PreGameTimer {

    public static void startCountdown(GameManager gameManager) {

        ValuesManager valuesManager = gameManager.getValuesManager();
        PlayerManager playerManager = gameManager.getPlayerManager();

        gameManager.setGameState(GameState.STARTING);


        new BukkitRunnable() {

            int number = 40;
            Location loc = valuesManager.getLobbySpawn();

            @Override
            public void run() {

                if (gameManager.getLobby().getPlayerCount() < 2) {
                    gameManager.setGameState(GameState.LOBBY);
                    ChatManager.sendAll(gameManager.getLobby(), ChatManager.prefix.append(Util.stringToComponent("&сЗапуск игры остановлен!")));
                    playerManager.checkCount();
                    cancel();
                }
                if (number > 0) {
                    ChatManager.hotbarAll(valuesManager.getLobbyWorld(), Util.stringToComponent("&2" + gameManager.getLobby().getPlayerCount() + " &fИгроков &f&l|&f До старта: " + "&6" + number));

                    if (number == 30) {
                        gameManager.setGameState(GameState.STARTING);
                        ChatManager.sendAll(gameManager.getLobby(), ChatManager.prefix.append(Util.stringToComponent("Игра начнётся через " + number)));
                        gameManager.getLobby().playSound(loc, Sound.BLOCK_NOTE_BLOCK_BANJO, 5, 1);
                    }
                    if (number == 20) {
                        ChatManager.sendAll(gameManager.getLobby(), ChatManager.prefix.append(Util.stringToComponent("Игра начнётся через " + number)));
                        gameManager.getLobby().playSound(loc, Sound.BLOCK_NOTE_BLOCK_BANJO, 5, 1);
                    }
                    if (number == 10) {
                        ChatManager.sendAll(gameManager.getLobby(), ChatManager.prefix.append(Util.stringToComponent("Игра начнётся через " + number)));
                        gameManager.getLobby().playSound(loc, Sound.BLOCK_NOTE_BLOCK_BANJO, 5, 1);
                    }
                    if (number <= 5) {
                        ChatManager.sendAll(gameManager.getLobby(), ChatManager.prefix.append(Util.stringToComponent("Игра начнётся через " + number)));
                        gameManager.getLobby().playSound(loc, Sound.BLOCK_NOTE_BLOCK_BANJO, 5, 1);
                    }
                    number--;
                } else {
                    ChatManager.titleAll(gameManager.getLobby(), Util.stringToComponent("&6Игра началась!"), Util.stringToComponent("&2Удачи"));
                    gameManager.getLobby().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);

                    gameManager.startGame();
                    cancel();
                }
            }
        }.runTaskTimer(HeavenWarsBeta.getPlugin(),20L, 20L);
    }


}
