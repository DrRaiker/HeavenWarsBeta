package drraiker.heavenWarsBeta.Managers;

import java.util.*;

import drraiker.heavenWarsBeta.Util.Configs;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TeamsManager {
   private int team1Score;
   private int team2Score;
   private int team3Score;
   private int team4Score;
   public final HashSet<Player> inGame = new HashSet();
   public final HashMap<Player, Integer> team1Scores = new HashMap();
   public final HashMap<Player, Integer> team2Scores = new HashMap();
   public final HashMap<Player, Integer> team3Scores = new HashMap();
   public final HashMap<Player, Integer> team4Scores = new HashMap();
   public final HashSet<Player> spectators = new HashSet();
   private String team1Name = "Первая";
   private String team2Name = "Вторая";
   private String team3Name = "Третья";
   private String team4Name = "Четвёртая";
   private String team1Color = "&f";
   private String team2Color = "&f";
   private String team3Color = "&f";
   private String team4Color = "&f";
   private Material team1Material;
   private Material team2Material;
   private Material team3Material;
   private Material team4Material;
   private int team1Health = Configs.config.getInt("max-health");
   private int team2Health = Configs.config.getInt("max-health");
   private int team3Health = Configs.config.getInt("max-health");
   private int team4Health = Configs.config.getInt("max-health");
   private long team1Cooldown = 0;
   private long team2Cooldown = 0;
   private long team3Cooldown = 0;
   private long team4Cooldown = 0;

   public TeamsManager() {
      this.team1Material = Material.WHITE_WOOL;
      this.team2Material = Material.WHITE_WOOL;
      this.team3Material = Material.WHITE_WOOL;
      this.team4Material = Material.WHITE_WOOL;
   }

   public boolean isColorAvailable(String name, String team) {

      if (team1Name.equals(name) && !team.equals("team1")) return false;
      if (team2Name.equals(name) && !team.equals("team2")) return false;
      if (team3Name.equals(name) && !team.equals("team3")) return false;
      if (team4Name.equals(name) && !team.equals("team4")) return false;

      return true;
   }

   private final String[] names = {
           "Оранжевая",
           "Пурпурная",
           "Голубая",
           "Жёлтая",
           "Лаймовая",
           "Розовая",
           "Серая",
           "Бирюзовая",
           "Фиолетовая",
           "Синяя",
           "Коричневая",
           "Зелёная",
           "Красная",
           "Чёрная"
   };

   public static final String[] colors = {
           "&#f9a84d&l",
           "&#d27dd4&l",
           "&#7dc3d2&l",
           "&#f2e854&l",
           "&#9ed74a&l",
           "&#f5a6b5&l",
           "&#7a7a7a&l",
           "&#3bb2c9&l",
           "&#8a43b1&l",
           "&#295bbd&l",
           "&#7b4a27&l",
           "&#4a8337&l",
           "&#be403e&l",
           "&#1d1d1d&l"
   };
   private final Material[] materials = {
           Material.ORANGE_WOOL,
           Material.MAGENTA_WOOL,
           Material.LIGHT_BLUE_WOOL,
           Material.YELLOW_WOOL,
           Material.LIME_WOOL,
           Material.PINK_WOOL,
           Material.GRAY_WOOL,
           Material.CYAN_WOOL,
           Material.PURPLE_WOOL,
           Material.BLUE_WOOL,
           Material.BROWN_WOOL,
           Material.GREEN_WOOL,
           Material.RED_WOOL,
           Material.BLACK_WOOL
   };

   public void setRandomColor(String team) {

      Random random = new Random();
      int num = random.nextInt(names.length);

      String name = names[num];

      if (!isColorAvailable(name, team)) {
         setRandomColor(team);
         return;
      }

      String color = colors[num];
      Material material = materials[num];

      if (team.equals("team1")) {
         team1Name = name;
         team1Color = color;
         team1Material = material;
      } else if (team.equals("team2")) {
         team2Name = name;
         team2Color = color;
         team2Material = material;
      } else if (team.equals("team3")) {
         team3Name = name;
         team3Color = color;
         team3Material = material;
      } else if (team.equals("team4")) {
         team4Name = name;
         team4Color = color;
         team4Material = material;
      }

   }

   public void updateColor(String team) {
      if (team.equals("team1")) {

         if (team1Scores.isEmpty()) {
            team1Name = "Первая";
            team1Material = Material.WHITE_WOOL;
            team1Color = "&f";
         } else {
            boolean isColored = false;
            for (Player player : team1Scores.keySet()) {
               String name = Configs.data.getString("colors." + player.getName() + ".name");

               if (name.equals("Белая")) continue;

               if (isColorAvailable(name, team)) {

                  String color = Configs.data.getString("colors." + player.getName() + ".color");
                  String material = Configs.data.getString("colors." + player.getName() + ".material").toUpperCase();

                  setTeam1Name(name);
                  setTeam1Color(color);
                  setTeam1Material(Material.valueOf(material));
                  isColored = true;
                  break;
               }
            }

            if (!isColored) {
               setRandomColor(team);
            }
         }

      } else if (team.equals("team2")) {

         if (team2Scores.isEmpty()) {
            team2Name = "Вторая";
            team2Material = Material.WHITE_WOOL;
            team2Color = "&f";
         } else {
            boolean isColored = false;
            for (Player player : team2Scores.keySet()) {
               String name = Configs.data.getString("colors." + player.getName() + ".name");

               if (name.equals("Белая")) continue;

               if (isColorAvailable(name, team)) {

                  String color = Configs.data.getString("colors." + player.getName() + ".color");
                  String material = Configs.data.getString("colors." + player.getName() + ".material").toUpperCase();

                  setTeam2Name(name);
                  setTeam2Color(color);
                  setTeam2Material(Material.valueOf(material));
                  isColored = true;
               }
            }

            if (!isColored) {
               setRandomColor(team);
            }
         }

      } else if (team.equals("team3")) {

         if (team3Scores.isEmpty()) {
            team3Name = "Третья";
            team3Material = Material.WHITE_WOOL;
            team3Color = "&f";
         } else {
            boolean isColored = false;
            for (Player player : team3Scores.keySet()) {
               String name = Configs.data.getString("colors." + player.getName() + ".name");

               if (name.equals("Белая")) continue;

               if (isColorAvailable(name, team)) {

                  String color = Configs.data.getString("colors." + player.getName() + ".color");
                  String material = Configs.data.getString("colors." + player.getName() + ".material").toUpperCase();

                  setTeam3Name(name);
                  setTeam3Color(color);
                  setTeam3Material(Material.valueOf(material));
                  isColored = true;
               }
            }

            if (!isColored) {
               setRandomColor(team);
            }
         }

      } else if (team.equals("team4")) {

         if (team4Scores.isEmpty()) {
            team4Name = "Четвёртая";
            team4Material = Material.WHITE_WOOL;
            team4Color = "&f";
         } else {
            boolean isColored = false;
            for (Player player : team4Scores.keySet()) {
               String name = Configs.data.getString("colors." + player.getName() + ".name");

               if (name.equals("Белая")) continue;

               if (isColorAvailable(name, team)) {

                  String color = Configs.data.getString("colors." + player.getName() + ".color");
                  String material = Configs.data.getString("colors." + player.getName() + ".material").toUpperCase();

                  setTeam4Name(name);
                  setTeam4Color(color);
                  setTeam4Material(Material.valueOf(material));
                  isColored = true;
               }
            }

            if (!isColored) {
               setRandomColor(team);
            }
         }
      }


   }

   public String getTeamLine(int line) {
      List<String> names = new ArrayList<>();
      List<String> colors = new ArrayList<>();
      List<Integer> scores = new ArrayList<>();


      if (!team1Scores.isEmpty()) {
         names.add(team1Name);
         colors.add(team1Color);
         scores.add(team1Score);
      }
      if (!team2Scores.isEmpty()) {
         names.add(team2Name);
         colors.add(team2Color);
         scores.add(team2Score);
      }
      if (!team3Scores.isEmpty()) {
         names.add(team3Name);
         colors.add(team3Color);
         scores.add(team3Score);
      }
      if (!team4Scores.isEmpty()) {
         names.add(team4Name);
         colors.add(team4Color);
         scores.add(team4Score);
      }

      if (line > names.size() - 1) return "";
      return colors.get(line) + names.get(line) + "&7 : &f" + scores.get(line);
   }


   public void calculateColorOnStart() {

      if (team1Name.equals("Первая") && !team1Scores.isEmpty()) {
         boolean isColored = false;
         for (Player player : team1Scores.keySet()) {
            String name = Configs.data.getString("colors." + player.getName() + ".name");

            if (name.equals("Белая")) continue;
            if (!isColorAvailable(name, "")) continue;

            String color = Configs.data.getString("colors." + player.getName() + ".color");
            String material = Configs.data.getString("colors." + player.getName() + ".material").toUpperCase();

            setTeam1Name(name);
            setTeam1Color(color);
            setTeam1Material(Material.valueOf(material));
            isColored = true;
         }

         if (!isColored) {
            setRandomColor("team1");
         }

      }
      if (team2Name.equals("Вторая") && !team2Scores.isEmpty()) {
         boolean isColored = false;
         for (Player player : team2Scores.keySet()) {
            String name = Configs.data.getString("colors." + player.getName() + ".name");

            if (name.equals("Белая")) continue;
            if (!isColorAvailable(name, "")) continue;

            String color = Configs.data.getString("colors." + player.getName() + ".color");
            String material = Configs.data.getString("colors." + player.getName() + ".material").toUpperCase();

            setTeam2Name(name);
            setTeam2Color(color);
            setTeam2Material(Material.valueOf(material));
            isColored = true;
         }

         if (!isColored) {
            setRandomColor("team2");
         }

      }
      if (team3Name.equals("Третья") && !team3Scores.isEmpty()) {
         boolean isColored = false;
         for (Player player : team3Scores.keySet()) {
            String name = Configs.data.getString("colors." + player.getName() + ".name");

            if (name.equals("Белая")) continue;
            if (!isColorAvailable(name, "")) continue;

            String color = Configs.data.getString("colors." + player.getName() + ".color");
            String material = Configs.data.getString("colors." + player.getName() + ".material").toUpperCase();

            setTeam3Name(name);
            setTeam3Color(color);
            setTeam3Material(Material.valueOf(material));
            isColored = true;
         }

         if (!isColored) {
            setRandomColor("team3");
         }

      }
      if (team4Name.equals("Четвёртая") && !team4Scores.isEmpty()) {
         boolean isColored = false;
         for (Player player : team4Scores.keySet()) {
            String name = Configs.data.getString("colors." + player.getName() + ".name");

            if (name.equals("Белая")) continue;
            if (!isColorAvailable(name, "")) continue;

            String color = Configs.data.getString("colors." + player.getName() + ".color");
            String material = Configs.data.getString("colors." + player.getName() + ".material").toUpperCase();

            setTeam4Name(name);
            setTeam4Color(color);
            setTeam4Material(Material.valueOf(material));
            isColored = true;
         }

         if (!isColored) {
            setRandomColor("team4");
         }
      }

      if (team1Scores.isEmpty()) {
         team1Name = "Первая";
         team1Color = "&f";
      }
      if (team2Scores.isEmpty()) {
         team2Name = "Вторая";
         team2Color = "&f";
      }
      if (team3Scores.isEmpty()) {
         team3Name = "Третья";
         team3Color = "&f";
      }
      if (team4Scores.isEmpty()) {
         team4Name = "Четвёртая";
         team4Color = "&f";
      }

   }

   public void resetColors() {
      team1Name = "Первая";
      team2Name = "Вторая";
      team3Name = "Третья";
      team4Name = "Четвёртая";
      team1Color = "&f";
      team2Color = "&f";
      team3Color = "&f";
      team4Color = "&f";
      team1Material = Material.WHITE_WOOL;
      team2Material = Material.WHITE_WOOL;
      team3Material = Material.WHITE_WOOL;
      team4Material = Material.WHITE_WOOL;
      team1Health = Configs.config.getInt("max-health");
      team2Health = Configs.config.getInt("max-health");
      team3Health = Configs.config.getInt("max-health");
      team4Health = Configs.config.getInt("max-health");
   }

   public void resetHealth() {
      team1Health = Configs.config.getInt("max-health");
      team2Health = Configs.config.getInt("max-health");
      team3Health = Configs.config.getInt("max-health");
      team4Health = Configs.config.getInt("max-health");
   }
   public void resetScores() {
      this.setTeam1Score(0);
      this.setTeam2Score(0);
      this.setTeam3Score(0);
      this.setTeam4Score(0);
   }

   public void resetPlayerTeam(Player player) {
      this.team1Scores.remove(player);
      this.team2Scores.remove(player);
      this.team3Scores.remove(player);
      this.team4Scores.remove(player);
   }

   public Material getTeamMaterialByPlayer(Player player) {
      if (this.team1Scores.containsKey(player)) {
         return this.getTeam1Material();
      } else if (this.team2Scores.containsKey(player)) {
         return this.getTeam2Material();
      } else if (this.team3Scores.containsKey(player)) {
         return this.getTeam3Material();
      } else {
         return this.team4Scores.containsKey(player) ? this.getTeam4Material() : Material.WHITE_WOOL;
      }
   }

   public String getTeamNameByPlayer(Player player) {
      if (this.team1Scores.containsKey(player)) {
         return this.getTeam1Name();
      } else if (this.team2Scores.containsKey(player)) {
         return this.getTeam2Name();
      } else if (this.team3Scores.containsKey(player)) {
         return this.getTeam3Name();
      } else if (this.team4Scores.containsKey(player)) {
         return this.getTeam4Name();
      } else {
         return this.spectators.contains(player) ? "&7Наблюдатели" : "&7Не выбрана";
      }
   }

   public String getTeamColorByPlayer(Player player) {
      if (this.team1Scores.containsKey(player)) {
         return this.getTeam1Color();
      } else if (this.team2Scores.containsKey(player)) {
         return this.getTeam2Color();
      } else if (this.team3Scores.containsKey(player)) {
         return this.getTeam3Color();
      } else if (this.team4Scores.containsKey(player)) {
         return this.getTeam4Color();
      } else {
         return this.spectators.contains(player) ? "&7" : "&f";
      }
   }

   public void addPlayerScore(Player player, int value) {
      if (this.team1Scores.containsKey(player)) {
         this.team1Scores.put(player, (Integer)this.team1Scores.get(player) + value);
      } else if (this.team2Scores.containsKey(player)) {
         this.team2Scores.put(player, (Integer)this.team2Scores.get(player) + value);
      } else if (this.team3Scores.containsKey(player)) {
         this.team3Scores.put(player, (Integer)this.team3Scores.get(player) + value);
      } else if (this.team4Scores.containsKey(player)) {
         this.team4Scores.put(player, (Integer)this.team4Scores.get(player) + value);
      }

   }

   public void addTeamScoreByPlayer(Player player, int value) {
      if (this.team1Scores.containsKey(player)) {
         this.addTeam1Score(value);
      } else if (this.team2Scores.containsKey(player)) {
         this.addTeam2Score(value);
      } else if (this.team3Scores.containsKey(player)) {
         this.addTeam3Score(value);
      } else if (this.team4Scores.containsKey(player)) {
         this.addTeam4Score(value);
      }

   }

   public void setTeam1Cooldown(long team1Cooldown) {
      this.team1Cooldown = team1Cooldown;
   }

   public void setTeam2Cooldown(long team2Cooldown) {
      this.team2Cooldown = team2Cooldown;
   }

   public void setTeam3Cooldown(long team3Cooldown) {
      this.team3Cooldown = team3Cooldown;
   }

   public void setTeam4Cooldown(long team4Cooldown) {
      this.team4Cooldown = team4Cooldown;
   }

   public long getTeam1Cooldown() {
      return team1Cooldown;
   }

   public long getTeam2Cooldown() {
      return team2Cooldown;
   }

   public long getTeam3Cooldown() {
      return team3Cooldown;
   }

   public long getTeam4Cooldown() {
      return team4Cooldown;
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
      return this.team1Material;
   }

   public Material getTeam2Material() {
      return this.team2Material;
   }

   public Material getTeam3Material() {
      return this.team3Material;
   }

   public Material getTeam4Material() {
      return this.team4Material;
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

   public int getTeam1Health() {
      return team1Health;
   }

   public int getTeam2Health() {
      return team2Health;
   }

   public int getTeam3Health() {
      return team3Health;
   }

   public int getTeam4Health() {
      return team4Health;
   }

   public void setTeam1Health(int team1Health) {
      this.team1Health = team1Health;
   }

   public void setTeam2Health(int team2Health) {
      this.team2Health = team2Health;
   }

   public void setTeam3Health(int team3Health) {
      this.team3Health = team3Health;
   }

   public void setTeam4Health(int team4Health) {
      this.team4Health = team4Health;
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
      return this.team1Name;
   }

   public String getTeam2Name() {
      return this.team2Name;
   }

   public String getTeam3Name() {
      return this.team3Name;
   }

   public String getTeam4Name() {
      return this.team4Name;
   }

   public String getTeam1Color() {
      return this.team1Color;
   }

   public String getTeam2Color() {
      return this.team2Color;
   }

   public String getTeam3Color() {
      return this.team3Color;
   }

   public String getTeam4Color() {
      return this.team4Color;
   }


   public void addTeam1Health(int value) {
      this.setTeam1Health(this.getTeam1Health() + value);

      if (getTeam1Health() < 0) setTeam1Health(0);
   }

   public void addTeam2Health(int value) {
      this.setTeam2Health(this.getTeam2Health() + value);

      if (getTeam2Health() < 0) setTeam2Health(0);
   }

   public void addTeam3Health(int value) {
      this.setTeam3Health(this.getTeam3Health() + value);

      if (getTeam3Health() < 0) setTeam3Health(0);
   }

   public void addTeam4Health(int value) {
      this.setTeam4Health(this.getTeam4Health() + value);

      if (getTeam4Health() < 0) setTeam4Health(0);
   }
   
   
   
   public void addTeam1Score(int value) {
      this.setTeam1Score(this.getTeam1Score() + value);

      if (getTeam1Score() < 0) setTeam1Score(0);
   }

   public void addTeam2Score(int value) {
      this.setTeam2Score(this.getTeam2Score() + value);

      if (getTeam2Score() < 0) setTeam2Score(0);
   }

   public void addTeam3Score(int value) {
      this.setTeam3Score(this.getTeam3Score() + value);

      if (getTeam3Score() < 0) setTeam3Score(0);
   }

   public void addTeam4Score(int value) {
      this.setTeam4Score(this.getTeam4Score() + value);

      if (getTeam4Score() < 0) setTeam4Score(0);
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
      return this.team1Score;
   }

   public int getTeam2Score() {
      return this.team2Score;
   }

   public int getTeam3Score() {
      return this.team3Score;
   }

   public int getTeam4Score() {
      return this.team4Score;
   }
}
