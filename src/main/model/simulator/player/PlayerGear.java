package main.model.simulator.player;

import main.model.StatBoost;
import main.model.gear.GearItem;

public class PlayerGear {
    private GearItem hat;
    private GearItem robe;
    private GearItem boots;
    private GearItem wand;
    private GearItem athame;
    private GearItem amulet;
    private GearItem ring;
    private GearItem deck;
    private GearItem pet;
    private GearItem mount;

    private Player player;

    // EFFECTS: constructs empty PlayerGear
    public PlayerGear(Player player) {
        this.player = player;
    }

    // MODIFIES: this
    // EFFECTS: updates stats for all stat boosts in item
    private void updateStats(GearItem item) {
        PlayerStats stats = player.getPlayerStats();
        for (StatBoost boost : item.getStatBoosts()) {
            stats.updateStats(boost.getType(), boost.getSchool(), boost.getBoost());
        }
    }

    // MODIFIES: this
    // EFFECTS: sets item to null and removes stat boosts
    public void unequip(GearItem item) {
        PlayerStats stats = player.getPlayerStats();
        for (StatBoost boost : item.getStatBoosts()) {
            stats.updateStats(boost.getType(), boost.getSchool(), boost.getBoost() * -1);
        }

        item.removeItem(this);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setHat(GearItem hat) {
        if (this.hat != null) {
            unequip(this.hat);
        }
        this.hat = hat;
        updateStats(hat);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setRobe(GearItem robe) {
        if (this.robe != null) {
            unequip(this.robe);
        }
        this.robe = robe;
        updateStats(robe);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setBoots(GearItem boots) {
        if (this.boots != null) {
            unequip(this.boots);
        }
        this.boots = boots;
        updateStats(boots);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setWand(GearItem wand) {
        if (this.wand != null) {
            unequip(this.wand);
        }
        this.wand = wand;
        updateStats(wand);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setAthame(GearItem athame) {
        if (this.athame != null) {
            unequip(this.athame);
        }
        this.athame = athame;
        updateStats(athame);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setAmulet(GearItem amulet) {
        if (this.amulet != null) {
            unequip(this.amulet);
        }
        this.amulet = amulet;
        updateStats(amulet);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setRing(GearItem ring) {
        if (this.ring != null) {
            unequip(this.ring);
        }
        this.ring = ring;
        updateStats(ring);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setDeck(GearItem deck) {
        if (this.deck != null) {
            unequip(this.deck);
        }
        this.deck = deck;
        updateStats(deck);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setPet(GearItem pet) {
        if (this.pet != null) {
            unequip(this.pet);
        }
        this.pet = pet;
        updateStats(pet);
    }

    // MODIFIES: this
    // EFFECTS: sets hat to hat and updates stats
    public void setMount(GearItem mount) {
        if (this.mount != null) {
            unequip(this.mount);
        }
        this.mount = mount;
        updateStats(mount);
    }

    public void removeHat() {
        this.hat = null;
    }

    public void removeRobe() {
        this.robe = null;
    }

    public void removeBoots() {
        this.boots = null;
    }

    public void removeWand() {
        this.wand = null;
    }

    public void removeAthame() {
        this.athame = null;
    }

    public void removeAmulet() {
        this.amulet = null;
    }

    public void removeRing() {
        this.ring = null;
    }

    public void removeDeck() {
        this.deck = null;
    }

    public void removePet() {
        this.pet = null;
    }

    public void removeMount() {
        this.mount = null;
    }

    public GearItem getHat() {
        return hat;
    }

    public GearItem getRobe() {
        return robe;
    }

    public GearItem getBoots() {
        return boots;
    }

    public GearItem getWand() {
        return wand;
    }

    public GearItem getAthame() {
        return athame;
    }

    public GearItem getAmulet() {
        return amulet;
    }

    public GearItem getRing() {
        return ring;
    }

    public GearItem getDeck() {
        return deck;
    }

    public GearItem getPet() {
        return pet;
    }

    public GearItem getMount() {
        return mount;
    }
}
