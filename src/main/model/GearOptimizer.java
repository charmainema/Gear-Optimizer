package main.model;

import java.util.ArrayList;
import java.util.HashMap;

import main.model.gear.GearItem;
import main.model.simulator.Simulator;
import main.model.simulator.player.Player;
import main.model.simulator.player.PlayerGear;

public class GearOptimizer {
    private ArrayList<HashMap<String, PlayerGear>> gearSets;
    private Simulator sim;

    public GearOptimizer(int level, int health, int mana) {
        // TODO
    }

    public void addGearSet(String name, PlayerGear gear) {
        // TODO
    }

    // EFFECTS: Returns the best possible set of gear from gearSets
    public HashMap<String, GearItem> optimizeGear() {
        // TODO
        return new HashMap<>();
    }

    public ArrayList<HashMap<String, PlayerGear>> getGearSets() {
        return gearSets;
    }

    public Player getMainPlayer() {
        return new Player(0, 0, 0);
    }

    public Simulator getSimulator() {
        return sim;
    }
}
