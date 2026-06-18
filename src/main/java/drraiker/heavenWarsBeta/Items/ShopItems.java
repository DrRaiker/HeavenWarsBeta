package drraiker.heavenWarsBeta.Items;

import de.tr7zw.nbtapi.NBT;
import drraiker.heavenWarsBeta.Util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopItems {

    public static ItemStack Shop;
    public static ItemStack Mine;
    public static ItemStack FlameMine;
    public static ItemStack ImpulseGrenade;
    public static ItemStack wall;
    public static ItemStack floor;

    public static void Init() {

        Shop();
        ImpulseGrenade();
        Mine();
        FlameMine();
        wall = wall();
        floor = floor();

    }

    //Шерсть - ""     TNT - ""
    private static ItemStack floor() {

        ItemStack item = new ItemStack(Material.SCUTE);

        NBT.modify(item, nbt->{
            nbt.setBoolean("isButton", true);
            nbt.setBoolean("isFloor", true);
        });
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Util.stringToComponent("&fСпасательная платформа"));
        meta.setCustomModelData(55001);
        List<Component> lore = new ArrayList<>();

        lore.add(Util.stringToComponent("&fСоздаёт платформу под игроком"));
        lore.add(Util.stringToComponent("&f"));
        lore.add(Util.stringToComponent("&fЦена:"));
        lore.add(Util.stringToComponent("&f4 \uEB56 1 \uEB57"));

        meta.lore(lore);
        item.setItemMeta(meta);

        return item;

    }

    private static ItemStack wall() {

        ItemStack item = new ItemStack(Material.SNOWBALL);

        NBT.modify(item, nbt->{
            nbt.setBoolean("isButton", true);
            nbt.setBoolean("isWall", true);
        });
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Util.stringToComponent("&fПортативная стена"));
        meta.setCustomModelData(55001);
        List<Component> lore = new ArrayList<>();

        lore.add(Util.stringToComponent("&fПризывает стену, повёрнутую по взгляду игрока"));
        lore.add(Util.stringToComponent("&f"));
        lore.add(Util.stringToComponent("&fЦена:"));
        lore.add(Util.stringToComponent("&f6 \uEB56 1 \uEB57"));

        meta.lore(lore);
        item.setItemMeta(meta);

        return item;

    }

    private static void Shop() {

        ItemStack item = new ItemStack(Material.CLOCK);

        NBT.modify(item, nbt->{
            nbt.setBoolean("isButton", true);
            nbt.setBoolean("isClock", true);
        });

        ItemMeta meta = item.getItemMeta();

        meta.displayName(Util.stringToComponent("&6&lМагазин"));
        item.setItemMeta(meta);

        Shop = item;

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
        List<Component> lore = new ArrayList<>();

        lore.add(Util.stringToComponent("&fПри взрыве отталкивает от себя"));
        lore.add(Util.stringToComponent("&fХороший способ перемещения"));
        lore.add(Util.stringToComponent("&f"));
        lore.add(Util.stringToComponent("&fЦена:"));
        lore.add(Util.stringToComponent("&f5 \uEB56 5 \uEB57"));

        meta.lore(lore);
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

        List<Component> lore = new ArrayList<>();
        lore.add(Util.stringToComponent("&fВзрывается при косании игрока или предмета"));
        lore.add(Util.stringToComponent("&fУничтожает блоки"));
        lore.add(Util.stringToComponent("&f"));
        lore.add(Util.stringToComponent("&fЦена:"));
        lore.add(Util.stringToComponent("&f2 \uEB56 2 \uEB57"));

        meta.lore(lore);
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

        List<Component> lore = new ArrayList<>();
        lore.add(Util.stringToComponent("&fВзрывается при косании игрока или предмета"));
        lore.add(Util.stringToComponent("&fПоджигает блоки"));
        lore.add(Util.stringToComponent("&f"));
        lore.add(Util.stringToComponent("&fЦена:"));
        lore.add(Util.stringToComponent("&f3 \uEB56 3 \uEB57"));

        meta.lore(lore);
        item.setItemMeta(meta);

        FlameMine = item;
    }


}
