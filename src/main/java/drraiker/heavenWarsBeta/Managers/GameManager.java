package drraiker.heavenWarsBeta.Managers;

import drraiker.heavenWarsBeta.Util.Configs;
import drraiker.heavenWarsBeta.Util.Util;
import drraiker.heavenWarsBeta.timers.MainTimer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;

import java.util.*;

public class GameManager {

    public final static HashMap<World, GameManager> worldMangers = new HashMap<>();

    private String timer = "| 0 : 00 |";

    private final World world;
    private final World lobby;
    private final PlayerManager playerManager;
    private final TeamsManager teamsManager;
    private final ValuesManager valuesManager;
    private final MapManager mapManager;
    private GameState gameState;

    private int timer_m = Configs.config.getInt("game-minutes");
    private int timer_s = 0;

    private GameManager(ConfigurationSection configurationSection) {
        this.world = Bukkit.getWorld(configurationSection.getString("game.name"));
        this.lobby = Bukkit.getWorld(configurationSection.getString("lobby.name"));
        this.gameState = GameState.LOBBY;
        this.teamsManager = new TeamsManager();
        this.valuesManager = new ValuesManager(
                world,
                lobby,
                Util.getLocationFromString(lobby, configurationSection.getString("lobby.spawn")).toCenterLocation(),
                Util.getLocationFromString(world, configurationSection.getString("game.teams.team1.spawn")).toCenterLocation(),
                Util.getLocationFromString(world, configurationSection.getString("game.teams.team2.spawn")).toCenterLocation(),
                Util.getLocationFromString(world, configurationSection.getString("game.teams.team3.spawn")).toCenterLocation(),
                Util.getLocationFromString(world, configurationSection.getString("game.teams.team4.spawn")).toCenterLocation(),
                Util.getLocationFromString(world, configurationSection.getString("game.teams.team1.core.pos")),
                Util.getLocationFromString(world, configurationSection.getString("game.teams.team2.core.pos")),
                Util.getLocationFromString(world, configurationSection.getString("game.teams.team3.core.pos")),
                Util.getLocationFromString(world, configurationSection.getString("game.teams.team4.core.pos")),
                configurationSection.getStringList("game.unbreakable"),
                configurationSection.getString("game.map"),
                Util.getLocationFromString(world, configurationSection.getString("game.center")).toCenterLocation()
        );

        this.playerManager = new PlayerManager(this);
        this.mapManager = new MapManager(this);
    }

    public static void create(ConfigurationSection configurationSection) {
        World world = Bukkit.getWorld(configurationSection.getString("game.name"));
        if (world == null) return;
        worldMangers.put(world, new GameManager(configurationSection));

    }

    public static GameManager getGameManager(World world) {
        if (worldMangers.containsKey(world)) return worldMangers.get(world);

        return null;
    }

    public static GameManager getGameManagerByLobby(World world) {
        for (GameManager gameManager : worldMangers.values()) {
            if (gameManager.getLobby().equals(world)) {
                return gameManager;
            }
        }
        return null;
    }


    public void startGame() {

        for (Player player : getLobby().getPlayers()) {
            getTeamsManager().inGame.add(player);
            if (!getTeamsManager().team1Scores.containsKey(player) && !getTeamsManager().team2Scores.containsKey(player) && !getTeamsManager().team3Scores.containsKey(player) && !getTeamsManager().team4Scores.containsKey(player)) {
                List<HashMap<Player, Integer>> teams = Arrays.asList(getTeamsManager().team1Scores, getTeamsManager().team2Scores, getTeamsManager().team3Scores, getTeamsManager().team4Scores);
                teams.stream().min(Comparator.comparing(HashMap::size)).get().put(player, 0);
            }
            player.setGameMode(GameMode.SURVIVAL);


        }

        setGameState(GameState.GAME);

        for (Entity entity : getWorld().getEntities()) {

            if (
                    entity instanceof Item ||
                    entity instanceof BlockDisplay ||
                    entity instanceof TextDisplay ||
                    entity instanceof TNTPrimed
            ) {

                entity.remove();

            }

        }


        int teamCount = 0;

        if (!getTeamsManager().team1Scores.isEmpty()) teamCount++;
        if (!getTeamsManager().team2Scores.isEmpty()) teamCount++;
        if (!getTeamsManager().team3Scores.isEmpty()) teamCount++;
        if (!getTeamsManager().team4Scores.isEmpty()) teamCount++;

        if (teamCount == 1) {

            List<HashMap<Player, Integer>> teams = Arrays.asList(getTeamsManager().team1Scores, getTeamsManager().team2Scores, getTeamsManager().team3Scores, getTeamsManager().team4Scores);
            HashMap<Player, Integer> team = teams.stream().max(Comparator.comparing(HashMap::size)).get();

            int half = team.size() / 2;
            int count = 0;

            for (Player player : team.keySet()) {

                getTeamsManager().team1Scores.remove(player);
                getTeamsManager().team2Scores.remove(player);
                getTeamsManager().team3Scores.remove(player);
                getTeamsManager().team4Scores.remove(player);


                List<HashMap<Player, Integer>> teams1 = Arrays.asList(getTeamsManager().team1Scores, getTeamsManager().team2Scores, getTeamsManager().team3Scores, getTeamsManager().team4Scores);
                teams1.stream().min(Comparator.comparing(HashMap::size)).get().put(player, 0);

                count++;

                if (count == half)
                    break;

            }

        }

        getTeamsManager().resetHealth();
        getTeamsManager().resetScores();
        getTeamsManager().calculateColorOnStart();
        //MapManager.loadStructure(getWorld(), valuesManager.getMap());
        mapManager.reset();
        playerManager.equip();
        playerManager.toSpawns();
        MainTimer.begin(this);

    }

