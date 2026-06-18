package drraiker.heavenWarsBeta.itemslogic;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Items.Items;
import drraiker.heavenWarsBeta.Items.ShopItems;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.TeamsManager;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class ImpulseGrenade implements Listener {

    @EventHandler
    private void Use(PlayerInteractEvent e) {

        if (!e.getAction().isRightClick()) return;
        if (e.getItem() == null) return;
        if (!e.getItem().isSimilar(Items.ImpulseGrenade)) return;

        Player player = e.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.isSimilar(Items.ImpulseGrenade))
            mainHand.setAmount(mainHand.getAmount() - 1);
        else if (offHand.isSimilar(Items.ImpulseGrenade))
            offHand.setAmount(offHand.getAmount() - 1);

        Launch(player.getLocation().add(0, 1,0));


    }

    private final HashMap<UUID, Long> flying = new HashMap<>();

    private void Launch(Location loc) {


        GameManager gameManager = GameManager.getGameManager(loc.getWorld());

        if (gameManager == null) return;

        TeamsManager teamsManager = gameManager.getTeamsManager();

        ArmorStand grenade = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND, CreatureSpawnEvent.SpawnReason.CUSTOM);
        grenade.setInvisible(true);
        grenade.setItem(EquipmentSlot.HEAD, ShopItems.ImpulseGrenade);
        grenade.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        grenade.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        grenade.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        grenade.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);

        grenade.setSmall(true);
        grenade.setInvulnerable(true);

        grenade.setVelocity(loc.getDirection());

        new BukkitRunnable() {

            int lifeTime = 100;
            int explosionTime = 13;

            @Override
            public void run() {

                if (Bukkit.getEntity(grenade.getUniqueId()) == null) cancel();

                if (explosionTime < 13)
                    explosionTime--;

                if (grenade.isOnGround() && explosionTime == 13) {
                    grenade.getWorld().playSound(grenade.getLocation(), "heaven_wars.impulse_grenade", 3, 1);
                    grenade.setGravity(false);
                    explosionTime--;
                }

                if (explosionTime == 0) {

                    Location g_loc = grenade.getLocation();
                    World world = g_loc.getWorld();

                    Particle.DustTransition dustTransition = new Particle.DustTransition(Color.fromRGB(161, 0, 255), Color.fromRGB(63, 0, 255), 1.5f);

                    world.spawnParticle(Particle.BLOCK_CRACK, g_loc, 200, 1.5, 1.5, 1.5, 1.0, Material.PURPLE_CONCRETE.createBlockData(), true);
                    world.spawnParticle(Particle.DUST_COLOR_TRANSITION, g_loc, 300, 1.5, 1.5, 1.5, 1.0, dustTransition, true);
                    //world.spawnParticle(Particle.SOUL_FIRE_FLAME, g_loc, 100, 0, 0, 0, 1.0);


                    @NotNull Collection<LivingEntity> entities = grenade.getLocation().getNearbyLivingEntities(3);

                    for (LivingEntity entity : entities) {

                        if (entity instanceof Player) {
                            if (teamsManager.spectators.contains(entity)) continue;
                        }

                        Location e_loc = entity.getLocation();

                        double distance = e_loc.distance(g_loc);
                        Location vector = new Location(grenade.getWorld(), e_loc.getX() - g_loc.getX(), e_loc.getY() - g_loc.getY(), e_loc.getZ() - g_loc.getZ());

                        if (distance > 2.5)
                            vector.multiply(0.5);
                        else if (distance > 2 && distance < 2.5)
                            vector.multiply(0.75);
                        else if (distance > 1.5 && distance < 2)
                            vector.multiply(1);
                        else if (distance > 1 && distance < 1.5)
                            vector.multiply(2);
                        else if (distance > 0.5 && distance < 1)
                            vector.multiply(2.5);
                        else if (distance < 0.5)
                            vector.multiply(3);

                        entity.setVelocity(vector.toVector());
                        StartFly(entity);
                    }

                    grenade.remove();
                }


                lifeTime--;

                if (lifeTime == 0)
                    grenade.remove();

            }
        }.runTaskTimer(HeavenWarsBeta.getPlugin(), 1L,1L);


    }

    public void StartFly(LivingEntity entity) {

        flying.put(entity.getUniqueId(), System.currentTimeMillis());
        World world = entity.getWorld();
        Particle.DustTransition dustTransition = new Particle.DustTransition(Color.fromRGB(161, 0, 255), Color.fromRGB(63, 0, 255), 1.5f);

        new BukkitRunnable() {

            @Override
            public void run() {

                if (!flying.containsKey(entity.getUniqueId())) {
                    cancel();
                    return;
                }
                if (Bukkit.getEntity(entity.getUniqueId()) == null) {
                    cancel();
                    return;
                }

                world.spawnParticle(Particle.DUST_COLOR_TRANSITION, entity.getLocation(), 10, 0.5, 0.5, 0.5, 1.0, dustTransition, true);

            }
        }.runTaskTimer(HeavenWarsBeta.getPlugin(), 1L, 1L);
    }


    @EventHandler
    private void FallDamage(EntityDamageEvent e){

        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
        if (!(e.getEntity() instanceof LivingEntity entity)) return;
        if (!flying.containsKey(entity.getUniqueId())) return;

        flying.remove(entity.getUniqueId());
        e.setCancelled(true);

    }

    @EventHandler
    private void onMove(EntityMoveEvent e) {
        LivingEntity entity = e.getEntity();
        if (!flying.containsKey(entity.getUniqueId())) return;
        if (!entity.isOnGround()) return;

        if (System.currentTimeMillis() < flying.get(entity.getUniqueId()) + 1000) return;
        flying.remove(entity.getUniqueId());
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!flying.containsKey(player.getUniqueId())) return;

        if (!player.getLocation().add(0, -0.1, 0).getBlock().isSolid()) return;

        if (System.currentTimeMillis() < flying.get(player.getUniqueId()) + 1000) return;
        flying.remove(player.getUniqueId());
    }

}
