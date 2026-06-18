package drraiker.heavenWarsBeta.Commands;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Items.Items;
import drraiker.heavenWarsBeta.Listeners.HeartAttack;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.MapManager;
import drraiker.heavenWarsBeta.Managers.TeamsManager;
import drraiker.heavenWarsBeta.Menus.ChooseWorldMenu;
import drraiker.heavenWarsBeta.Menus.ChooseWorldMenuSAVED;
import drraiker.heavenWarsBeta.Menus.ModeMenu;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {

    HeavenWarsBeta plugin;

    public MainCommand(HeavenWarsBeta plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Эту команду может использовать только игрок.");
            return true;
        }

        if (args.length == 0) {
            ChooseWorldMenu.openInventory(player);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("mode") ) {
                ModeMenu.openInventory(player);
                return true;
            } else if (args[0].equalsIgnoreCase("bar") ) {
                player.sendMessage(Util.stringToComponent(HeartAttack.getHealthBar(40, 27, "&2")));
                return true;
            }
        }
        if (player.hasPermission("heaven-wars-beta.admin")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reset-map") ) {

                    GameManager gameManager = GameManager.getGameManager(player.getWorld());

                    if (gameManager == null) return false;

                    gameManager.getMapManager().reset();

                } else if (args[0].equalsIgnoreCase("start") ) {

                    GameManager gameManager = GameManager.getGameManagerByLobby(player.getWorld());

                    if (gameManager == null) return false;

                    gameManager.startGame();

                } else if (args[0].equalsIgnoreCase("test") ) {

                    player.getInventory().addItem(Items.wall);
                    player.getInventory().addItem(Items.floor);

                }
            }
            if (args.length > 1) {
                if (args[0].equalsIgnoreCase("save") && args.length == 8) {
                    try {
                        int x1 = Integer.parseInt(args[1]);
                        int y1 = Integer.parseInt(args[2]);
                        int z1 = Integer.parseInt(args[3]);
                        int x2 = Integer.parseInt(args[4]);
                        int y2 = Integer.parseInt(args[5]);
                        int z2 = Integer.parseInt(args[6]);
                        String fileName = args[7] + ".json";

                        Location pos1 = new Location(player.getWorld(), x1, y1, z1);
                        Location pos2 = new Location(player.getWorld(), x2, y2, z2);

                        MapManager.saveStructure(pos1, pos2, fileName);
                        player.sendMessage("Постройка сохранена в файл " + fileName);
                    } catch (NumberFormatException e) {
                        player.sendMessage("Координаты должны быть числами.");
                    }
                } else if (args[0].equalsIgnoreCase("load") && args.length == 2) {
                    String fileName = args[1] + ".json";
                    MapManager.loadStructure(player.getWorld(), fileName);
                    player.sendMessage("Постройка загружена из файла " + fileName);
                } else if (args[0].equalsIgnoreCase("set-min") && args.length == 2) {

                    int minutes = 5;

                    try {
                        minutes = Integer.parseInt(args[1]);
                    } catch (NumberFormatException ignored) {}

                    GameManager gameManager = GameManager.getGameManager(player.getWorld());

                    if (gameManager == null) return false;

                    gameManager.setTimer_m(minutes);

                }
            }
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player player)) {
            return Collections.emptyList();
        }
        if (player.hasPermission("heaven-wars-beta.admin")) {
            if (args.length == 1) {
                return List.of("save", "load", "reset-map", "start", "test", "mode", "set-min", "bar");
            }

            if (args[0].equalsIgnoreCase("save")) {
                Location loc = player.getLocation();
                switch (args.length) {
                    case 2, 5:
                        return List.of(String.valueOf(loc.getBlockX()));
                    case 3, 6:
                        return List.of(String.valueOf(loc.getBlockY()));
                    case 4, 7:
                        return List.of(String.valueOf(loc.getBlockZ()));
                }
            } else if (args[0].equalsIgnoreCase("load") && args.length == 2) {
                // Подсказки для имени файла при загрузке
                File folder = new File(plugin.getDataFolder().getPath());
                File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
                if (files != null) {
                    List<String> fileNames = new ArrayList<>();
                    for (File file : files) {
                        fileNames.add(file.getName().replace(".json", ""));
                    }
                    return fileNames;
                }
            }
        }
        return Collections.emptyList();
    }
}
