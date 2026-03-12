package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.StatBoost;
import main.model.gear.GearItem;
import main.model.gear.gearType.Hat;
import main.model.gear.gearType.Mount;
import main.model.gear.gearType.Robe;
import main.model.simulator.Simulator;
import main.model.simulator.Spell;
import main.model.simulator.player.Player;
import main.model.simulator.player.PlayerGear;

public class SimulatorTest {
    Simulator sim;
    Player enemy1;
    PlayerGear gear;
    Spell lifeSpell1;
    Spell stormSpell1;

    @BeforeEach
    void runBefore() {
        enemy1 = new Player(30, 1500, 800);
        sim = new Simulator(60, 4000, 1000);
        lifeSpell1 = new Spell("Leprechaun", "life", 300, 0, 0, 0, 3, 10, 95, false);
        stormSpell1 = new Spell("Storm Shark", "storm", 500, 0, 0, 0, 3, 10, 70, false);
    }

    @Test
    void testFizzleNoAccuracy() {
        Spell noAccuracy = new Spell("fizzle", "life", 0, 0, 0, 0, 0, 0, 0, false);
        sim.getMainPlayer().updateStats("accuracy", "life", 0);
        assertEquals(0, sim.getMainPlayer().getPlayerStats().getStat("accuracy", "life"));
        assertTrue(sim.fizzle(sim.getMainPlayer(), noAccuracy, Math.random()));
    }

    @Test
    void testFizzleNoFizzle() {
        sim.getMainPlayer().updateStats("accuracy", "life", 10);
        assertFalse(sim.fizzle(sim.getMainPlayer(), lifeSpell1, Math.random()));
    }

    @Test
    void testFizzlePossibleFizzleNoFizzle() {
        assertFalse(sim.fizzle(sim.getMainPlayer(), stormSpell1, 0.7));
        assertFalse(sim.fizzle(sim.getMainPlayer(), stormSpell1, 0.69));
    }

    @Test
    void testFizzlePossibleFizzleWillFizzle() {
        assertTrue(sim.fizzle(sim.getMainPlayer(), stormSpell1, 0.71));
        assertTrue(sim.fizzle(sim.getMainPlayer(), stormSpell1, 0.9));
    }

    @Test
    void testSetGear() {
        ArrayList<GearItem> gearItems = new ArrayList<>();

        GearItem hat = new Hat("cool hat");
        hat.addStatBoost(new StatBoost(10, "life", "pierce"));

        GearItem mount = new Mount("cool mount");
        mount.addStatBoost(new StatBoost(30, "ice", "resist"));

        GearItem robe = new Robe("cool robe");
        robe.addStatBoost(new StatBoost(30, "life", "pierce"));

        GearItem hat2 = new Hat("another cool hat");
        hat2.addStatBoost(new StatBoost(20, "life", "pierce"));

        gearItems.add(hat);
        gearItems.add(mount);
        gearItems.add(robe);
        gearItems.add(hat2);
        
        sim.setGear(gearItems);

        assertEquals(50, sim.getMainPlayer().getPlayerStats().getStat("pierce", "life"));
        assertEquals(30, sim.getMainPlayer().getPlayerStats().getStat("resist", "ice"));
    }

    @Test
    void testAddEnemy() {
        Player enemy = new Player(100, 10, 10);
        sim.addEnemy(enemy);
        assertEquals(1, sim.getMainPlayer().getEnemies().size());
        assertEquals(enemy, sim.getMainPlayer().getEnemies().get(0));
    }
}
