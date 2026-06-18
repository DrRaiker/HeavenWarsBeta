package drraiker.heavenWarsBeta.Listeners;

import drraiker.heavenWarsBeta.Managers.*;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class RepairHeart implements Listener {

    @EventHandler
    private void click(PlayerInteractEvent e) {
        GameManager gameManager = GameManager.getGameManager(e.getPlayer().getWorld());
        if (gameManager == null) return;
        if (e.getClickedBlock() == null) return;

        if (!e.getClickedBlock().getType().equals(Material.CONDUIT)) return;

        if (!gameManager.getGameState().equals(GameState.GAME)) return;

        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        ValuesManager valuesManager = gameManager.getValuesManager();
        TeamsManager teamsManager = gameManager.getTeamsManager();
        PlayerManager playerManager = gameManager.getPlayerManager();

        World world = gameManager.getWorld();


        if (block.getLocation().equals(valuesManager.getCore1())) {

            if (!teamsManager.team1Scores.containsKey(player)) return;

            int health = teamsManager.getTeam1Health();
            int needRepair = Configs.config.getInt("max-health") - health;

            if (needRepair == 0) {
                player.sendActionBar(Util.stringToComponent(teamsManager.getTeam1Color() + "Ядро " + HeartAttack.getHealthBar(Configs.config.getInt("max-health"), teamsManager.getTeam1Health(), teamsManager.getTeam1Color())));
                return;
            }

            int price = needRepair * 2;

            int amount = Util.getItemAmount(player, Material.WHITE_WOOL);

            if (amount % 2 != 0) {
                amount -= 1;
            }

            if (amount == 0) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eблоков&c для ремонта")));
                return;
            }

            if (amount < price) {
                price = amount;
            }


            Util.removeItem(player, Material.WHITE_WOOL, price);

            teamsManager.addTeam1Health(price / 2);
            player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&eЗдоровье ядра восстановлено до &f" + teamsManager.getTeam1Health() + "&7/" + teamsManager.getTeam1Color() + Configs.config.getInt("max-health"))));
            world.playSound(valuesManager.getCore1().clone().toCenterLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            world.spawnParticle(Particle.VILLAGER_HAPPY, valuesManager.getCore1().clone().toCenterLocation(), 10, 0.3, 0.3, 0.3, 0.2);

        } else if (block.getLocation().equals(valuesManager.getCore2())) {
            if (!teamsManager.team2Scores.containsKey(player)) return;

            int health = teamsManager.getTeam2Health();
            int needRepair = Configs.config.getInt("max-health") - health;

            if (needRepair == 0) {
                player.sendActionBar(Util.stringToComponent(teamsManager.getTeam2Color() + "Ядро " + HeartAttack.getHealthBar(Configs.config.getInt("max-health"), teamsManager.getTeam2Health(), teamsManager.getTeam2Color())));
                return;
            }

            int price = needRepair * 2;

            int amount = Util.getItemAmount(player, Material.WHITE_WOOL);

            if (amount % 2 != 0) {
                amount -= 1;
            }

            if (amount == 0) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eблоков&c для ремонта")));
                return;
            }

            if (amount < price) {
                price = amount;
            }


            Util.removeItem(player, Material.WHITE_WOOL, price);

            teamsManager.addTeam2Health(price / 2);
            player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&eЗдоровье ядра восстановлено до &f" + teamsManager.getTeam2Health() + "&7/" + teamsManager.getTeam2Color() + Configs.config.getInt("max-health"))));
            world.playSound(valuesManager.getCore1().clone().toCenterLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            world.spawnParticle(Particle.VILLAGER_HAPPY, valuesManager.getCore1().clone().toCenterLocation(), 10, 0.3, 0.3, 0.3, 0.2);

        } else if (block.getLocation().equals(valuesManager.getCore3())) {
            if (!teamsManager.team3Scores.containsKey(player)) return;

            int health = teamsManager.getTeam3Health();
            int needRepair = Configs.config.getInt("max-health") - health;

            if (needRepair == 0) {
                player.sendActionBar(Util.stringToComponent(teamsManager.getTeam3Color() + "Ядро " + HeartAttack.getHealthBar(Configs.config.getInt("max-health"), teamsManager.getTeam3Health(), teamsManager.getTeam3Color())));
                return;
            }

            int price = needRepair * 2;

            int amount = Util.getItemAmount(player, Material.WHITE_WOOL);

            if (amount % 2 != 0) {
                amount -= 1;
            }

            if (amount == 0) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eблоков&c для ремонта")));
                return;
            }

            if (amount < price) {
                price = amount;
            }


            Util.removeItem(player, Material.WHITE_WOOL, price);

            teamsManager.addTeam3Health(price / 2);
            player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&eЗдоровье ядра восстановлено до &f" + teamsManager.getTeam3Health() + "&7/" + teamsManager.getTeam3Color() + Configs.config.getInt("max-health"))));
            world.playSound(valuesManager.getCore1().clone().toCenterLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            world.spawnParticle(Particle.VILLAGER_HAPPY, valuesManager.getCore1().clone().toCenterLocation(), 10, 0.3, 0.3, 0.3, 0.2);

        } else if (block.getLocation().equals(valuesManager.getCore4())) {
            if (!teamsManager.team4Scores.containsKey(player)) return;

            int health = teamsManager.getTeam4Health();
            int needRepair = Configs.config.getInt("max-health") - health;

            if (needRepair == 0) {
                player.sendActionBar(Util.stringToComponent(teamsManager.getTeam4Color() + "Ядро " + HeartAttack.getHealthBar(Configs.config.getInt("max-health"), teamsManager.getTeam4Health(), teamsManager.getTeam4Color())));
                return;
            }

            int price = needRepair * 2;

            int amount = Util.getItemAmount(player, Material.WHITE_WOOL);

            if (amount % 2 != 0) {
                amount -= 1;
            }

            if (amount == 0) {
                player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&cНедостаточно &eблоков&c для ремонта")));
                return;
            }

            if (amount < price) {
                price = amount;
            }


            Util.removeItem(player, Material.WHITE_WOOL, price);

            teamsManager.addTeam4Health(price / 2);
            player.sendMessage(ChatManager.prefix.append(Util.stringToComponent("&eЗдоровье ядра восстановлено до &f" + teamsManager.getTeam4Health() + "&7/" + teamsManager.getTeam4Color() + Configs.config.getInt("max-health"))));
            world.playSound(valuesManager.getCore1().clone().toCenterLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            world.spawnParticle(Particle.VILLAGER_HAPPY, valuesManager.getCore1().clone().toCenterLocation(), 10, 0.3, 0.3, 0.3, 0.2);

        }
    }
}
