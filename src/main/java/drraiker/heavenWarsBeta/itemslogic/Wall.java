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

public class Wall implements Listener {

    @EventHandler
    private void onLaunch(ProjectileLaunchEvent e) {

        if (!(e.getEntity() instanceof Snowball snowball)) return;

        if (!snowball.getItem().isSimilar(Items.wall)) return;

        if (!(snowball.getShooter() instanceof Player player)) return;
        int angle = (int) Util.roundYaw45(player.getYaw());

        NBT.modifyPersistentData(snowball, nbt -> {
            nbt.setInteger("angle", angle);
        });

    }

    @EventHandler
    private void onHit(ProjectileHitEvent e) {

        if (!(e.getEntity() instanceof Snowball snowball)) return;


        if (!(snowball.getShooter() instanceof Player player)) return;

        GameManager gameManager = GameManager.getGameManager(player.getWorld());

        if (gameManager == null) return;
        TeamsManager teamsManager = gameManager.getTeamsManager();

        if (!NBT.getPersistentData(snowball, (nbt) -> nbt.hasTag("angle"))) return;

        int angle = NBT.getPersistentData(snowball, (nbt) -> nbt.getInteger("angle"));

        Location location = snowball.getLocation();

        if (angle == 0) {
            buildWall(location, teamsManager.getTeamMaterialByPlayer(player) , false);
        } else if (angle == 180) {
            buildWall(location, teamsManager.getTeamMaterialByPlayer(player) , true);
        } else if (angle == -90) {
            buildWall90(location, teamsManager.getTeamMaterialByPlayer(player) , false);
        } else if (angle == 90) {
            buildWall90(location, teamsManager.getTeamMaterialByPlayer(player) , true);
        } else if (angle == 135) {
            buildDiagonal135(location, teamsManager.getTeamMaterialByPlayer(player) );
        } else if (angle == -45) {
            buildDiagonal45m(location, teamsManager.getTeamMaterialByPlayer(player) );
        } else if (angle == 45) {
            buildDiagonal45(location, teamsManager.getTeamMaterialByPlayer(player) );
        } else if (angle == -135) {
            buildDiagonal135m(location, teamsManager.getTeamMaterialByPlayer(player) );
        }

        World world = location.getWorld();

        world.spawnParticle(Particle.EXPLOSION_NORMAL, location.clone().add(0,2,0), 30, 1.5f, 1.5f, 1.5f, 0);
        world.spawnParticle(Particle.BLOCK_CRACK, location.clone().add(0,2,0), 50, 1.5f, 1.5f, 1.5f, 0, teamsManager.getTeamMaterialByPlayer(player) .createBlockData());

        world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1, 2f);

    }


    private void buildWall(Location location, Material wallMaterial, boolean isMirrored) {
        World world = location.getWorld();


        int startX = location.getBlockX() - 2;
        int startY = location.getBlockY() - 1;
        int startZ = location.getBlockZ();


        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if ((x == 4 || x ==0) && (y == 0 || y == 4)) continue;
                setNotInArea(world, startX + x, startY + y, startZ, wallMaterial);
            }
        }
        int m = -1;
        if (isMirrored) {
            m = 1;
        }

        setNotInArea(world, startX + 1, startY + 1, startZ + m, wallMaterial);
        setNotInArea(world, startX + 2, startY + 2, startZ + m, wallMaterial);
        setNotInArea(world, startX + 3, startY + 3, startZ + m, wallMaterial);

    }

    private void buildWall90(Location location, Material wallMaterial, boolean isMirrored) {
        World world = location.getWorld();


        int startX = location.getBlockX();
        int startY = location.getBlockY() - 1;
        int startZ = location.getBlockZ() - 2;


        for (int z = 0; z < 5; z++) {
            for (int y = 0; y < 5; y++) {
                if ((z == 4 || z == 0) && (y == 0 || y == 4)) continue;
                setNotInArea(world, startX, startY + y, startZ + z, wallMaterial);
            }
        }

        int m = -1;
        if (isMirrored) {
            m = 1;
        }


        setNotInArea(world, startX + m, startY + 1, startZ + 1, wallMaterial);
        setNotInArea(world, startX + m, startY + 2, startZ + 2, wallMaterial);
        setNotInArea(world, startX + m, startY + 3, startZ + 3, wallMaterial);
    }

    private void buildDiagonal135(Location location, Material wallMaterial) {
        World world = location.getWorld();


        int startX = location.getBlockX();
        int startY = location.getBlockY()-1;
        int startZ = location.getBlockZ();

        setNotInArea(world, startX , startY, startZ, wallMaterial);
        setNotInArea(world, startX , startY+1, startZ, wallMaterial);
        setNotInArea(world, startX , startY+2, startZ, wallMaterial);
        setNotInArea(world, startX , startY+3, startZ, wallMaterial);
        setNotInArea(world, startX , startY+4, startZ, wallMaterial);

        setNotInArea(world, startX - 1 , startY, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 1 , startY+1, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 1 , startY+2, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 1 , startY+3, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 1 , startY+4, startZ + 1, wallMaterial);

        setNotInArea(world, startX - 1 , startY+1, startZ + 2, wallMaterial);
        setNotInArea(world, startX - 1 , startY+2, startZ + 2, wallMaterial);
        setNotInArea(world, startX - 1 , startY+3, startZ + 2, wallMaterial);

        setNotInArea(world, startX + 1 , startY, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 1 , startY+1, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 1 , startY+2, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 1 , startY+3, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 1 , startY+4, startZ - 1, wallMaterial);

        setNotInArea(world, startX + 2 , startY+1, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 2 , startY+2, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 2 , startY+3, startZ - 1, wallMaterial);


        setNotInArea(world, startX , startY+1, startZ + 1, wallMaterial);
        setNotInArea(world, startX , startY+2, startZ + 2, wallMaterial);
        setNotInArea(world, startX + 1, startY+1, startZ, wallMaterial);
        setNotInArea(world, startX + 2, startY+2, startZ, wallMaterial);
    }

    private void buildDiagonal45m(Location location, Material wallMaterial) {
        World world = location.getWorld();

        int startX = location.getBlockX();
        int startY = location.getBlockY() - 1;
        int startZ = location.getBlockZ();


        setNotInArea(world, startX, startY, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 1, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 2, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 3, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 4, startZ, wallMaterial);

        setNotInArea(world, startX + 1, startY, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 1, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 2, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 3, startZ - 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 4, startZ - 1, wallMaterial);

        setNotInArea(world, startX + 1, startY + 1, startZ - 2, wallMaterial);
        setNotInArea(world, startX + 1, startY + 2, startZ - 2, wallMaterial);
        setNotInArea(world, startX + 1, startY + 3, startZ - 2, wallMaterial);

        setNotInArea(world, startX - 1, startY, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 1, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 2, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 3, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 4, startZ + 1, wallMaterial);

        setNotInArea(world, startX - 2, startY + 1, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 2, startY + 2, startZ + 1, wallMaterial);
        setNotInArea(world, startX - 2, startY + 3, startZ + 1, wallMaterial);

        setNotInArea(world, startX, startY + 1, startZ - 1, wallMaterial);
        setNotInArea(world, startX, startY + 2, startZ - 2, wallMaterial);
        setNotInArea(world, startX - 1, startY + 1, startZ, wallMaterial);
        setNotInArea(world, startX - 2, startY + 2, startZ, wallMaterial);
    }

    private void buildDiagonal45(Location location, Material wallMaterial) {
        World world = location.getWorld();

        int startX = location.getBlockX();
        int startY = location.getBlockY() - 1;
        int startZ = location.getBlockZ();

        setNotInArea(world, startX, startY, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 1, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 2, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 3, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 4, startZ, wallMaterial);

        setNotInArea(world, startX - 1, startY, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 1, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 2, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 3, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 4, startZ - 1, wallMaterial);


        setNotInArea(world, startX - 1, startY + 1, startZ - 2, wallMaterial);
        setNotInArea(world, startX - 1, startY + 2, startZ - 2, wallMaterial);
        setNotInArea(world, startX - 1, startY + 3, startZ - 2, wallMaterial);


        setNotInArea(world, startX + 1, startY, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 1, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 2, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 3, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 4, startZ + 1, wallMaterial);

        setNotInArea(world, startX + 2, startY + 1, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 2, startY + 2, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 2, startY + 3, startZ + 1, wallMaterial);


        setNotInArea(world, startX + 1, startY + 1, startZ, wallMaterial);
        setNotInArea(world, startX + 2, startY + 2, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 1, startZ-1, wallMaterial);
        setNotInArea(world, startX, startY + 2, startZ-2, wallMaterial);
    }

    private void buildDiagonal135m(Location location, Material wallMaterial) {
        World world = location.getWorld();

        int startX = location.getBlockX();
        int startY = location.getBlockY() - 1;
        int startZ = location.getBlockZ();

        setNotInArea(world, startX, startY, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 1, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 2, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 3, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 4, startZ, wallMaterial);

        setNotInArea(world, startX + 1, startY, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 1, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 2, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 3, startZ + 1, wallMaterial);
        setNotInArea(world, startX + 1, startY + 4, startZ + 1, wallMaterial);

        setNotInArea(world, startX + 1, startY + 1, startZ + 2, wallMaterial);
        setNotInArea(world, startX + 1, startY + 2, startZ + 2, wallMaterial);
        setNotInArea(world, startX + 1, startY + 3, startZ + 2, wallMaterial);

        setNotInArea(world, startX - 1, startY, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 1, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 2, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 3, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 1, startY + 4, startZ - 1, wallMaterial);

        setNotInArea(world, startX - 2, startY + 1, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 2, startY + 2, startZ - 1, wallMaterial);
        setNotInArea(world, startX - 2, startY + 3, startZ - 1, wallMaterial);

        setNotInArea(world, startX - 1, startY + 1, startZ, wallMaterial);
        setNotInArea(world, startX - 2, startY + 2, startZ, wallMaterial);
        setNotInArea(world, startX, startY + 1, startZ + 1, wallMaterial);
        setNotInArea(world, startX, startY + 2, startZ + 2, wallMaterial);
    }

    public static void setNotInArea(World world, double x, double y, double z, Material material) {

        Location loc = new Location(world,x,y,z);

        if (loc.getX() > 45 || loc.getX() < -45 ||
                loc.getZ() > 45 || loc.getZ() < -45 ||
                loc.getY() > 99 || loc.getY() < 53)
            return;

        Block block = world.getBlockAt(loc);

        if (block.getType().equals(Material.CONDUIT))
            return;

        GameManager gameManager = GameManager.getGameManager(world);
        if (gameManager != null) {
            if (gameManager.getValuesManager().getUnbreakable().contains(block.getLocation()) && !block.getType().equals(Material.FIRE)) {
                return;
            }
        }
        block.setType(material);
    }

}
