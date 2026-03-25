package main.model.gear.setBonus;

import java.util.ArrayList;
import java.util.List;

import main.model.gear.StatBoost;

// represents a set bonus tier with a list of stat boosts
public class Tier {
    private List<StatBoost> statBoosts;

    // EFFECTS: constructs a tier with a list of stat boosts
    public Tier() {
        statBoosts = new ArrayList<>();
    }

    // REQUIRES: statBoost != null
    // MODIFIES: this
    // EFFECTS: adds statBoost to statBoosts
    public void addStatBoost(StatBoost statBoost) {
        statBoosts.add(statBoost);
    }

    // MODIFIES: this
    // EFFECTS: removes statBoost from statBoosts
    public void removeStatBoost(StatBoost statBoost) {
        statBoosts.remove(statBoost);
    }

    public List<StatBoost> getStatBoosts() {
        return statBoosts;
    }
}
