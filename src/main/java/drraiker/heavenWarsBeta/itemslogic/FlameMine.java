package drraiker.heavenWarsBeta.itemslogic;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Items.Items;
import drraiker.heavenWarsBeta.Items.ShopItems;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.TeamsManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class FlameMine implements Listener {


    @EventHandler
    private void Use(PlayerInteractEvent e) {

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getItem() == null) return;
        if (!e.getItem().isSimilar(Items.FlameMine)) return;

        Block block = e.getClickedBlock().getRelative(e.getBlockFace());

        @NotNull Collection<Entity> entityList = block.getLocation().toCenterLocation().getNearbyEntities(0.5,0.5,0.5);

        for (Entity entity : entityList) {

            if (entity instanceof ArmorStand) return;

        }

        Player player = e.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.isSimilar(Items.FlameMine))
            mainHand.setAmount(mainHand.getAmount() - 1);
        else if (offHand.isSimilar(Items.FlameMine))
            offHand.setAmount(offHand.getAmount() - 1);


        World world = block.getWorld();

        ArmorStand mine = (ArmorStand) world.spawnEntity(block.getLocation().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);

        mine.setInvisible(true);
        mine.setItem(EquipmentSlot.HEAD, ShopItems.FlameMine);
        mine.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        mine.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        mine.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        mine.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
        mine.setSmall(true);
        mine.setInvulnerable(true);

        CheckEntity(mine);


    }


    private void CheckEntity(Entity entity){

        GameManager gameManager = GameManager.getGameManager(entity.getWorld());

        if (gameManager == null) return;

        TeamsManager teamsManager = gameManager.getTeamsManager();

        new BukkitRunnable() {
            @Override
            public void run() {

                if (Bukkit.getEntity(entity.getUniqueId()) == null) cancel();

                List<Entity> entityList = entity.getNearbyEntities(0.5,0.5,0.5);

                for (Entity entity1 : entityList) {

                    if (entity1 instanceof Player) {
                        if (teamsManager.spectators.contains(entity1)) continue;
                    }
                    if (!(entity1 instanceof ArmorStand))
                        Explode(entity, gameManager);

                }
            }
        }.runTaskTimer(HeavenWarsBeta.getPlugin(), 1L, 1L);

    }

    public static void Explode(Entity mine, GameManager gameManager) {

        mine.getWorld().spawnParticle(Particle.FLAME, mine.getLocation(), 50, 0, 0, 0, 1.0);
        mine.getWorld().playSound(mine.getLocation(), Sound.ENTITY_GHAST_SHOOT, 3, 0);

        Location loc = mine.getLocation();
        int mineX = loc.getBlockX();
        int mineY = loc.getBlockY();
        int mineZ = loc.getBlockZ();

        int x1 = mineX - 1;
        int y1 = mineY;
        int z1 = mineZ - 1;

        int x2 = mineX + 1;
        int y2 = mineY + 1;
        int z2 = mineZ + 1;

        for(int x = x1; x <= x2; x++){
            for(int y = y1; y <= y2; y++) {
                for(int z = z1; z <= z2; z++) {
                    Block block = gameManager.getWorld().getBlockAt(x, y, z);
                    if (block.getType().equals(Material.AIR))
                        block.setType(Material.FIRE);
                }
            }
        }
        mine.remove();

        Mine.CheckMines(mine);
    }


}
