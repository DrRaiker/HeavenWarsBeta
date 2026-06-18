package drraiker.heavenWarsBeta.Listeners;

import drraiker.heavenWarsBeta.Managers.*;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class HeartAttack implements Listener {

    @EventHandler
    private void lightningDamage(EntityDamageEvent e) {
        GameManager gameManager = GameManager.getGameManager(e.getEntity().getWorld());

        if (gameManager == null) return;

        if (!e.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING)) return;

        e.setCancelled(true);
    }

    @EventHandler
    private void leftClick(PlayerInteractEvent e) {
        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());

        if (gameManager == null) return;

        if (!e.getAction().isLeftClick()) return;

        if (e.getClickedBlock() == null) return;

        if (!e.getClickedBlock().getType().equals(Material.CONDUIT)) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        ValuesManager valuesManager = gameManager.getValuesManager();
        TeamsManager teamsManager = gameManager.getTeamsManager();
        PlayerManager playerManager = gameManager.getPlayerManager();

        World world = gameManager.getWorld();

        int damage = (int) Util.getAttackDamage(player);

        if (block.getLocation().equals(valuesManager.getCore1())) {

            if (!teamsManager.team1Scores.containsKey(player)) {

                if (System.currentTimeMillis() > teamsManager.getTeam1Cooldown()) {

                    if (teamsManager.getTeam1Health() == Configs.config.getInt("max-health")) {
                        for (Player target : teamsManager.team1Scores.keySet()) {
                            target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cВаше &nядро&c атаковано и &eтребует ремонта!")));
                        }
                    }

                    world.spawnParticle(Particle.DAMAGE_INDICATOR, valuesManager.getCore1().clone().toCenterLocation(), 10);
                    world.playSound(valuesManager.getCore1().clone().toCenterLocation(), Sound.ENTITY_GENERIC_HURT, 1, 0.5f);

                    teamsManager.addTeam1Health(-damage);
                    teamsManager.setTeam1Cooldown(System.currentTimeMillis() + 1000L);

                    if (teamsManager.getTeam1Health() == 0) {
                        if (teamsManager.team1Scores.isEmpty()) return;
                        if (teamsManager.team1Scores.containsKey(player)) return;

                        gameManager.getWorld().spawnEntity(valuesManager.getTeam1Spawn(), EntityType.LIGHTNING);

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
                                if (!target.equals(player) && !teamsManager.team1Scores.containsKey(target)) {
                                    target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&7Ты отправлен на базу, так как был рядом с &nатакованым ядром")));
                                }
                                playerManager.playerToSpawn(target);
                            }
                        }
                        
                        for (Player target : teamsManager.team1Scores.keySet()) {
                            target.teleport(valuesManager.getTeam1Spawn());
                        }

                        teamsManager.setTeam1Health(Configs.config.getInt("max-health"));
                        
                    }

                }
            }
            player.sendActionBar(Util.stringToComponent(teamsManager.getTeam1Color() + "Ядро " + getHealthBar(Configs.config.getInt("max-health"), teamsManager.getTeam1Health(), teamsManager.getTeam1Color())));
        } else if (block.getLocation().equals(valuesManager.getCore2())) {

            if (!teamsManager.team2Scores.containsKey(player)) {

                if (System.currentTimeMillis() > teamsManager.getTeam2Cooldown()) {

                    if (teamsManager.getTeam2Health() == Configs.config.getInt("max-health")) {
                        for (Player target : teamsManager.team2Scores.keySet()) {
                            target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cВаше &nядро&c атаковано и &eтребует ремонта!")));
                        }
                    }

                    world.spawnParticle(Particle.DAMAGE_INDICATOR, valuesManager.getCore1().clone().toCenterLocation(), 10);
                    world.playSound(valuesManager.getCore1().clone().toCenterLocation(), Sound.ENTITY_GENERIC_HURT, 1, 0.5f);

                    teamsManager.addTeam2Health(-damage);
                    teamsManager.setTeam2Cooldown(System.currentTimeMillis() + 1000L);

                    if (teamsManager.getTeam2Health() == 0) {
                        if (teamsManager.team2Scores.isEmpty()) return;
                        if (teamsManager.team2Scores.containsKey(player)) return;

                        gameManager.getWorld().spawnEntity(valuesManager.getTeam2Spawn(), EntityType.LIGHTNING);

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
                                if (!target.equals(player) && !teamsManager.team2Scores.containsKey(target)) {
                                    target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&7Ты отправлен на базу, так как был рядом с &nатакованым ядром")));
                                }
                                playerManager.playerToSpawn(target);
                            }
                        }

                        for (Player target : teamsManager.team2Scores.keySet()) {
                            target.teleport(valuesManager.getTeam2Spawn());
                        }

                        teamsManager.setTeam2Health(Configs.config.getInt("max-health"));

                    }

                }
            }
            player.sendActionBar(Util.stringToComponent(teamsManager.getTeam2Color() + "Ядро " + getHealthBar(Configs.config.getInt("max-health"), teamsManager.getTeam2Health(), teamsManager.getTeam2Color())));

        } else if (block.getLocation().equals(valuesManager.getCore3())) {

            if (!teamsManager.team3Scores.containsKey(player)) {

                if (System.currentTimeMillis() > teamsManager.getTeam3Cooldown()) {

                    if (teamsManager.getTeam3Health() == Configs.config.getInt("max-health")) {
                        for (Player target : teamsManager.team3Scores.keySet()) {
                            target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cВаше &nядро&c атаковано и &eтребует ремонта!")));
                        }
                    }

                    world.spawnParticle(Particle.DAMAGE_INDICATOR, valuesManager.getCore1().clone().toCenterLocation(), 10);
                    world.playSound(valuesManager.getCore1().clone().toCenterLocation(), Sound.ENTITY_GENERIC_HURT, 1, 0.5f);

                    teamsManager.addTeam3Health(-damage);
                    teamsManager.setTeam3Cooldown(System.currentTimeMillis() + 1000L);

                    if (teamsManager.getTeam3Health() == 0) {
                        if (teamsManager.team3Scores.isEmpty()) return;
                        if (teamsManager.team3Scores.containsKey(player)) return;

                        gameManager.getWorld().spawnEntity(valuesManager.getTeam3Spawn(), EntityType.LIGHTNING);

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
                                if (!target.equals(player) && !teamsManager.team3Scores.containsKey(target)) {
                                    target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&7Ты отправлен на базу, так как был рядом с &nатакованым ядром")));
                                }
                                playerManager.playerToSpawn(target);
                            }
                        }

                        for (Player target : teamsManager.team3Scores.keySet()) {
                            target.teleport(valuesManager.getTeam3Spawn());
                        }

                        teamsManager.setTeam3Health(Configs.config.getInt("max-health"));

                    }

                }
            }
            player.sendActionBar(Util.stringToComponent(teamsManager.getTeam3Color() + "Ядро " + getHealthBar(Configs.config.getInt("max-health"), teamsManager.getTeam3Health(), teamsManager.getTeam3Color())));

        } else if (block.getLocation().equals(valuesManager.getCore4())) {

            if (!teamsManager.team4Scores.containsKey(player)) {

                if (System.currentTimeMillis() > teamsManager.getTeam4Cooldown()) {

                    if (teamsManager.getTeam4Health() == Configs.config.getInt("max-health")) {
                        for (Player target : teamsManager.team4Scores.keySet()) {
                            target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cВаше &nядро&c атаковано и &eтребует ремонта!")));
                        }
                    }

                    world.spawnParticle(Particle.DAMAGE_INDICATOR, valuesManager.getCore1().clone().toCenterLocation(), 10);
                    world.playSound(valuesManager.getCore1().clone().toCenterLocation(), Sound.ENTITY_GENERIC_HURT, 1, 0.5f);

                    teamsManager.addTeam4Health(-damage);
                    teamsManager.setTeam4Cooldown(System.currentTimeMillis() + 1000L);

                    if (teamsManager.getTeam4Health() == 0) {
                        if (teamsManager.team4Scores.isEmpty()) return;
                        if (teamsManager.team4Scores.containsKey(player)) return;

                        gameManager.getWorld().spawnEntity(valuesManager.getTeam4Spawn(), EntityType.LIGHTNING);

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
                                if (!target.equals(player) && !teamsManager.team4Scores.containsKey(target)) {
                                    target.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&7Ты отправлен на базу, так как был рядом с &nатакованым ядром")));
                                }
                                playerManager.playerToSpawn(target);
                            }
                        }

                        for (Player target : teamsManager.team4Scores.keySet()) {
                            target.teleport(valuesManager.getTeam4Spawn());
                        }

                        teamsManager.setTeam4Health(Configs.config.getInt("max-health"));

                    }

                }
            }
            player.sendActionBar(Util.stringToComponent(teamsManager.getTeam4Color() + "Ядро " + getHealthBar(Configs.config.getInt("max-health"), teamsManager.getTeam4Health(), teamsManager.getTeam4Color())));


        }

    }


    public static String getHealthBar(int max, int value, String color) {
        int size = 100;

        int coloredBars = (int) Math.round(((double) value / max) * size);
        StringBuilder bar = new StringBuilder("&f&l|" + color.replace("&l", "") + "\uf811");

        for (int i = 0; i < coloredBars; i++) {
            bar.append("|\uf811");
        }

        if (max != value) {
            bar.append("&f|\uf811");
        }

        for (int i = coloredBars+1; i < size; i++) {
            bar.append("&7|\uf811");
        }

        return bar.append("&f&l|").toString();
    }


}
