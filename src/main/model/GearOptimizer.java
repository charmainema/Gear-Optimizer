package main.model;

import java.util.ArrayList;
import java.util.HashMap;

import main.model.gear.GearItem;
import main.model.simulator.Simulator;
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

    // EFFECTS: Returns the best possible set of gear from gearSets
    public HashMap<String, ArrayList<GearItem>> optimizeGear() {
        // TODO
        return new HashMap<>();
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
