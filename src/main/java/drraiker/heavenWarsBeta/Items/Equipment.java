package drraiker.heavenWarsBeta.Items;

import drraiker.heavenWarsBeta.Managers.TeamsManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;

public class Equipment {

    public static HashMap<String, ItemStack> helmets = new HashMap<>();
    public static HashMap<String, ItemStack> chestplates = new HashMap<>();


    public static ItemStack Sword;
    public static ItemStack Shears;
    public static ItemStack FlintAndSteel;
    public static ItemStack Bow;
    public static ItemStack Arrow;
    public static ItemStack Shield;



    public static void Init() {

        for (String string : TeamsManager.colors) {
            helmets.put(string, createHelmet(hexToColor(string.replace("&","").replace("l",""))));
            chestplates.put(string, createChestplate(hexToColor(string.replace("&","").replace("l",""))));
        }

        Sword();
        Bow();
        Arrow();
        Shears();
        FlintAndSteel();
        Shield();

    }

    private static ItemStack createHelmet(Color color) {

        ItemStack item = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_DYE,ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);

        return item;

    }

    private static ItemStack createChestplate(Color color) {

        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(color);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_DYE,ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, true);

        item.setItemMeta(meta);

        return item;

    }

    private static void Sword() {

        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        item.setItemMeta(meta);

        Sword = item;

    }

    private static void Bow() {

        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

        item.setItemMeta(meta);

        Bow = item;

    }

    private static void Arrow() {

        ItemStack item = new ItemStack(Material.ARROW);

        Arrow = item;

    }

    private static void Shears() {

        ItemStack item = new ItemStack(Material.SHEARS);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        item.setItemMeta(meta);

        Shears = item;

    }

    private static void Shield() {

        ItemStack item = new ItemStack(Material.SHIELD);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        item.setItemMeta(meta);

        Shield = item;

    }

    private static void FlintAndSteel() {

        ItemStack item = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE,ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.MENDING, 1, true);

        item.setItemMeta(meta);

        FlintAndSteel = item;

    }


    private static Color hexToColor(String hex) {
        try {

            hex = hex.replace("#", "");
            int rgb = Integer.parseInt(hex, 16);
            return Color.fromRGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);  // Извлекаем R, G, B компоненты
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
