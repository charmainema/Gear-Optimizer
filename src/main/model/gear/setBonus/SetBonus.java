package main.model.gear.setBonus;

import java.util.ArrayList;
import java.util.List;

// represents a gear set bonus with tiers of set bonus stat boosts
// listed in chronological order based on list position
// ex. tier 1 is the first item in tiers, and is fulfilled if the condition
// for tier 1 is met
public class SetBonus {
    private String name;
    private List<Tier> tiers;
    private List<Integer> tierConditions;

    // EFFECTS: constructs a set bonus with a name and no tiers
    public SetBonus(String name) {
        this.name = name;
        tiers = new ArrayList<>();
        tierConditions = new ArrayList<>();
    }

    // REQUIRES: tier != null
    // MODIFIES: this
    // EFFECTS: adds tier to tiers
    public void addTier(Tier tier) {
        tiers.add(tier);
    }

    // MODIFIES: this
    // EFFECTS: removes tier from tiers
    public void removeTier(Tier tier) {
        tiers.remove(tier);
    }

    // REQUIRES: condition >= 0
    // MODIFIES: this
    // EFFECTS: adds condition to tierConditions
    public void addTierCondition(int condition) {
        tierConditions.add(condition);
    }

    // REQUIRES: conditions.size() > 0 && 0 <= condition <= conditions.size() - 1
    // MODIFIES: this
    // EFFECTS: removes condition with index tierNum from conditions
    public void removeTierCondition(int tierNum) {
        tierConditions.remove(tierNum);
    }

    // MODIFIES: this
    // EFFECTS: sets this.name to name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Tier> getTiers() {
        return tiers;
    }

    public List<Integer> getTierConditions() {
        return tierConditions;
    }
}
