package drraiker.heavenWarsBeta.Items;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuItems {

    public static ItemStack holder;
    public static ItemStack classic;
    public static ItemStack beta;

    public static void Init() {
        holder = holder();
        classic = classic();
        beta = beta();
    }

    private static ItemStack holder() {
        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Util.stringToComponent(" "));
        meta.setCustomModelData(1533);

        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack classic() {
        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
        NBT.modify(item, nbt ->{
            nbt.setBoolean("isButton", true);
            nbt.setBoolean("isClassic", true);
        });
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Util.stringToComponent("&f&lКлассический режим"));
        meta.setCustomModelData(1533);

        List<Component> lore = new ArrayList<>();

        lore.add(Util.stringToComponent("Оригинальная Мини-Игра СГМ"));
        lore.add(Util.stringToComponent("Присоединись к одной из команд"));
        lore.add(Util.stringToComponent("Унижтожай противников!"));
        lore.add(Util.stringToComponent("Доберись до базы и атакуй ядро!"));
        lore.add(Util.stringToComponent(""));
        lore.add(Util.stringToComponent("Команда с &nнаименьшим&f кол-вом смертей"));
        lore.add(Util.stringToComponent("&6Побеждает!"));
        lore.add(Util.stringToComponent(""));
        lore.add(Util.stringToComponent("Этот режим всегда стабилен"));
        lore.add(Util.stringToComponent("Он не может изменится"));

        meta.lore(lore);

        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack beta() {
        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
        NBT.modify(item, nbt ->{
            nbt.setBoolean("isButton", true);
            nbt.setBoolean("isBeta", true);
        });
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Util.stringToComponent("&6&lЭкспериментальный режим"));
        meta.setCustomModelData(1533);

        List<Component> lore = new ArrayList<>();

        lore.add(Util.stringToComponent("&fОригинальная Мини-Игра СГМ"));
        lore.add(Util.stringToComponent("&fПрисоединись к одной из команд"));
        lore.add(Util.stringToComponent("&fУнижтожай противников!"));
        lore.add(Util.stringToComponent("&fДоберись до базы и атакуй ядро!"));
        lore.add(Util.stringToComponent("&f"));
        lore.add(Util.stringToComponent("&fКоманда с &nнаибольшим&f кол-вом убийств"));
        lore.add(Util.stringToComponent("&6Побеждает!"));
        lore.add(Util.stringToComponent(""));
        lore.add(Util.stringToComponent("Этот режим меняется и обновляется"));
        lore.add(Util.stringToComponent("Больше цветов!"));
        lore.add(Util.stringToComponent("Больше карта!"));
        lore.add(Util.stringToComponent("Больше веселья!"));

        meta.lore(lore);

        item.setItemMeta(meta);
        return item;
    }

}
