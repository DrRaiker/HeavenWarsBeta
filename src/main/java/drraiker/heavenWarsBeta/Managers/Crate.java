package drraiker.heavenWarsBeta.Managers;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Items.Items;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class Crate implements Listener {

    public static void spawnCrate(GameManager gameManager) {

        ChatManager.sendAll(gameManager.getWorld(), ChatManager.prefix.append(Util.stringToComponent("&fПосылка уже здесь!")));

        Location spawnLoc = getLoc(gameManager);

        Chicken chicken = (Chicken) gameManager.getWorld().spawnEntity(spawnLoc, EntityType.CHICKEN);

        chicken.setEggLayTime(10000000);
        chicken.setAdult();
        chicken.setInvisible(true);
        chicken.setSilent(true);
        chicken.setInvulnerable(true);



        ArmorStand armorStand = (ArmorStand) gameManager.getWorld().spawnEntity(spawnLoc, EntityType.ARMOR_STAND);
        armorStand.setItem(EquipmentSlot.HEAD, Items.CrateFly);
        armorStand.setSmall(true);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);


        chicken.addPassenger(armorStand);

        removeChicken(chicken);


    }


    private static void removeChicken(Entity entity) {

        new BukkitRunnable() {
            @Override
            public void run() {

                if (Bukkit.getEntity(entity.getUniqueId()) == null) cancel();

                Location loc = entity.getLocation();
                loc.setY(loc.getY() - 1);

                if (!loc.getBlock().getType().equals(Material.AIR)) {

                    List<Entity> list = entity.getPassengers();

                    if (list.size() > 0) {
                        ArmorStand armorStand = (ArmorStand) list.get(0);

                        armorStand.setItem(EquipmentSlot.HEAD, Items.Crate);
                    }
                    entity.remove();
                }

            }
        }.runTaskTimer(HeavenWarsBeta.getPlugin(), 1L, 1L);

    }

    private static Location getLoc(GameManager gameManager) {

        int x1 = -23;
        int z1 = -23;
        int x2 = 23;
        int z2 = 23;

        Random random = new Random();

        int x = random.nextInt(x1,x2 + 1);
        int z = random.nextInt(z1,z2 + 1);

        Location loc = new Location(gameManager.getWorld(), x, 100, z);

        while (true) {

            Block block = loc.getBlock();

            if (!block.getType().equals(Material.AIR)) {

                loc.setY(100);

                return loc.toCenterLocation();
            }

            loc.setY(loc.getY() - 1);

            if (loc.getY() <= 57) return getLoc(gameManager);
        }

    }

    @EventHandler
    private void clickOnCrate(PlayerInteractAtEntityEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        TeamsManager teamsManager = gameManager.getTeamsManager();

        if (!(e.getRightClicked() instanceof ArmorStand)) return;
        if (!teamsManager.inGame.contains(e.getPlayer())) return;
        ArmorStand armorStand = (ArmorStand) e.getRightClicked();

        if (!armorStand.getItem(EquipmentSlot.HEAD).isSimilar(Items.Crate)) return;

        World world = gameManager.getWorld();
        Location loc = armorStand.getLocation();

        Random random = new Random();

        ItemStack impulse = Items.ImpulseGrenade.clone();
        impulse.setAmount(random.nextInt(0, 32));

        ItemStack mine = Items.Mine.clone();
        mine.setAmount(random.nextInt(0, 32));

        ItemStack flameMine = Items.FlameMine.clone();
        flameMine.setAmount(random.nextInt(0, 16));

        ItemStack floor = Items.floor.clone();
        floor.setAmount(random.nextInt(0, 32));
        ItemStack wall = Items.wall.clone();
        wall.setAmount(random.nextInt(0, 16));

        ItemStack wool = new ItemStack(Material.WHITE_WOOL, random.nextInt(32,64));
        ItemStack wool2 = new ItemStack(Material.WHITE_WOOL, random.nextInt(32,64));
        ItemStack tnt = new ItemStack(Material.TNT, random.nextInt(32,64));
        ItemStack tnt2 = new ItemStack(Material.TNT, random.nextInt(32,64));


        world.dropItem(loc, wall);
        world.dropItem(loc, floor);
        world.dropItem(loc, mine);
        world.dropItem(loc, impulse);
        world.dropItem(loc, flameMine);
        world.dropItem(loc, wool);
        world.dropItem(loc, wool2);
        world.dropItem(loc, tnt);
        world.dropItem(loc, tnt2);

        world.spawnParticle(Particle.TOTEM, loc.toCenterLocation(), 150);
        world.spawnParticle(Particle.BLOCK_CRACK, loc.toCenterLocation(), 50, Material.SPRUCE_PLANKS.createBlockData());
        world.playSound(loc, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1, 1);

        armorStand.remove();

    }

}
