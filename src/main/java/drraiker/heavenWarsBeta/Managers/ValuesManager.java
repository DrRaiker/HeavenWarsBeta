package drraiker.heavenWarsBeta.Managers;

import drraiker.heavenWarsBeta.Util.Util;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashSet;
import java.util.List;

public class ValuesManager {

    private final World lobbyWorld;
    private final World gameWorld;

    private final Location lobbySpawn;

    private final Location gameCenter;

    private final Location team1Spawn;
    private final Location team2Spawn;
    private final Location team3Spawn;
    private final Location team4Spawn;

    private final Location core1;
    private final Location core2;
    private final Location core3;
    private final Location core4;
    private final String map;

    private final HashSet<Location> unbreakable = new HashSet<>();

    public ValuesManager(World gameWorld,
                         World lobbyWorld,
                         Location lobbySpawn,
                         Location team1Spawn,
                         Location team2Spawn,
                         Location team3Spawn,
                         Location team4Spawn,
                         Location core1,
                         Location core2,
                         Location core3,
                         Location core4,
                         List<String> cords,
                         String map,
                         Location gameCenter
    )
    {

        this.lobbySpawn = lobbySpawn;
        this.team1Spawn = team1Spawn;
        this.team2Spawn = team2Spawn;
        this.team3Spawn = team3Spawn;
        this.team4Spawn = team4Spawn;
        this.core1 = core1;
        this.core2 = core2;
        this.core3 = core3;
        this.core4 = core4;
        this.gameWorld = gameWorld;
        this.lobbyWorld = lobbyWorld;
        this.map = map;
        this.gameCenter = gameCenter;

        for (String string : cords) {
            unbreakable.add(Util.getLocationFromString(gameWorld, string));

        }

    }

    public String getMap() {
        return map;
    }

    public Location getGameCenter() {
        return gameCenter;
    }

    public World getGameWorld() {
        return gameWorld;
    }

    public World getLobbyWorld() {
        return lobbyWorld;
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
    }

    public Location getTeam1Spawn() {
        return team1Spawn;
    }

    public Location getTeam2Spawn() {
        return team2Spawn;
    }

    public Location getTeam3Spawn() {
        return team3Spawn;
    }

    public Location getTeam4Spawn() {
        return team4Spawn;
    }

    public Location getCore1() {
        return core1;
    }

    public Location getCore2() {
        return core2;
    }

    public Location getCore3() {
        return core3;
    }

    public Location getCore4() {
        return core4;
    }

    public HashSet<Location> getUnbreakable() {
        return unbreakable;
    }
}
