package drraiker.heavenWarsBeta.Util;

import java.util.List;
import org.bukkit.configuration.Configuration;

public class ConfigLoader {
   private static Configuration config;

   public static void update() {
      config = Configs.config;
      if (config.get("worlds") == null) {
         set("worlds.1.game.name", "HeavenWarsBeta");
         set("worlds.1.game.map", "castles");
         set("worlds.1.game.center", "0 60 0");
         set("worlds.1.game.teams.team1.spawn", "33 58 33");
         set("worlds.1.game.teams.team1.core.pos", "35 58 35");
         set("worlds.1.game.teams.team2.spawn", "-33 58 33");
         set("worlds.1.game.teams.team2.core.pos", "-35 58 35");
         set("worlds.1.game.teams.team3.spawn", "33 58 -33");
         set("worlds.1.game.teams.team3.core.pos", "35 58 -35");
         set("worlds.1.game.teams.team4.spawn", "-33 58 -33");
         set("worlds.1.game.teams.team4.core.pos", "-35 58 -35");
         set("worlds.1.game.unbreakable", List.of("1 58 0", "-1 58 0", "0 58 1", "0 58 -1", "0 58 0"));
         set("worlds.1.lobby.name", "HeavenWarsBetaLobby");
         set("worlds.1.lobby.spawn", "0 100 0");
      }

      set("game-minutes", 15);
      set("worlds-menu.name", "&6&lВыбери игровой мир");
      set("teams-menu.name", "&6&lВыбери команду");
      set("colors-menu.name", "&f\uf811\uf811\uf811\uf811\uf811\uf811\uf811\uf811\uab78");
      set("mode-menu.name", "&f\uf811\uf811\uf811\uf811\uf811\uf811\uf811\uf811\uAB79");

      set("max-health", 40);

      Configs.saveConfig();
   }

   private static void set(String path, Object value) {
      if (config.get(path) == null) {
         config.set(path, value);
      }

   }
}