    public void stopGame() {

        List<Integer> scores = new ArrayList<>();
        if (!getTeamsManager().team1Scores.isEmpty())
            scores.add(getTeamsManager().getTeam1Score());
        if (!getTeamsManager().team2Scores.isEmpty())
            scores.add(getTeamsManager().getTeam2Score());
        if (!getTeamsManager().team3Scores.isEmpty())
            scores.add(getTeamsManager().getTeam3Score());
        if (!getTeamsManager().team4Scores.isEmpty())
            scores.add(getTeamsManager().getTeam4Score());

        Collections.sort(scores);
        Collections.reverse(scores);

        if (scores.isEmpty()) {
            ChatManager.titleAll(getWorld(), Util.stringToComponent("&f¯\\_(ツ)_/¯"), Util.stringToComponent(""));
        } else {

            if (scores.size() > 1) {
                if (scores.get(0).equals(scores.get(1))) {
                    ChatManager.titleAll(getWorld(), Util.stringToComponent("&7&lНичья"), Util.stringToComponent("&f¯\\_(ツ)_/¯"));
                } else {

                    int min = scores.get(0);

                    if (min == getTeamsManager().getTeam1Score() && !getTeamsManager().team1Scores.isEmpty()) {

                        ChatManager.titleAll(getWorld(),Util.stringToComponent("&6&lПобедили"), Util.stringToComponent(getTeamsManager().getTeam1Color() + getTeamsManager().getTeam1Name()));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&fКоманда " + getTeamsManager().getTeam1Color() + getTeamsManager().getTeam1Name() + "&f:"));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));
                        for (Player player : getTeamsManager().team1Scores.keySet()) {
                            ChatManager.sendAll(getWorld(), Util.stringToComponent("&7- &f" + player.getName() + " &7:&f " + getTeamsManager().team1Scores.get(player)));
                        }
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));

                    } else if (min == getTeamsManager().getTeam2Score() && !getTeamsManager().team2Scores.isEmpty()) {

                        ChatManager.titleAll(getWorld(),Util.stringToComponent("&6&lПобедили"), Util.stringToComponent(getTeamsManager().getTeam2Color() + getTeamsManager().getTeam2Name()));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&fКоманда " + getTeamsManager().getTeam2Color() + getTeamsManager().getTeam2Name() + "&f:"));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));
                        for (Player player : getTeamsManager().team2Scores.keySet()) {
                            ChatManager.sendAll(getWorld(), Util.stringToComponent("&7- &f" + player.getName() + " &7:&f " + getTeamsManager().team2Scores.get(player)));
                        }
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));

                    } else if (min == getTeamsManager().getTeam3Score() && !getTeamsManager().team3Scores.isEmpty()) {

                        ChatManager.titleAll(getWorld(),Util.stringToComponent("&6&lПобедили"), Util.stringToComponent(getTeamsManager().getTeam3Color() + getTeamsManager().getTeam3Name()));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&fКоманда " + getTeamsManager().getTeam3Color() + getTeamsManager().getTeam3Name() + "&f:"));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));
                        for (Player player : getTeamsManager().team3Scores.keySet()) {
                            ChatManager.sendAll(getWorld(), Util.stringToComponent("&7- &f" + player.getName() + " &7:&f " + getTeamsManager().team3Scores.get(player)));
                        }
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));

                    } else if (min == getTeamsManager().getTeam4Score() && !getTeamsManager().team4Scores.isEmpty()) {

                        ChatManager.titleAll(getWorld(),Util.stringToComponent("&6&lПобедили"), Util.stringToComponent(getTeamsManager().getTeam4Color() + getTeamsManager().getTeam4Name()));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&fКоманда " + getTeamsManager().getTeam4Color() + getTeamsManager().getTeam4Name() + "&f:"));
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));
                        for (Player player : getTeamsManager().team4Scores.keySet()) {
                            ChatManager.sendAll(getWorld(), Util.stringToComponent("&7- &f" + player.getName() + " &7:&f " + getTeamsManager().team4Scores.get(player)));
                        }
                        ChatManager.sendAll(getWorld(),Util.stringToComponent("&7-------------------"));

                    }
                }
            }
        }

        getTeamsManager().inGame.clear();
        getTeamsManager().team1Scores.clear();
        getTeamsManager().team2Scores.clear();
        getTeamsManager().team3Scores.clear();
        getTeamsManager().team4Scores.clear();
        getTeamsManager().spectators.clear();

        getTeamsManager().resetColors();

        for (Player player : getWorld().getPlayers()) {

            Location loc = new Location(Bukkit.getWorld("spawn"), 0, 50, 0);
            player.teleport(loc);
        }

        setGameState(GameState.LOBBY);
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getTimer() {
        return timer;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ValuesManager getValuesManager() {
        return valuesManager;
    }

    public TeamsManager getTeamsManager() {
        return teamsManager;
    }

    public World getWorld() {
        return world;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setTimer_m(int timer_m) {
        this.timer_m = timer_m;
    }

    public void setTimer_s(int timer_s) {
        this.timer_s = timer_s;
    }

    public int getTimer_m() {
        return timer_m;
    }

    public int getTimer_s() {
        return timer_s;
    }

    public World getLobby() {
        return lobby;
    }
}
