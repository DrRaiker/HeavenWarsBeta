package drraiker.heavenWarsBeta.Managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;

public class TeamsManagerSAVED {

    private int team1Score;
    private int team2Score;
    private int team3Score;
    private int team4Score;

    public final HashSet<Player> inGame = new HashSet<>();


    public final HashMap<Player, Integer> team1Scores = new HashMap<>();
    public final HashMap<Player, Integer> team2Scores = new HashMap<>();
    public final HashMap<Player, Integer> team3Scores = new HashMap<>();
    public final HashMap<Player, Integer> team4Scores = new HashMap<>();

    public final HashSet<Player> spectators = new HashSet<>();

    private String team1Name = "&fПервые";
    private String team2Name = "&fВторые";
    private String team3Name = "&fТретьи";
    private String team4Name = "&fЧетвёртые";

    private String team1Color = "&f";
    private String team2Color = "&f";
    private String team3Color = "&f";
    private String team4Color = "&f";

    private Material team1Material = Material.WHITE_WOOL;
    private Material team2Material = Material.WHITE_WOOL;
    private Material team3Material = Material.WHITE_WOOL;
    private Material team4Material = Material.WHITE_WOOL;

    public void resetColors() {

        team1Name = "&fПервые";
        team2Name = "&fВторые";
        team3Name = "&fТретьи";
        team4Name = "&fЧетвёртые";

        team1Color = "&f";
        team2Color = "&f";
        team3Color = "&f";
        team4Color = "&f";

        team1Material = Material.WHITE_WOOL;
        team2Material = Material.WHITE_WOOL;
        team3Material = Material.WHITE_WOOL;
        team4Material = Material.WHITE_WOOL;
    }

    public void resetScores() {
        setTeam1Score(0);
        setTeam2Score(0);
        setTeam3Score(0);
        setTeam4Score(0);
    }

    public Material getTeamMaterialByPlayer(Player player) {
        if (team1Scores.containsKey(player)) {
            return getTeam1Material();
        } else if (team2Scores.containsKey(player)) {
            return getTeam2Material();
        } else if (team3Scores.containsKey(player)) {
            return getTeam3Material();
        } else if (team4Scores.containsKey(player)) {
            return getTeam4Material();
        } else return Material.WHITE_WOOL;
    }
    public String getTeamNameByPlayer(Player player) {
        if (team1Scores.containsKey(player)) {
            return getTeam1Name();
        } else if (team2Scores.containsKey(player)) {
            return getTeam2Name();
        } else if (team3Scores.containsKey(player)) {
            return getTeam3Name();
        } else if (team4Scores.containsKey(player)) {
            return getTeam4Name();
        } else if (spectators.contains(player)) {
            return "&7Наблюдатели";
        }
        return "&7Не выбрана";
    }
    public String getTeamColorByPlayer(Player player) {
        if (team1Scores.containsKey(player)) {
            return getTeam1Color();
        } else if (team2Scores.containsKey(player)) {
            return getTeam2Color();
        } else if (team3Scores.containsKey(player)) {
            return getTeam3Color();
        } else if (team4Scores.containsKey(player)) {
            return getTeam4Color();
        } else if (spectators.contains(player)) {
            return "&7";
        }
        return "&f";
    }

    public void addPlayerScore(Player player, int value) {
        if (team1Scores.containsKey(player)) {
            team1Scores.put(player, team1Scores.get(player) + value);
        } else if (team2Scores.containsKey(player)) {
            team2Scores.put(player, team2Scores.get(player) + value);
        } else if (team3Scores.containsKey(player)) {
            team3Scores.put(player, team3Scores.get(player) + value);
        } else if (team4Scores.containsKey(player)) {
            team4Scores.put(player, team4Scores.get(player) + value);
        }
    }

    public void addTeamScoreByPlayer(Player player, int value) {
        if (team1Scores.containsKey(player)) {
            addTeam1Score(value);
        } else if (team2Scores.containsKey(player)) {
            addTeam2Score(value);
        } else if (team3Scores.containsKey(player)) {
            addTeam3Score(value);
        } else if (team4Scores.containsKey(player)) {
            addTeam4Score(value);
        }
    }

    public void setTeam2Material(Material team2Material) {
        this.team2Material = team2Material;
    }

    public void setTeam1Material(Material team1Material) {
        this.team1Material = team1Material;
    }

    public void setTeam3Material(Material team3Material) {
        this.team3Material = team3Material;
    }

    public void setTeam4Material(Material team4Material) {
        this.team4Material = team4Material;
    }

    public Material getTeam1Material() {
        return team1Material;
    }

    public Material getTeam2Material() {
        return team2Material;
    }

    public Material getTeam3Material() {
        return team3Material;
    }

    public Material getTeam4Material() {
        return team4Material;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public void setTeam3Name(String team3Name) {
        this.team3Name = team3Name;
    }

    public void setTeam4Name(String team4Name) {
        this.team4Name = team4Name;
    }

    public void setTeam1Color(String team1Color) {
        this.team1Color = team1Color;
    }

    public void setTeam2Color(String team2Color) {
        this.team2Color = team2Color;
    }

    public void setTeam3Color(String team3Color) {
        this.team3Color = team3Color;
    }

    public void setTeam4Color(String team4Color) {
        this.team4Color = team4Color;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public String getTeam3Name() {
        return team3Name;
    }

    public String getTeam4Name() {
        return team4Name;
    }

    public String getTeam1Color() {
        return team1Color;
    }

    public String getTeam2Color() {
        return team2Color;
    }

    public String getTeam3Color() {
        return team3Color;
    }

    public String getTeam4Color() {
        return team4Color;
    }

    public void addTeam1Score(int value) {
        setTeam1Score(getTeam1Score() + value);
    }
    public void addTeam2Score(int value) {
        setTeam2Score(getTeam2Score() + value);
    }
    public void addTeam3Score(int value) {
        setTeam3Score(getTeam3Score() + value);
    }
    public void addTeam4Score(int value) {
        setTeam4Score(getTeam4Score() + value);
    }


    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }

    public void setTeam3Score(int team3Score) {
        this.team3Score = team3Score;
    }

    public void setTeam4Score(int team4Score) {
        this.team4Score = team4Score;
    }


    public int getTeam1Score() {
        return team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public int getTeam3Score() {
        return team3Score;
    }

    public int getTeam4Score() {
        return team4Score;
    }
}
