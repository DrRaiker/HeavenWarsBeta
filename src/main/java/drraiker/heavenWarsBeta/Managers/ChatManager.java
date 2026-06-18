package drraiker.heavenWarsBeta.Managers;

import drraiker.heavenWarsBeta.Util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ChatManager {

    public static Component prefix = Util.stringToComponent("&8[&6&lHW&#00e695Beta&8]&r ");

    public static void sendAll(World world, Component message) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(message);
        }
    }

    public static void hotbarAll(World world, Component message) {
        for (Player player : world.getPlayers()) {
            player.sendActionBar(message);
        }
    }


    public static void title(Player player, Component titletext, Component subtitle) {

        Title title = Title.title(titletext, subtitle);
        player.showTitle(title);
    }

    public static void titleAll(World world, Component titletext, Component subtitle) {

        Title title = Title.title(titletext, subtitle);

        for (Player player : world.getPlayers()) {
            player.showTitle(title);
        }

    }

}
