package main.model.simulator;

import java.util.HashMap;

import main.model.simulator.player.Player;
import main.model.simulator.player.PlayerGear;

public class Simulator {

    private Player mainPlayer;
    private PlayerGear gear;

    public Simulator(int level, int health, int mana) {
        mainPlayer = new Player(level, health, mana, this);
        gear = new PlayerGear(mainPlayer);
    }

    // EFFECTS: returns true if total accuracy (from spell + player) expressed as
    // percentage < random decimal in range [0, 1], or
    // if total accuracy == 0
    public boolean fizzle(Player player, Spell spell) {
        double accuracy = (double) (spell.getAccuracy()
                + player.getPlayerStats().getStat("accuracy", spell.getSchool()) / 100);
        double randomCast = Math.random();
        return accuracy < randomCast || accuracy == 0;
    }

    // EFFECTS: returns true if player's mana < spell's required mana
    private boolean insufficientMana(Player player, Spell spell) {
        return player.getPlayerStats().getStat("mana", null) < spell.getRequiredMana();
    }

    // EFFECTS: returns false if player's pips < spell's required pips
    private boolean insufficientPips() {
        // TODO
        return false;
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

    // EFFECTS: adds enemy to player
    public void addEnemy() {
        // TODO
    }

    // EFFECTS: simulates 1 round of battle, updating summary stats
    private void simulateRound(HashMap<String, Double> battleStats) {
        // TODO
    }

    // EFFECTS: simulates battle until either mainPlayer dies, or all of
    // mainPlayer's enemies die, returns battle summary stats such as total damage
    // dealt, total damage received, num rounds, etc.
    private HashMap<String, Double> simulate() {
        // TODO
        return new HashMap<>();
    }
}
