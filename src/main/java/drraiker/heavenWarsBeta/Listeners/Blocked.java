package drraiker.heavenWarsBeta.Listeners;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Managers.*;
import drraiker.heavenWarsBeta.Util.Util;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Blocked implements Listener {

    @EventHandler
    private void Damage(EntityDamageEvent e) {


        GameManager gameManager = GameManager.getGameManagerByLobby(e.getEntity().getWorld());

        if (gameManager == null) return;
        
        if (gameManager.getGameState().equals(GameState.GAME)) return;

        e.setCancelled(true);

    }

    @EventHandler
    private void Drop(PlayerDropItemEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) {
            gameManager = GameManager.getGameManagerByLobby(e.getPlayer().getWorld());
        }
        if (gameManager == null) return;

        Material item = e.getItemDrop().getItemStack().getType();

        if (item.equals(Material.WHITE_WOOL) || item.equals(Material.TNT)) return;

        e.setCancelled(true);

    }


    @EventHandler
    private void BreakBlock(BlockBreakEvent e) {

        GameManager gameManager = GameManager.getGameManagerByLobby(e.getPlayer().getWorld());

        if (gameManager == null) return;

        e.setCancelled(true);
    }


    @EventHandler
    private void placeColoredBlock(BlockPlaceEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        TeamsManager teamsManager = gameManager.getTeamsManager();

        Material material = teamsManager.getTeamMaterialByPlayer(e.getPlayer());

        if (e.getBlockPlaced().getType().equals(Material.WHITE_WOOL))
            e.getBlockPlaced().setType(material);

    }



    @EventHandler
    private void PlaceBlock(BlockPlaceEvent e) {

        GameManager gameManager = GameManager.getGameManagerByLobby(e.getPlayer().getWorld());

        if (gameManager == null) return;

        e.setCancelled(true);
    }

    @EventHandler
    private void PlaceBlockInArea(BlockPlaceEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        Block block = e.getBlock();

        if (block.getX() > 45 || block.getX() < -45 ||
                block.getZ() > 45 || block.getZ() < -45 ||
                block.getY() > 100 || block.getY() < 53)
            e.setCancelled(true);


        ValuesManager valuesManager = gameManager.getValuesManager();

        if (valuesManager.getUnbreakable().contains(block.getLocation()) && !block.getType().equals(Material.FIRE)) {
            e.setCancelled(true);
        }
        
    }
    @EventHandler
    private void BreakBlockInArea(BlockBreakEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        Block block = e.getBlock();

        if (block.getX() > 45 || block.getX() < -45 ||
                block.getZ() > 45 || block.getZ() < -45 ||
                block.getY() > 100 || block.getY() < 53)
            e.setCancelled(true);


        ValuesManager valuesManager = gameManager.getValuesManager();

        if (valuesManager.getUnbreakable().contains(block.getLocation()) && !block.getType().equals(Material.FIRE)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void BlockBurn(BlockBurnEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getBlock().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        Block block = e.getBlock();

        ValuesManager valuesManager = gameManager.getValuesManager();

        if (valuesManager.getUnbreakable().contains(block.getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        GameManager gameManager = GameManager.getGameManager(e.getBlock().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        Block block = e.getBlock();

        ValuesManager valuesManager = gameManager.getValuesManager();

        if (valuesManager.getUnbreakable().contains(block.getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void EnderPearl(PlayerTeleportEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        if (!e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) return;

        Location loc = e.getTo();

        if (loc.getX() > 45 || loc.getX() < -45 ||
                loc.getZ() > 45 || loc.getZ() < -45 ||
                loc.getY() > 100 || loc.getY() < 53)
            e.setCancelled(true);
    }

    @EventHandler
    private void fallLobby(PlayerMoveEvent e) {

        GameManager gameManager = GameManager.getGameManagerByLobby(e.getPlayer().getWorld());

        if (gameManager == null) return;

        if (gameManager.getGameState().equals(GameState.GAME)) return;

        Player player = e.getPlayer();

        Location loc = e.getTo();

        World world = player.getWorld();

        if (loc.getY() < 90) {
            world.spawnParticle(Particle.CLOUD, player.getLocation(), 10, 0.4, 0.1, 0.4, 0);
            world.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1.2f);
            player.setVelocity(new Vector(0, 2, 0));
        }

        if (loc.getX() < -35) {
            world.spawnParticle(Particle.CLOUD, player.getLocation().add(0,1,0), 10, 0.4, 1, 0.4, 0);
            world.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1.2f);
            player.setVelocity(new Vector(3, 0, 0));
        }

        if (loc.getX() > 35) {
            world.spawnParticle(Particle.CLOUD, player.getLocation().add(0,1,0), 10, 0.4, 1, 0.4, 0);
            world.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1.2f);
            player.setVelocity(new Vector(-3, 0, 0));
        }
        if (loc.getZ() < -35) {
            world.spawnParticle(Particle.CLOUD, player.getLocation().add(0,1,0), 10, 0.4, 1, 0.4, 0);
            world.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1.2f);
            player.setVelocity(new Vector(0, 0, 3));
        }

        if (loc.getZ() > 35) {
            world.spawnParticle(Particle.CLOUD, player.getLocation().add(0,1,0), 10, 0.4, 1, 0.4, 0);
            world.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1.2f);
            player.setVelocity(new Vector(0, 0, -3));
        }

    }
    @EventHandler
    private void FallEvent(PlayerMoveEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        Player player = e.getPlayer();

        if (!gameManager.getTeamsManager().inGame.contains(player)) return;

        Location loc = e.getTo();

        if (loc.getY() < 53)
           player.damage(100);
    }

    @EventHandler
    private void EntityFallEvent(EntityMoveEvent e) {

        if (e.getEntity() instanceof Player) return;

        GameManager gameManager = GameManager.getGameManager(e.getEntity().getWorld());

        if (gameManager == null) return;
        Location loc = e.getTo();

        if (loc.getY() < 53)
            e.getEntity().remove();
    }

    @EventHandler
    private void BreakHeard(BlockBreakEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        Block block = e.getBlock();

        if (block.getType().equals(Material.CONDUIT))
            e.setCancelled(true);

    }


    @EventHandler
    private void PlayerRespawn(PlayerRespawnEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        ValuesManager valuesManager = gameManager.getValuesManager();
        TeamsManager teamsManager = gameManager.getTeamsManager();
        
        Player player = e.getPlayer();

        if (teamsManager.team1Scores.containsKey(player)) {

            e.setRespawnLocation(valuesManager.getTeam1Spawn());

        } else if (teamsManager.team2Scores.containsKey(player)) {

            e.setRespawnLocation(valuesManager.getTeam2Spawn());

        } else if (teamsManager.team3Scores.containsKey(player)) {

            e.setRespawnLocation(valuesManager.getTeam3Spawn());

        } else if (teamsManager.team4Scores.containsKey(player)) {

            e.setRespawnLocation(valuesManager.getTeam4Spawn());

        }

    }


    @EventHandler
    private void PlayerDeath(PlayerDeathEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;


        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        TeamsManager teamsManager = gameManager.getTeamsManager();

        Player player = e.getPlayer();
        Player killer = player.getKiller();

        if (killer == null) return;

        teamsManager.addPlayerScore(killer, 1);

        teamsManager.addTeamScoreByPlayer(killer, 1);

        ChatManager.sendAll(gameManager.getWorld(), Util.stringToComponent(Util.componentToString(ChatManager.prefix) + teamsManager.getTeamColorByPlayer(player) + player.getName() + "&f был убит " + teamsManager.getTeamColorByPlayer(killer) + killer.getName() + " &f&l| " + teamsManager.getTeamColorByPlayer(killer) + teamsManager.getTeamNameByPlayer(killer) + "&f +1"));

    }

    //@EventHandler
    private void ClickOnHeart(PlayerInteractEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());


        if (gameManager == null) return;

        if (!e.getAction().isRightClick()) return;

        if (e.getClickedBlock() == null) return;

        if (!e.getClickedBlock().getType().equals(Material.CONDUIT)) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        ValuesManager valuesManager = gameManager.getValuesManager();
        TeamsManager teamsManager = gameManager.getTeamsManager();
        PlayerManager playerManager = gameManager.getPlayerManager();
        
        
        if (block.getLocation().equals(valuesManager.getCore1())) {

            if (teamsManager.team1Scores.isEmpty()) return;
            if (teamsManager.team1Scores.containsKey(player)) return;

            gameManager.getWorld().spawnEntity(valuesManager.getTeam1Spawn().clone().add(0,30,0), EntityType.LIGHTNING);

            int num = 0;

            if (teamsManager.getTeam1Score() >= 3) {
                num = 3;
            } else {
                num = teamsManager.getTeam1Score();
            }

            if (num > 0) {

                teamsManager.addTeam1Score(-num);
                teamsManager.addPlayerScore(player, num);
                teamsManager.addTeamScoreByPlayer(player, num);
                ChatManager.sendAll(gameManager.getWorld(), Util.stringToComponent(Util.componentToString(ChatManager.prefix) + teamsManager.getTeamColorByPlayer(player) + player.getName() + "&f разрушил ядро " + teamsManager.getTeam1Color() + teamsManager.getTeam1Name() + " &f&l| " + teamsManager.getTeamColorByPlayer(player) + teamsManager.getTeamNameByPlayer(player) + "&f +" + num));
                ChatManager.titleAll(gameManager.getWorld(), Util.stringToComponent(""), Util.stringToComponent(teamsManager.getTeam1Color() + teamsManager.getTeam1Name() + "&f -" + num));
                player.sendActionBar(Util.stringToComponent("&f+" + num * 2 + " \uEB35"));

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addbalance " + player.getName() + " " + num * 2);
            } else {
                ChatManager.sendAll(gameManager.getWorld(), Util.stringToComponent(Util.componentToString(ChatManager.prefix) + teamsManager.getTeamColorByPlayer(player) + player.getName() + "&f разрушил пустое ядро " + teamsManager.getTeam1Color() + teamsManager.getTeam1Name()));
            }

            Location center = valuesManager.getTeam1Spawn(); // Центр радиуса (или другая нужная точка)
            double radius = 30.0;

            for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
                if (entity instanceof Player) {
                    Player target = (Player) entity;
                    if (!target.equals(player)) {
                        target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&7Ты отправлен на базу, так как был рядом с &nатакованым ядром")));
                    }
                    playerManager.playerToSpawn(target);
                }
            }

        } else if (block.getLocation().equals(valuesManager.getCore2())) {

            if (teamsManager.team2Scores.isEmpty()) return;
            if (teamsManager.team2Scores.containsKey(player)) return;

            gameManager.getWorld().spawnEntity(valuesManager.getTeam2Spawn().clone().add(0,30,0), EntityType.LIGHTNING);

            int num = 0;

            if (teamsManager.getTeam2Score() >= 3) {
                num = 3;
            } else {
                num = teamsManager.getTeam2Score();
            }

            if (num > 0) {

                teamsManager.addTeam2Score(-num);
                teamsManager.addPlayerScore(player, num);
                teamsManager.addTeamScoreByPlayer(player, num);
                ChatManager.sendAll(gameManager.getWorld(), Util.stringToComponent(Util.componentToString(ChatManager.prefix) + teamsManager.getTeamColorByPlayer(player) + player.getName() + "&f разрушил ядро " + teamsManager.getTeam2Color() + teamsManager.getTeam2Name() + " &f&l| " + teamsManager.getTeamColorByPlayer(player) + teamsManager.getTeamNameByPlayer(player) + "&f +" + num));
                ChatManager.titleAll(gameManager.getWorld(), Util.stringToComponent(""), Util.stringToComponent(teamsManager.getTeam2Color() + teamsManager.getTeam2Name() + "&f -" + num));
                player.sendActionBar(Util.stringToComponent("&f+" + num * 2 + " \uEB35"));

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addbalance " + player.getName() + " " + num * 2);
            } else {
                ChatManager.sendAll(gameManager.getWorld(), Util.stringToComponent(Util.componentToString(ChatManager.prefix) + teamsManager.getTeamColorByPlayer(player) + player.getName() + "&f разрушил пустое ядро " + teamsManager.getTeam2Color() + teamsManager.getTeam2Name()));
            }

            Location center = valuesManager.getTeam2Spawn(); // Центр радиуса (или другая нужная точка)
            double radius = 30.0;

            for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
                if (entity instanceof Player) {
                    Player target = (Player) entity;
                    if (!target.equals(player)) {
                        target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&7Ты отправлен на базу, так как был рядом с &nатакованым ядром")));
                    }
                    playerManager.playerToSpawn(target);
                }
            }

        } else if (block.getLocation().equals(valuesManager.getCore3())) {

            if (teamsManager.team3Scores.isEmpty()) return;
            if (teamsManager.team3Scores.containsKey(player)) return;

            gameManager.getWorld().spawnEntity(valuesManager.getTeam3Spawn().clone().add(0,30,0), EntityType.LIGHTNING);

            int num = 0;

            if (teamsManager.getTeam3Score() >= 3) {
                num = 3;
            } else {
                num = teamsManager.getTeam3Score();
            }

            if (num > 0) {

                teamsManager.addTeam3Score(-num);
                teamsManager.addPlayerScore(player, num);
                teamsManager.addTeamScoreByPlayer(player, num);
                ChatManager.sendAll(gameManager.getWorld(), Util.stringToComponent(Util.componentToString(ChatManager.prefix) + teamsManager.getTeamColorByPlayer(player) + player.getName() + "&f разрушил ядро " + teamsManager.getTeam3Color() + teamsManager.getTeam3Name() + " &f&l| " + teamsManager.getTeamColorByPlayer(player) + teamsManager.getTeamNameByPlayer(player) + "&f +" + num));
                ChatManager.titleAll(gameManager.getWorld(), Util.stringToComponent(""), Util.stringToComponent(teamsManager.getTeam3Color() + teamsManager.getTeam3Name() + "&f -" + num));
                player.sendActionBar(Util.stringToComponent("&f+" + num * 2 + " \uEB35"));

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addbalance " + player.getName() + " " + num * 2);
            } else {
                ChatManager.sendAll(gameManager.getWorld(), Util.stringToComponent(Util.componentToString(ChatManager.prefix) + teamsManager.getTeamColorByPlayer(player) + player.getName() + "&f разрушил пустое ядро " + teamsManager.getTeam3Color() + teamsManager.getTeam3Name()));
            }

            Location center = valuesManager.getTeam3Spawn(); // Центр радиуса (или другая нужная точка)
            double radius = 30.0;

            for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
                if (entity instanceof Player) {
                    Player target = (Player) entity;
                    if (!target.equals(player)) {
                        target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&7Ты отправлен на базу, так как был рядом с &nатакованым ядром")));
                    }
                    playerManager.playerToSpawn(target);
                }
            }

        } else if (block.getLocation().equals(valuesManager.getCore4())) {

            if (teamsManager.team4Scores.isEmpty()) return;
            if (teamsManager.team4Scores.containsKey(player)) return;

            gameManager.getWorld().spawnEntity(valuesManager.getTeam4Spawn().clone().add(0,30,0), EntityType.LIGHTNING);

            int num = 0;

            if (teamsManager.getTeam4Score() >= 3) {
                num = 3;
            } else {
                num = teamsManager.getTeam4Score();
            }

            if (num > 0) {

                teamsManager.addTeam4Score(-num);
                teamsManager.addPlayerScore(player, num);
                teamsManager.addTeamScoreByPlayer(player, num);
                ChatManager.sendAll(gameManager.getWorld(), Util.stringToComponent(Util.componentToString(ChatManager.prefix) + teamsManager.getTeamColorByPlayer(player) + player.getName() + "&f разрушил ядро " + teamsManager.getTeam4Color() + teamsManager.getTeam4Name() + " &f&l| " + teamsManager.getTeamColorByPlayer(player) + teamsManager.getTeamNameByPlayer(player) + "&f +" + num));
                ChatManager.titleAll(gameManager.getWorld(), Util.stringToComponent(""), Util.stringToComponent(teamsManager.getTeam4Color() + teamsManager.getTeam4Name() + "&f -" + num));
                player.sendActionBar(Util.stringToComponent("&f+" + num * 2 + " \uEB35"));

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addbalance " + player.getName() + " " + num * 2);
            } else {
                ChatManager.sendAll(gameManager.getWorld(), Util.stringToComponent(Util.componentToString(ChatManager.prefix) + teamsManager.getTeamColorByPlayer(player) + player.getName() + "&f разрушил пустое ядро " + teamsManager.getTeam4Color() + teamsManager.getTeam4Name()));
            }

            Location center = valuesManager.getTeam4Spawn(); // Центр радиуса (или другая нужная точка)
            double radius = 30.0;

            for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
                if (entity instanceof Player) {
                    Player target = (Player) entity;
                    if (!target.equals(player)) {
                        target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&7Ты отправлен на базу, так как был рядом с &nатакованым ядром")));
                    }
                    playerManager.playerToSpawn(target);
                }
            }

        }

    }


    @EventHandler
    private void ExplosiveHeart(EntityExplodeEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getEntity().getWorld());

        if (gameManager == null) return;


        if (!(gameManager.getGameState().equals(GameState.GAME))) return;

        for (Block block : e.blockList()) {

            if (block.getType().equals(Material.CONDUIT)) {

                BlockData data = block.getBlockData();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.CONDUIT);
                        block.setBlockData(data);
                    }
                }.runTaskLater(HeavenWarsBeta.getPlugin(), 5L);


            }

        }
    }

    @EventHandler
    private void ExplodeBlock(EntityExplodeEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getEntity().getWorld());

        if (gameManager == null) return;

        if (!(gameManager.getGameState().equals(GameState.GAME))) return;

        ValuesManager valuesManager = gameManager.getValuesManager();

        List<Block> blocks = new ArrayList<>();

        for (Block block : e.blockList()) {


            if (valuesManager.getUnbreakable().contains(block.getLocation())) {
                blocks.add(block);
            }

        }

        e.blockList().removeAll(blocks);

    }


    @EventHandler
    private void PickUpHeart(EntityPickupItemEvent e) {

        if (!(e.getEntity() instanceof Player)) return;

        GameManager gameManager = GameManager.getGameManager(e.getEntity().getWorld());

        if (gameManager == null) return;


        if (!(gameManager.getGameState().equals(GameState.GAME))) return;

        if (!e.getItem().getItemStack().getType().equals(Material.CONDUIT)) return;

        e.getItem().remove();
        e.setCancelled(true);

    }


    @EventHandler
    private void itemFall(ItemSpawnEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getEntity().getWorld());

        if (gameManager == null) return;


        if (!(gameManager.getGameState().equals(GameState.GAME))) return;

       RemoveItem(e.getEntity());

    }

    private void RemoveItem(Item item) {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (item == null) {
                    cancel();
                    return;
                }

                if (item.getLocation().getY() < 53) item.remove();

            }
        }.runTaskTimer(HeavenWarsBeta.getPlugin(), 20L, 20L);
    }

    @EventHandler
    private void PickUpWool(EntityPickupItemEvent e) {

        if (!(e.getEntity() instanceof Player)) return;

        GameManager gameManager = GameManager.getGameManager(e.getEntity().getWorld());

        if (gameManager == null) return;


        if (!(gameManager.getGameState().equals(GameState.GAME))) return;

        ItemStack item = e.getItem().getItemStack();

        if (!item.getType().equals(Material.WHITE_WOOL) && item.getType().toString().contains("_WOOL"))
            item.setType(Material.WHITE_WOOL);

    }


    @EventHandler
    private void TeamDamage(EntityDamageByEntityEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getEntity().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;

        Player damager = (Player) e.getDamager();
        Player entity = (Player) e.getEntity();

        TeamsManager teamsManager = gameManager.getTeamsManager();

        if (teamsManager.team1Scores.containsKey(damager) && teamsManager.team1Scores.containsKey(entity)) {
            e.setCancelled(true);
        }
        else if (teamsManager.team2Scores.containsKey(damager) && teamsManager.team2Scores.containsKey(entity)) {
            e.setCancelled(true);
        }
        else if (teamsManager.team3Scores.containsKey(damager) && teamsManager.team3Scores.containsKey(entity)) {
            e.setCancelled(true);
        }
        else if (teamsManager.team4Scores.containsKey(damager) && teamsManager.team4Scores.containsKey(entity)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void PlayerKill(EntityDeathEvent e) {

        GameManager gameManager = GameManager.getGameManager(e.getEntity().getWorld());

        if (gameManager == null) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        if (!(e.getEntity() instanceof Player)) return;

        Player damager = e.getEntity().getKiller();
        
        if (damager == null) return;
        
        Player entity = (Player) e.getEntity();

        if (entity.equals(damager)) return;

        damager.sendActionBar(Util.stringToComponent("&f+1 \uEB35"));

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"addbalance " + damager.getName() + " " + 1);

        damager.setHealth(20);
        damager.setFoodLevel(20);
        damager.setSaturation(20);
        damager.playSound(damager, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);

        entity.getWorld().spawnParticle(Particle.BLOCK_CRACK, entity.getLocation(), 70, 0, 1,0, 0.5, Material.RED_CONCRETE.createBlockData());


    }

}
