package main.model.simulator;

import java.util.ArrayList;
import java.util.HashMap;

import main.model.gear.GearItem;
import main.model.simulator.player.Player;
import main.model.simulator.player.PlayerGear;

public class Simulator {

    private Player mainPlayer;
    private PlayerGear gear;

    public Simulator(int level, int health, int mana) {
        mainPlayer = new Player(level, health, mana);
        gear = new PlayerGear(mainPlayer);
    }

    // EFFECTS: updates pips of player
    private void updatePips(Player player) {
        if (player.getPips() == Player.MAX_PIPS) {
            return;
        }

        double powerPipRate = player.getPowerPipConversionRate();
        double randomPowerPipRate = Math.random();

        if (randomPowerPipRate < powerPipRate) {
            player.addPips(2);
        } else {
            player.addPips(1);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets gear item for mainPlayer if it exists in gearItems
    public void setGear(ArrayList<GearItem> gearItems) {
        for (GearItem gearItem : gearItems) {
            gearItem.setGear(gear);
        }
    }

    // EFFECTS: adds enemy to mainPlayer, and adds mainPlayer as and enemy
    // to enemy
    public void addEnemy(Player enemy) {
        mainPlayer.addEnemy(enemy);
        enemy.addEnemy(mainPlayer);
    }

    // MODIFIES: this
    // EFFECTS: adds pip/power pip to all enemies and main player
    private void pipAll() {
        updatePips(mainPlayer);

        for (Player enemy : getEnemies()) {
            updatePips(enemy);
        }
    }

    // EFFECTS: mainPlayer casts a random spell, updating battleStats
    private void castMainPlayer(HashMap<String, Double> battleStats) {
        HashMap<String, Double> playerCastStats = mainPlayer.castRandom();
        double playerDamage = playerCastStats.get("damage");
        double playerHealing = playerCastStats.get("healing");

        battleStats.put("total healing", battleStats.getOrDefault("total healing", 0.0) + playerHealing);
        battleStats.put("total damage", battleStats.getOrDefault("total damage", 0.0) + playerDamage);

        if (battleStats.get("max damage") == null || battleStats.get("max damage") < playerDamage) {
            battleStats.put("max damage", playerDamage);
        }
    }

    // EFFECTS: all enemies and main player cast a random spell from their deck,
    // update battleStats
    private void castEnemies(HashMap<String, Double> battleStats) {
        for (Player enemy : getEnemies()) {
            double enemyDamage = enemy.castRandom().get("damage");
            battleStats.put("damage received", battleStats.getOrDefault("damage received", 0.0) + enemyDamage);
        }
    }

    // EFFECTS: simulates 1 round of battle, updating summary stats
    // 1. adds pip/power pip to all of main player and enemies
    // 2. main player casts random spell from deck on first enemy in list of enemies
    // 3. enemies cast random spell from deck on main player
    // 4. update battleStats
    private void simulateRound(HashMap<String, Double> battleStats) {
        pipAll();
        castMainPlayer(battleStats);
        castEnemies(battleStats);
        battleStats.put("total rounds", battleStats.getOrDefault("total rounds", 0.0) + 1.0);
    }

    private void initBattleStats(HashMap<String, Double> battleStats) {
        battleStats.put("max damage", 0.0);
        battleStats.put("total damage", 0.0);
        battleStats.put("total rounds", 0.0);
        battleStats.put("damage received", 0.0);
        battleStats.put("total healing", 0.0);
        battleStats.put("total blocked", 0.0);
    }

    // EFFECTS: simulates battle until either mainPlayer dies, or all of
    // mainPlayer's enemies die, returns battle summary stats such as total damage
    // dealt, total damage received, num rounds, etc.
    public HashMap<String, Double> simulate() {
        HashMap<String, Double> battleStats = new HashMap<>();
        initBattleStats(battleStats);

        while (!mainPlayer.isDead() && getEnemies().size() > 0) {
            simulateRound(battleStats);
        }

        return battleStats;
    }

    public PlayerGear getGear() {
        return gear;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public ArrayList<Player> getEnemies() {
        return mainPlayer.getEnemies();
    }
}
