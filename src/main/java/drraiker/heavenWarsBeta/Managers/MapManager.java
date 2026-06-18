package drraiker.heavenWarsBeta.Managers;

import drraiker.heavenWarsBeta.HeavenWarsBeta;
import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MapManager {

    private final GameManager gameManager;
    private final PlayerManager playerManager;
    private final TeamsManager teamsManager;
    private final ValuesManager valuesManager;

    public MapManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.playerManager = gameManager.getPlayerManager();
        this.teamsManager = gameManager.getTeamsManager();
        this.valuesManager = gameManager.getValuesManager();
    }

    public static void saveStructure(Location pos1, Location pos2, String fileName) {
        HeavenWarsBeta plugin = HeavenWarsBeta.getPlugin();

        int x1 = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int x2 = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int y1 = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int y2 = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int z1 = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int z2 = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        JSONArray blocksArray = new JSONArray();

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    Block block = pos1.getWorld().getBlockAt(x, y, z);
                    JSONObject blockData = new JSONObject();
                    blockData.put("x", x - x1);
                    blockData.put("y", y - y1);
                    blockData.put("z", z - z1);
                    blockData.put("type", block.getType().toString().toLowerCase());

                    // Сохраняем дополнительные данные блока
                    blockData.put("data", block.getBlockData().getAsString());

                    blocksArray.add(blockData);
                }
            }
        }

        JSONArray originArray = new JSONArray();
        originArray.add(x1);
        originArray.add(y1);
        originArray.add(z1);

        JSONObject structure = new JSONObject();
        structure.put("blocks", blocksArray);
        structure.put("origin", originArray);

        try (FileWriter file = new FileWriter(plugin.getDataFolder() + "/" + fileName)) {
            file.write(structure.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadStructure(World world, String fileName) {
        HeavenWarsBeta plugin = HeavenWarsBeta.getPlugin();

        try (FileReader reader = new FileReader(plugin.getDataFolder() + "/" + fileName)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject structure = (JSONObject) jsonParser.parse(reader);

            JSONArray blocksArray = (JSONArray) structure.get("blocks");

            JSONArray originArray = (JSONArray) structure.get("origin");
            int xOrigin = ((Long) originArray.get(0)).intValue();
            int yOrigin = ((Long) originArray.get(1)).intValue();
            int zOrigin = ((Long) originArray.get(2)).intValue();

            Location originLocation = new Location(world, xOrigin, yOrigin, zOrigin);

            for (Object obj : blocksArray) {
                JSONObject blockData = (JSONObject) obj;
                int x = ((Long) blockData.get("x")).intValue();
                int y = ((Long) blockData.get("y")).intValue();
                int z = ((Long) blockData.get("z")).intValue();
                Material material = Material.valueOf(((String) blockData.get("type")).toUpperCase());

                Location blockLocation = originLocation.clone().add(x, y, z);
                Block block = world.getBlockAt(blockLocation);
                block.setType(material);

                if (blockData.containsKey("data")) {
                    String dataString = (String) blockData.get("data");
                    block.setBlockData(Bukkit.createBlockData(dataString));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void loadStructureReplace(World world, String fileName, Material replaceMaterial) {
        HeavenWarsBeta plugin = HeavenWarsBeta.getPlugin();

        try (FileReader reader = new FileReader(plugin.getDataFolder() + "/" + fileName)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject structure = (JSONObject) jsonParser.parse(reader);

            JSONArray blocksArray = (JSONArray) structure.get("blocks");

            JSONArray originArray = (JSONArray) structure.get("origin");
            int xOrigin = ((Long) originArray.get(0)).intValue();
            int yOrigin = ((Long) originArray.get(1)).intValue();
            int zOrigin = ((Long) originArray.get(2)).intValue();

            Location originLocation = new Location(world, xOrigin, yOrigin, zOrigin);

            List<JSONObject> blockDataList = new ArrayList<>();
            for (Object obj : blocksArray) {
                blockDataList.add((JSONObject) obj);
            }

            int blocksPerTick = 10000;
            AtomicInteger index = new AtomicInteger(0);

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < blocksPerTick; i++) {
                        if (index.get() >= blockDataList.size()) {
                            cancel();
                            break;
                        }

                        JSONObject blockData = blockDataList.get(index.getAndIncrement());

                        int x = ((Long) blockData.get("x")).intValue();
                        int y = ((Long) blockData.get("y")).intValue();
                        int z = ((Long) blockData.get("z")).intValue();
                        Material material = Material.valueOf(((String) blockData.get("type")).toUpperCase());

                        Location blockLocation = originLocation.clone().add(x, y, z);
                        Block block = world.getBlockAt(blockLocation);


                        if (material.equals(Material.WHITE_WOOL)) {
                            block.setType(replaceMaterial);
                        } else {
                            block.setType(material);
                        }

                        if (blockData.containsKey("data") && !material.equals(Material.WHITE_WOOL)) {
                            String dataString = (String) blockData.get("data");
                            block.setBlockData(Bukkit.createBlockData(dataString));
                        }
                    }
                }
            }.runTaskTimer(plugin, 0L, 1L);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }



    public void reset() {
        //loadStructure(gameManager.getWorld(), valuesManager.getMap());
        loadStructure(gameManager.getWorld(), "center.json");

        if (teamsManager.team1Scores.isEmpty()) {
            loadStructure(gameManager.getWorld(), "empty_1.json");
        } else {
            loadStructureReplace(gameManager.getWorld(), valuesManager.getMap() + "_colored_1.json", teamsManager.getTeam1Material());
        }
        if (teamsManager.team2Scores.isEmpty()) {
            loadStructure(gameManager.getWorld(), "empty_2.json");
        } else {
            loadStructureReplace(gameManager.getWorld(), valuesManager.getMap() + "_colored_2.json", teamsManager.getTeam2Material());
        }
        if (teamsManager.team3Scores.isEmpty()) {
            loadStructure(gameManager.getWorld(), "empty_3.json");
        } else {
            loadStructureReplace(gameManager.getWorld(), valuesManager.getMap() + "_colored_3.json", teamsManager.getTeam3Material());
        }
        if (teamsManager.team4Scores.isEmpty()) {
            loadStructure(gameManager.getWorld(), "empty_4.json");
        } else {
            loadStructureReplace(gameManager.getWorld(), valuesManager.getMap() + "_colored_4.json", teamsManager.getTeam4Material());
        }

        playerManager.toSpawns();

        for (Entity entity : gameManager.getWorld().getEntities()) {
            if (entity instanceof ArmorStand) entity.remove();
            if (entity instanceof Chicken) entity.remove();
        }

        ChatManager.hotbarAll(gameManager.getWorld(), Util.stringToComponent("&2&lКарта восстановлена!"));
    }
}
