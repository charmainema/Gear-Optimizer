package main.model.gear;

import main.model.StatBoost;
import main.model.setBonus.SetBonus;
import main.model.simulator.player.PlayerGear;

import java.util.ArrayList;

// represents a gear item with item stats
public abstract class GearItem {
    private String name;
    private String type;
    // dictionary of stat boosts of mapped as stat type : list of stats of said type
    private ArrayList<StatBoost> statBoosts;
    private SetBonus setBonus;

    // EFFECTS: constructs a gear item with a name and type, and an empty list of
    // stat boosts
    public GearItem(String name, String type) {
        this.name = name;
        this.type = type;
        statBoosts = new ArrayList<>();
        setBonus = null;
    }

    // MODIFIES: this
    // EFFECTS: if stat boost with same type and school is already in stats, replaces
    // existing stat boost with new statBoost
    // otherwise adds statBoost to statBoosts
    public void addStatBoost(StatBoost statBoost) {
        if (statBoosts.contains(statBoost)) {
            StatBoost boost = getStatBoost(statBoost);
            boost.setBoost(statBoost.getBoost());
        } else {
            statBoosts.add(statBoost);
        }
    }

    // REQUIRES: statBoost is in statBoosts
    // MODIFIES: this
    // EFFECTS: replaces stat boost in stats with the same school
    // and type as statBoost
    private StatBoost getStatBoost(StatBoost statBoost) {
        StatBoost stat = null;
        for (StatBoost boost : statBoosts) {
            if (boost.equals(statBoost)) {
                stat = boost;
            }
        }
        return stat;
    }

    public void setSetBonus(SetBonus setBonus) {
        this.setBonus = setBonus;
    }

    // MODIFIES: this
    // EFFECTS: sets setBonus to null
    public void removeSetBonus() {
        setBonus = null;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ArrayList<StatBoost> getStatBoosts() {
        return statBoosts;
    }

    public SetBonus getSetBonus() {
        return setBonus;
    }

    public abstract void setGear(PlayerGear gear);

    public abstract void removeItem(PlayerGear gear);
}
