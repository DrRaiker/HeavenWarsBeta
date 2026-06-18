package drraiker.heavenWarsBeta.Items;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

    public static ItemStack white_wool;
    public static ItemStack tnt;
    public static ItemStack teamSelect;

    public static ItemStack Crate;
    public static ItemStack CrateFly;

    public static ItemStack Mine;
    public static ItemStack FlameMine;
    public static ItemStack ImpulseGrenade;
    public static ItemStack wall;
    public static ItemStack floor;

    public static void Init() {
        white_wool = white_wool();
        tnt = tnt();
        teamSelect = teamSelect();
        Crate();
        CrateFly();

        wall = wall();
        floor = floor();
        ImpulseGrenade();
        Mine();
        FlameMine();
    }


    private static ItemStack floor() {

        ItemStack item = new ItemStack(Material.SCUTE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Util.stringToComponent("&fСпасательная платформа"));
        meta.setCustomModelData(55001);
        item.setItemMeta(meta);

        return item;

    }

    private static ItemStack wall() {

        ItemStack item = new ItemStack(Material.SNOWBALL);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Util.stringToComponent("&fПортативная стена"));
        meta.setCustomModelData(55001);
        item.setItemMeta(meta);

        return item;

    }

    private static void ImpulseGrenade() {

        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
        NBT.modify(item, nbt->{
            nbt.setBoolean("isButton", true);
            nbt.setBoolean("isImpulse", true);
        });
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Util.stringToComponent("&fИмпульсная граната"));
        meta.setCustomModelData(55001);
        item.setItemMeta(meta);

        ImpulseGrenade = item;

    }

    private static void Mine() {

        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
        NBT.modify(item, nbt -> {
            nbt.setBoolean("isButton", true);
            nbt.setBoolean("isMine", true);
        });

        ItemMeta meta = item.getItemMeta();
        meta.displayName(Util.stringToComponent("&fМина"));
        meta.setCustomModelData(55002);

        item.setItemMeta(meta);

        Mine = item;
    }

    private static void FlameMine() {

        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
        NBT.modify(item, nbt -> {
            nbt.setBoolean("isButton", true);
            nbt.setBoolean("isFlameMine", true);
        });

        ItemMeta meta = item.getItemMeta();
        meta.displayName(Util.stringToComponent("&fОгненная мина"));
        meta.setCustomModelData(55003);

        item.setItemMeta(meta);

        FlameMine = item;
    }
    private static void Crate() {

        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(55004);
        item.setItemMeta(meta);

        Crate = item;

    }

    private static void CrateFly() {

        ItemStack item = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(55005);
        item.setItemMeta(meta);

        CrateFly = item;

    }

    private static ItemStack white_wool() {
        ItemStack item = new ItemStack(Material.WHITE_WOOL);
        ItemMeta meta = item.getItemMeta();

        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack tnt() {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();

        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack teamSelect() {

        ItemStack item = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.MENDING,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.displayName(Util.stringToComponent("&6&lВыбор Команды"));
        item.setItemMeta(meta);

        return item;
    }
}
