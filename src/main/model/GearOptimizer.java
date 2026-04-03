package main.model;

import java.util.ArrayList;
import java.util.HashMap;

import main.model.gear.GearItem;
import main.model.simulator.Simulator;
import main.model.simulator.Spell;
import main.model.simulator.player.Player;

public class GearOptimizer {
    private HashMap<String, ArrayList<GearItem>> gearSets;
    private Simulator sim;

    public GearOptimizer(int level, int health, int mana) {
        gearSets = new HashMap<>();
        sim = new Simulator(level, health, mana);
    }

    // EFFECTS: adds a PlayerGear to gearSets using gear array
    public void addGearSet(String name, ArrayList<GearItem> gear) {
        gearSets.put(name, gear);
    }

    // EFFECTS: adds an enemy to sim
    public void addEnemy(int level, int health, int mana) {
        sim.addEnemy(new Player(level, health, mana));
    }

    // EFFECTS: add a boost to enemy at index, if it exists
    public void addBoostToEnemy(int index, String type, String school, int boost) {
        if (!(index >= sim.getEnemies().size())) {
            Player enemy = sim.getEnemies().get(index);
            enemy.updateStats(type, school, boost);
        }
    }

    // EFFECTS: adds spell to main player
    public void addSpellToPlayer(String name, String school, int damage, int healing, int shield, int ward, int requiredPips,
    int requiredMana, int accuracy, boolean aoe) {
        sim.getMainPlayer().addSpell(new Spell(name, school, damage, healing, shield, ward, requiredPips, requiredMana, accuracy, aoe));
    }

    // EFFECTS: adds spell to enemy at index, if it exists
    public void addSpellToEnemy(int index, String name, String school, int damage, int healing, int shield, int ward, int requiredPips,
    int requiredMana, int accuracy, boolean aoe) {
        if (index >= sim.getEnemies().size()) {
            return;
        }

        sim.getEnemies().get(index).addSpell(new Spell(name, school, damage, healing, shield, ward, requiredPips, requiredMana, accuracy, aoe));
    }

    // EFFECTS: Returns the best possible set of gear from gearSets
    public HashMap<String, ArrayList<GearItem>> optimizeGear() {
        String currentBestSet = "";
        double bestDamage = 0;

        for (String key : gearSets.keySet()) {
            ArrayList<GearItem> gearSet = gearSets.get(key);
            sim.setGear(gearSet);
            HashMap<String, Double> battleStats = sim.simulate();

            if (battleStats == null) {
                continue;
            }

            double currentDamage = (double)battleStats.get("total damage") / (double)battleStats.get("total rounds");
            if (currentDamage > bestDamage) {
                currentBestSet = key;
                bestDamage = currentDamage;
            }
        }

        HashMap<String, ArrayList<GearItem>> bestSet = new HashMap<>();
        bestSet.put(currentBestSet, gearSets.get(currentBestSet));
        return bestSet;
    }

    public HashMap<String, ArrayList<GearItem>> getGearSets() {
        return gearSets;
    }

    public Player getMainPlayer() {
        return sim.getMainPlayer();
    }

    public Simulator getSimulator() {
        return sim;
    }
}
