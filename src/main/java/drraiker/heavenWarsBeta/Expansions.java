package drraiker.heavenWarsBeta;

import drraiker.heavenWarsBeta.Managers.GameManager;
import drraiker.heavenWarsBeta.Managers.TeamsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Expansions extends PlaceholderExpansion {

    HeavenWarsBeta plugin;

    public Expansions(HeavenWarsBeta plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "DrRaiker";
    }

    @Override
    public String getIdentifier() {
        return "heavenwarsbeta";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if(player == null) {
            return "";
        }

        GameManager gameManager = GameManager.getGameManager(player.getWorld());

        if(params.equalsIgnoreCase("team")) {

            if (gameManager == null) {
                gameManager = GameManager.getGameManagerByLobby(player.getWorld());
            }

            if (gameManager == null) return null;


            TeamsManager teamsManager = gameManager.getTeamsManager();

            String team = teamsManager.getTeamNameByPlayer(player);;
            String color = teamsManager.getTeamColorByPlayer(player);;

            return color + team;
        }


        if (gameManager == null) return null;

        TeamsManager teamsManager =gameManager.getTeamsManager();

        if(params.equalsIgnoreCase("score1")) {
            return teamsManager.getTeamLine(0);
        }

        if(params.equalsIgnoreCase("score2")) {
            return teamsManager.getTeamLine(1);
        }

        if(params.equalsIgnoreCase("score3")) {
            return teamsManager.getTeamLine(2);
        }

        if(params.equalsIgnoreCase("score4")) {
            return teamsManager.getTeamLine(3);
        }
        if(params.equalsIgnoreCase("player_score")) {
            if (teamsManager.team1Scores.containsKey(player)) {
                return "&7Очки: &f" + teamsManager.team1Scores.get(player);
            } else if (teamsManager.team2Scores.containsKey(player)) {
                return "&7Очки: &f" + teamsManager.team2Scores.get(player);
            } else if (teamsManager.team3Scores.containsKey(player)) {
                return "&7Очки: &f" + teamsManager.team3Scores.get(player);
            } else if (teamsManager.team4Scores.containsKey(player)) {
                return "&7Очки: &f" + teamsManager.team4Scores.get(player);
            } else return "";
        }

        if(params.equalsIgnoreCase("timer")) {
            return gameManager.getTimer();
        }

        return null;
    }
}