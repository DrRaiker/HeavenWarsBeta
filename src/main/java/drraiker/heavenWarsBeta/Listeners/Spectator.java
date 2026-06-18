package drraiker.heavenWarsBeta.Listeners;

import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.GameState;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Spectator implements Listener {

    @EventHandler
    private void LockInArea(PlayerMoveEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        if (!gameManager.getTeamsManager().spectators.contains(e.getPlayer())) return;

        Location loc = e.getTo();

        if (loc.getX() > 45 || loc.getX() < -45 ||
                loc.getZ() > 45 || loc.getZ() < -45 ||
                loc.getY() > 100 || loc.getY() < 53)
            e.setCancelled(true);

    }

}
