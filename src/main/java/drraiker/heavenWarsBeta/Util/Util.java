package drraiker.heavenWarsBeta.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Random;

public class Util {


    public static Component stringToComponent(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string).decoration(TextDecoration.ITALIC, false).colorIfAbsent(NamedTextColor.WHITE);
    }

    public static String componentToString(Component component) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }

    public static float roundYaw45(float yaw) {

        if (yaw >= -22.5 && yaw < 22.5) {
            return 0;
        } else if (yaw >= 22.5 && yaw < 67.5) {
            return 45;
        } else if (yaw >= 67.5 && yaw < 112.5) {
            return 90;
        } else if (yaw >= 112.5 && yaw < 157.5) {
            return 135;
        } else if (yaw >= 157.5 || yaw < -157.5) {
            return 180;
        } else if (yaw >= -157.5 && yaw < -112.5) {
            return -135;
        } else if (yaw >= -112.5 && yaw < -67.5) {
            return -90;
        } else if (yaw >= -67.5 && yaw < -22.5) {
            return -45;
        } else {
            return 0;
        }
    }


    public static boolean hasItem(Player player, Material material, int amount) {

        ItemStack[] inventory = player.getInventory().getContents();
        int totalAmount = 0;


        for (ItemStack item : inventory) {

            if (item != null && item.getType() == material) {
                totalAmount += item.getAmount();


                if (totalAmount >= amount) {
                    return true;
                }
            }
        }

        return false;
    }

    public static int getItemAmount(Player player, Material material) {

        ItemStack[] inventory = player.getInventory().getContents();
        int totalAmount = 0;


        for (ItemStack item : inventory) {
            if (item != null && item.getType() == material) {
                totalAmount += item.getAmount();

            }
        }

        return totalAmount;
    }

    public static void removeItem(Player player, Material material, int amount) {
        ItemStack[] inventory = player.getInventory().getContents();
        int remaining = amount;

        for (int i = 0; i < inventory.length; i++) {
            ItemStack item = inventory[i];

            if (item != null && item.getType() == material) {
                if (item.getAmount() > remaining) {

                    item.setAmount(item.getAmount() - remaining);
                } else {
                    remaining -= item.getAmount();
                    player.getInventory().clear(i);
                }

            }
        }
    }

    public static Location getLocationFromString(World world, String cords) {
        String[] pos = cords.split(" ");

        return new Location(world, Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Double.parseDouble(pos[2]));
    }

    public static double getAttackDamage(Player player) {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        double baseDamage = 1.0;

        if (itemInHand != null) {
            switch (itemInHand.getType()) {
                case WOODEN_SWORD -> baseDamage = 4.0;
                case STONE_SWORD -> baseDamage = 5.0;
                case IRON_SWORD -> baseDamage = 6.0;
                case DIAMOND_SWORD -> baseDamage = 7.0;
                case NETHERITE_SWORD -> baseDamage = 8.0;
                case WOODEN_AXE -> baseDamage = 7.0;
                case STONE_AXE -> baseDamage = 9.0;
                case IRON_AXE -> baseDamage = 9.0;
                case DIAMOND_AXE -> baseDamage = 9.0;
                case NETHERITE_AXE -> baseDamage = 10.0;

                default -> baseDamage = 1.0;
            }


            if (itemInHand.containsEnchantment(Enchantment.DAMAGE_ALL)) {
                int level = itemInHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                baseDamage += 1.0 + (level - 1) * 0.5;
            }
        }

        return baseDamage;
    }

}
