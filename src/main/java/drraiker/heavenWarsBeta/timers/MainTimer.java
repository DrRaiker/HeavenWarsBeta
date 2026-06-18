package drraiker.heavenWarsBeta.timers;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Managers.*;
import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class MainTimer {

    public static void begin(GameManager gameManager) {

        ValuesManager valuesManager = gameManager.getValuesManager();
        TeamsManager teamsManager = gameManager.getTeamsManager();

        gameManager.setTimer_m(Configs.config.getInt("game-minutes"));
        gameManager.setTimer_s(0);


        new BukkitRunnable() {
        int wool = 2;
        int tnt = 4;
        int reset = 5 * 60;
        int crate = 2 * 60;

            @Override
            public void run() {


                if (wool == 0) {

                    ItemStack item = new ItemStack(Material.WHITE_WOOL);

                    if (!teamsManager.team1Scores.isEmpty())
                        dropUnstackableItem( valuesManager.getTeam1Spawn(), item);
                    if (!teamsManager.team2Scores.isEmpty())
                        dropUnstackableItem( valuesManager.getTeam2Spawn(), item);
                    if (!teamsManager.team3Scores.isEmpty())
                        dropUnstackableItem( valuesManager.getTeam3Spawn(), item);
                    if (!teamsManager.team4Scores.isEmpty())
                        dropUnstackableItem( valuesManager.getTeam4Spawn(), item);

                    wool = 2;
                }

                if (tnt == 0) {

                    ItemStack item = new ItemStack(Material.TNT);

                    if (!teamsManager.team1Scores.isEmpty())
                        dropUnstackableItem( valuesManager.getTeam1Spawn(), item);
                    if (!teamsManager.team2Scores.isEmpty())
                        dropUnstackableItem( valuesManager.getTeam2Spawn(), item);
                    if (!teamsManager.team3Scores.isEmpty())
                        dropUnstackableItem( valuesManager.getTeam3Spawn(), item);
                    if (!teamsManager.team4Scores.isEmpty())
                        dropUnstackableItem( valuesManager.getTeam4Spawn(), item);

                    tnt = 4;
                }

                if (reset == 15)
                    ChatManager.sendAll(gameManager.getWorld(), ChatManager.prefix.append(Util.stringToComponent("Восстановление карты через &315 секунд")));

                if (reset == 0) {

                    ChatManager.sendAll(gameManager.getWorld(), ChatManager.prefix.append(Util.stringToComponent("Карта востановлена!")));
                    ChatManager.sendAll(gameManager.getWorld(), ChatManager.prefix.append(Util.stringToComponent("Ядра востановлены!")));
                    teamsManager.resetHealth();
                    gameManager.getMapManager().reset();

                    reset = 5 * 60;
                }


                if (crate == 15)
                    ChatManager.sendAll(gameManager.getWorld(), ChatManager.prefix.append(Util.stringToComponent("Посылка появится через &315 секунд")));

                if (crate == 0) {

                    Crate.spawnCrate(gameManager);

                    crate = 2 * 60;
                }

                wool--;
                tnt--;
                reset--;
                crate--;

                if (gameManager.getTimer_s() > 9)
                    gameManager.setTimer("| " + gameManager.getTimer_m() + " : " + gameManager.getTimer_s() + " |");
                else
                    gameManager.setTimer("| " + gameManager.getTimer_m() + " : 0" + gameManager.getTimer_s() + " |");




                if (gameManager.getTimer_m() == 0 && gameManager.getTimer_s() == 0) {

                    gameManager.stopGame();
                    cancel();

                }

                if (gameManager.getTimer_s() < 1) {
                    gameManager.setTimer_s(60);
                    gameManager.setTimer_m(gameManager.getTimer_m() - 1);
                }
                gameManager.setTimer_s(gameManager.getTimer_s() - 1);
            }
        }.runTaskTimer(HeavenWarsBeta.getPlugin(), 20L, 20L);

    }

    public static final NamespacedKey key = new NamespacedKey(HeavenWarsBeta.getPlugin(), "random");

    private static void dropUnstackableItem(Location location, ItemStack originalItemStack) {

        World world = location.getWorld();;

        ItemStack itemStack = originalItemStack.clone();
        ItemMeta meta = itemStack.getItemMeta();

        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, PersistentDataType.STRING, UUID.randomUUID().toString());

        itemStack.setItemMeta(meta);

        Item item = world.dropItem(location, itemStack);

        item.setPickupDelay(0);



    }

}
