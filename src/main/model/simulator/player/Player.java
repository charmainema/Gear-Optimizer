package main.model.simulator.player;

import java.util.ArrayList;
import java.util.HashMap;

import main.model.GearCalculator;
import main.model.simulator.Simulator;
import main.model.simulator.Spell;

public class Player {
    public static final int MAX_PIPS = 14;

    private int level;
    private PlayerStats stats;

    private ArrayList<Spell> spells;
    private ArrayList<Spell> currentHand;
    private int pips;
    private HashMap<String, ArrayList<Integer>> shields;
    private HashMap<String, ArrayList<Integer>> wards;

    private ArrayList<Player> enemies;

    private GearCalculator calculator;
    private Simulator sim;

    // EFFECTS: constructs a player with initial level, health, and mana, and empty
    // stats, gear, spells, hand, shields,
    // and wards, and 1 pip
    public Player(int level, int health, int mana, Simulator sim) {
        this.level = level;
        this.sim = sim;
        stats = new PlayerStats(health, mana);
        spells = new ArrayList<>();
        currentHand = new ArrayList<>();
        pips = 0;
        shields = new HashMap<>();
        wards = new HashMap<>();
        enemies = new ArrayList<>();
        calculator = new GearCalculator();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // MODIFIES: this
    // EFFECTS: adds spell to spells
    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    // MODIFIES: this
    // EFFECTS: removes spell from spells
    public void removeSpell(Spell spell) {
        spells.remove(spell);
    }

    // MODIFIES: this
    // EFFECTS: adds enemy to enemies
    public void addEnemy(Player enemy) {
        enemies.add(enemy);
    }

    // MODIFIES: this
    // EFFECTS: adds pip or power pip to pips if pips < MAX_PIPS
    public void addPips(int numPips) {
        if (pips == Player.MAX_PIPS) {
            return;
        } else if (pips + numPips > Player.MAX_PIPS) {
            pips = Player.MAX_PIPS;
        }
        pips += numPips;
    }

    // MODIFIES: this
    // EFFECTS: adds ward associated with school to wards
    private void addWard(String school, int ward) {
        ArrayList<Integer> wardBoosts;
        if (!wards.containsKey(school)) {
            wardBoosts = new ArrayList<>();
            wards.put(school, wardBoosts);
        } else {
            wardBoosts = wards.get(school);
        }
        wardBoosts.add(ward);
    }

    // MODIFIES: this
    // EFFECTS: adds shield associated with school to shields
    private void addShield(String school, int ward) {
        ArrayList<Integer> shieldBoosts;
        if (!shields.containsKey(school)) {
            shieldBoosts = new ArrayList<>();
            shields.put(school, shieldBoosts);
        } else {
            shieldBoosts = shields.get(school);
        }
        shieldBoosts.add(ward);
    }

    // MODIFIES: this
    // EFFECTS: if spells is empty, does nothing
    // else if currentHand is empty or spell is null, generates a random number
    // of 7 random spells from spells
    // else removes spell from currentHand and replaces it with a new random
    // spell copy from spells
    public void updateHand(Spell spell) {
        if (spells.isEmpty()) {
            return;
        } else if (currentHand.isEmpty() || spell == null) {
            generateHand();
        } else {
            respellHand(spell);
        }
    }

    // MODIFIES: this
    // EFFECTS: generate hand of 7 random spells from spells
    private void generateHand() {
        if (!currentHand.isEmpty()) {
            currentHand.removeAll(currentHand);
        }
        for (int i = 0; i < 7; i++) {
            addRandomSpellToHand();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes spell from currentHand and replaces it with a new random
    // spell from spells
    private void respellHand(Spell spell) {
        currentHand.remove(spell);
        addRandomSpellToHand();
    }

    // MODIFIES: this
    // EFFECTS: adds random spell from spells to currentHand
    private void addRandomSpellToHand() {
        int random = (int) (Math.random() * (spells.size()));
        currentHand.add(spells.get(random));
    }

    // REQUIRES: spell is in current hand, and this player has sufficient mana and
    // pips
    // MODIFIES: this
    // EFFECTS: if spell's aoe == false, casts spell on first enemy,
    // else if spell's aoe == true, casts spell on all enemies
    // - updates currentHand and subtracts spell's required pips from pips
    // the gear calculator is used to calculate boost rates for outgoing damage, and
    // fizzle/cast chance
    // - deducts spell's required mana and pips from player
    public void castSpell(Spell spell) {
        if (spell.getAoe() == true) {
            for (Player enemy : enemies) {
                doDamage(spell, enemy);
            }
        } else {
            doDamage(spell, enemies.get(0));
        }

        updateHand(spell);
        stats.updateStats("mana", null, spell.getRequiredMana() * -1);
        stats.updateStats("health", null, spell.getHealing());
        updateWardsAndShields(spell);
        pips -= spell.getRequiredPips();
    }

    // MODIFIES: this
    // EFFECTS: adds spell's ward to wards if ward != 0,
    // and adds spell's shield to shields if shield != 0
    private void updateWardsAndShields(Spell spell) {
        if (spell.getWard() != 0) {
            addWard(spell.getSchool(), spell.getWard());
        }

        if (spell.getShield() != 0) {
            addShield(spell.getSchool(), spell.getShield());
        }
    }

    // MODIFIES: this
    // EFFECTS: calculates total damage output using calculator, and deducts health
    // from enemy
    private void doDamage(Spell spell, Player enemy) {
        if (spell.getDamage() == 0) {
            return;
        }

        String spellSchool = spell.getSchool();

        double damageMultiplier = calculator.calculateDamageMultiplier(stats.getStat("damage", spellSchool));
        double wardMultiplier = getWardFactor(spellSchool);
        double pierceMultiplier = getPierceMultiplier(spellSchool, enemy);

        double boostMultiplier = damageMultiplier + wardMultiplier + pierceMultiplier;
        int boostedDamage = (int) (boostMultiplier * spell.getDamage());

        double critMultiplier = castCritical(spellSchool, enemy);
        int totalDamage = Math.max((int) (critMultiplier * boostedDamage), 0);

        enemy.getPlayerStats().updateStats("health", null, totalDamage * -1);
    }

    // EFFECTS: returns player's pierce - enemy's shield - enemy's resist
    private double getPierceMultiplier(String spellSchool, Player enemy) {
        int pierce = stats.getStat("pierce", spellSchool);
        int enemyShield = enemy.getShieldFactor(spellSchool);
        int enemyResist = enemy.getPlayerStats().getStat("resist", spellSchool);
        return calculator.calculatePierceMultiplier(pierce - enemyResist - enemyShield);
    }

    // EFFECTS: if randomCrit < player's critChance, returns crit multiplier as
    // determined
    // by calculator
    // otherwise returns 0.0
    private double castCritical(String spellSchool, Player enemy) {
        double critChance = Math.pow(calculator.calculateCritChanceMultiplier(stats.getStat("critical", spellSchool),
                enemy.getPlayerStats().getStat("block", spellSchool), level), 2);
        double randomCrit = Math.random();
        double critMultiplier = calculator.calculateCritDmgMultiplier(stats.getStat("critical", spellSchool),
                enemy.getPlayerStats().getStat("block", spellSchool));

        if (randomCrit < critChance) {
            return critMultiplier;
        } else {
            return 1.0;
        }
    }

    // EFFECTS: returns percentage of damage deflected by first shield in shields associated with school
    public int getShieldFactor(String school) {
        if (shields.containsKey(school) && shields.get(school).size() > 0) {
            return shields.get(school).remove(0);
        }
        return 0;
    }

    // EFFECTS: returns percentage of damage boosted by first ward in wards associated with school
    public double getWardFactor(String school) {
        if (wards.containsKey(school) && wards.get(school).size() > 0) {
            return (double) wards.get(school).remove(0) / 100;
        }
        return 0;
    }

    public int getLevel() {
        return level;
    }

    public PlayerStats getPlayerStats() {
        return stats;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public ArrayList<Spell> getCurrentHand() {
        return currentHand;
    }

    public int getPips() {
        return pips;
    }

    public double getPowerPipConversionRate() {
        return calculator.calculatePowerPipConversionRate(stats.getStat("power pip", null));
    }

    public HashMap<String, ArrayList<Integer>> getShields() {
        return shields;
    }

    public HashMap<String, ArrayList<Integer>> getWards() {
        return wards;
    }

    public ArrayList<Player> getEnemies() {
        return enemies;
    }
}
