package drraiker.heavenWarsBeta;

import drraiker.heavenWarsBeta.Commands.MainCommand;
import drraiker.heavenWarsBeta.Items.Equipment;
import drraiker.heavenWarsBeta.Items.Items;
import drraiker.heavenWarsBeta.Items.MenuItems;
import drraiker.heavenWarsBeta.Items.ShopItems;
import drraiker.heavenWarsBeta.Listeners.*;
import drraiker.heavenWarsBeta.Managers.Crate;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Menus.TeamChooseMenu;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.itemslogic.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class HeavenWarsBeta extends JavaPlugin {
    private static HeavenWarsBeta plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;

        new Configs().load(this);

        Items.Init();
        MenuItems.Init();
        Equipment.Init();
        ShopItems.Init();

        getCommand("heaven-wars-beta").setExecutor(new MainCommand(this));

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinLeaveGame(), this);
        pluginManager.registerEvents(new Blocked(), this);
        pluginManager.registerEvents(new Spectator(), this);
        pluginManager.registerEvents(new ChooseWorldMenuListener(), this);
        pluginManager.registerEvents(new TeamInteraction(), this);
        pluginManager.registerEvents(new TeamChooseMenuListener(), this);
        pluginManager.registerEvents(new ColorChooseMenuListener(), this);
        pluginManager.registerEvents(new Mine(), this);
        pluginManager.registerEvents(new FlameMine(), this);
        pluginManager.registerEvents(new ImpulseGrenade(), this);
        pluginManager.registerEvents(new ShopMenuListener(), this);
        pluginManager.registerEvents(new ModeMenuListener(), this);
        pluginManager.registerEvents(new PickUpRandomItem(), this);
        pluginManager.registerEvents(new Crate(), this);
        pluginManager.registerEvents(new HeartAttack(), this);
        pluginManager.registerEvents(new RepairHeart(), this);
        pluginManager.registerEvents(new Wall(), this);
        pluginManager.registerEvents(new Floor(), this);


        List<String> keys = new ArrayList<>(Configs.config.getConfigurationSection("worlds").getKeys(false));

        for (String key : keys) {
            GameManager.create(Configs.config.getConfigurationSection("worlds." + key));
        }

        new Expansions(this).register();

    }

    public static HeavenWarsBeta getPlugin() {
        return plugin;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
