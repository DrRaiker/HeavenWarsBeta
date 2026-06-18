package drraiker.heavenWarsBeta.Menus;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Items.MenuItems;
import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.GameState;
import drraiker.heavenWarsBeta.Managers.ValuesManager;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.MenuHolder;
import drraiker.heavenWarsBeta.Util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChooseWorldMenuSAVED {

    public static void openInventory(Player player) {

        Inventory inv = Bukkit.createInventory(new MenuHolder("worldsMenu"), 27, Util.stringToComponent(Configs.config.getString("worlds-menu.name")));

        for (int i = 0; i < 9; i++) {
            inv.setItem(i, MenuItems.holder);
        }
        for (int i = 18; i < 27; i++) {
            inv.setItem(i, MenuItems.holder);
        }
        inv.setItem(9, MenuItems.holder);
        inv.setItem(17, MenuItems.holder);

        int index = 0;

        for (GameManager gameManager : GameManager.worldMangers.values()) {

            ValuesManager valuesManager = gameManager.getValuesManager();
            Location lobby = valuesManager.getLobbySpawn();

            index++;
            ItemStack item = new ItemStack(Material.WHITE_WOOL, index);

            NBT.modify(item, nbt -> {
                nbt.setBoolean("isButton", true);
                nbt.setString("world", gameManager.getLobby().getName());
                nbt.setDouble("x", lobby.x());
                nbt.setDouble("y", lobby.y());
                nbt.setDouble("z", lobby.z());
            });

            ItemMeta itemMeta = item.getItemMeta();

            itemMeta.displayName(Util.stringToComponent("&f&lИгровой мир " + index));

            List<Component> lore = new ArrayList<>();

            lore.add(Util.stringToComponent(" "));
            lore.add(Util.stringToComponent("&7Карта: &f" + gameManager.getValuesManager().getMap()));
            lore.add(Util.stringToComponent("&7Игроков: &f" +(gameManager.getWorld().getPlayerCount() + gameManager.getLobby().getPlayerCount())));
            if (gameManager.getGameState().equals(GameState.GAME)) {
                lore.add(Util.stringToComponent("&7Состояние: &fИдёт игра"));
            } else {
                lore.add(Util.stringToComponent("&7Состояние: &bОжидание игроков"));
            }
            lore.add(Util.stringToComponent(" "));


            itemMeta.lore(lore);
            item.setItemMeta(itemMeta);

            inv.addItem(item);

        }

        player.openInventory(inv);

    }

}
