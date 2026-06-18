package drraiker.heavenWarsBeta.itemslogic;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Items.Items;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.TeamsManager;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Floor implements Listener {



    @EventHandler
    private void onClick(PlayerInteractEvent e) {

        if (!e.getAction().isRightClick()) return;
        if (e.getItem() == null) return;
        if (!e.getItem().isSimilar(Items.floor)) return;

        Player player = e.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.isSimilar(Items.floor))
            mainHand.setAmount(mainHand.getAmount() - 1);
        else if (offHand.isSimilar(Items.floor))
            offHand.setAmount(offHand.getAmount() - 1);

        GameManager gameManager = GameManager.getGameManager(player.getWorld());

        if (gameManager == null) return;
        TeamsManager teamsManager = gameManager.getTeamsManager();

        Location location = player.getLocation();

        //player.setVelocity(new Vector(0,2,0));

        buildFloor(location, teamsManager.getTeamMaterialByPlayer(player));

        World world = location.getWorld();

        world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 30, 1.5f, 1.5f, 1.5f, 0);
        world.spawnParticle(Particle.BLOCK_CRACK, location, 50, 1.5f, 1.5f, 1.5f, 0, teamsManager.getTeamMaterialByPlayer(player) .createBlockData());

        world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1, 2f);

    }

    private void buildFloor(Location location, Material wallMaterial) {
        World world = location.getWorld();

        int startX = location.getBlockX() - 2;
        int startY = location.getBlockY() - 1;
        int startZ = location.getBlockZ() - 2;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if ((x == 4 || x ==0) && (y == 0 || y == 4)) continue;
                Wall.setNotInArea(world, startX + x, startY, startZ + y, wallMaterial);
            }
        }

    }

}
