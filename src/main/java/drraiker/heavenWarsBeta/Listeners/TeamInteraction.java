package drraiker.heavenWarsBeta.Listeners;

import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.GameState;
import drraiker.heavenWarsBeta.Menus.ShopMenu;
import drraiker.heavenWarsBeta.Menus.TeamChooseMenu;
import drraiker.heavenWarsBeta.Menus.TeamChooseMenuSAVED;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeamInteraction implements Listener {

    @EventHandler
    private void OpenMenu(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        GameManager gameManager = GameManager.getGameManagerByLobby(player.getWorld());

        if (gameManager == null) return;

        if (!e.getAction().isRightClick()) return;
        if (e.getItem() == null) return;
        if (!e.getItem().getType().equals(Material.HEART_OF_THE_SEA)) return;

        if (gameManager.getGameState().equals(GameState.LOBBY) || gameManager.getGameState().equals(GameState.STARTING))
            TeamChooseMenu.openInventory(player);

    }
    @EventHandler
    private void OpenShop(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        GameManager gameManager = GameManager.getGameManager(player.getWorld());

        if (gameManager == null) return;

        if (!e.getAction().isRightClick()) return;
        if (e.getItem() == null) return;
        if (!e.getItem().getType().equals(Material.CLOCK)) return;

        if (gameManager.getGameState().equals(GameState.GAME))
            ShopMenu.openInventory(player);

    }

}
