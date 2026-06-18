package drraiker.heavenWarsBeta.itemslogic;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Items.Items;
import drraiker.heavenWarsBeta.Items.ShopItems;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.TeamsManager;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
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

public class Mine implements Listener {


    @EventHandler
    private void Use(PlayerInteractEvent e) {

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getItem() == null) return;
        if (!e.getItem().isSimilar(Items.Mine)) return;

        Block block = e.getClickedBlock().getRelative(e.getBlockFace());

        @NotNull Collection<Entity> entityList = block.getLocation().toCenterLocation().getNearbyEntities(0.5,0.5,0.5);

        for (Entity entity : entityList) {

            if (entity instanceof ArmorStand) return;

        }



        Player player = e.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.isSimilar(Items.Mine))
            mainHand.setAmount(mainHand.getAmount() - 1);
        else if (offHand.isSimilar(Items.Mine))
            offHand.setAmount(offHand.getAmount() - 1);


        World world = block.getWorld();

        ArmorStand mine = (ArmorStand) world.spawnEntity(block.getLocation().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);

        mine.setInvisible(true);
        mine.setItem(EquipmentSlot.HEAD, ShopItems.Mine);
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
                        Explode(entity);

                }

            }
        }.runTaskTimer(HeavenWarsBeta.getPlugin(), 1L, 1L);

    }


    public static void CheckMines(Entity mine) {

        GameManager gameManager = GameManager.getGameManager(mine.getWorld());

        if (gameManager == null) return;

        TeamsManager teamsManager = gameManager.getTeamsManager();

        List<Entity> entityList = mine.getNearbyEntities(4,4,4);

        for (Entity entity1 : entityList) {
            if (!(entity1 instanceof ArmorStand)) continue;
            if (entity1.equals(mine)) continue;
            ArmorStand armorStand = (ArmorStand) entity1;

            if (armorStand.getItem(EquipmentSlot.HEAD).isSimilar(ShopItems.Mine)) {
                Explode(entity1);
            } else if (armorStand.getItem(EquipmentSlot.HEAD).isSimilar(ShopItems.FlameMine)) {
                FlameMine.Explode(entity1, gameManager);
            }
        }
    }

    public static void Explode(Entity mine) {

        mine.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, mine.getLocation(), 5);
        mine.getWorld().createExplosion(mine ,mine.getLocation(), 1.5f, false, true);
        mine.remove();

        CheckMines(mine);
    }


}
