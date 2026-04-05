package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.GearOptimizer;
import main.model.gear.GearItem;
import main.model.gear.StatBoost;
import main.model.gear.gearType.Amulet;
import main.model.gear.gearType.Hat;
import main.model.simulator.Simulator;
import main.model.simulator.player.Player;

public class GearOptimizerTest {
    private GearOptimizer optimizer;
    private ArrayList<GearItem> worstGear;
    private ArrayList<GearItem> bestGear;

    @BeforeEach
    void runBefore() {
        optimizer = new GearOptimizer(100, 10000, 10000);
        worstGear = new ArrayList<>();
        bestGear = new ArrayList<>();
        initGear();
    }

    @Test
    void testAddGearSetOneSet() {
        ArrayList<GearItem> gear = new ArrayList<>();
        gear.add(new Amulet("cool amulet"));
        optimizer.addGearSet("set 1", gear);

        HashMap<String, ArrayList<GearItem>> gearSets = optimizer.getGearSets();
        assertEquals(1, gearSets.size());

        assertEquals(1, gearSets.keySet().size());
        assertEquals("cool amulet", gearSets.get("set 1").get(0).getName());
    }

    @Test
    void testGetters() {
        // optimizer will create the player, so getter should not return null
        Player player = optimizer.getMainPlayer();
        assertNotNull(player);

        Simulator sim = optimizer.getSimulator();
        assertNotNull(sim);
    }

    @Test
    void testOptimizeGearOneSet() {
        optimizer.addSpellToPlayer("some spell", "life", 100, 0, 0, 0, 0, 0, 100, false);
        optimizer.addGearSet("worst", worstGear);

        optimizer.addEnemy(10, 2000, 10000);
        optimizer.addSpellToEnemy(0, "another spell", "fire", 100, 0, 0, 0, 0, 0, 100, false);
        
        HashMap<String, ArrayList<GearItem>> optimizedGear = optimizer.optimizeGear();
        assertNotNull(optimizedGear.get("worst"));
    }

    @Test
    void testOptimizeGearMultipleSets() {
        optimizer.addSpellToPlayer("some spell", "life", 100, 0, 0, 0, 0, 0, 100, false);
        optimizer.addGearSet("best", bestGear);
        optimizer.addGearSet("worst", worstGear);

        optimizer.addEnemy(10, 2000, 10000);
        optimizer.addSpellToEnemy(0, "another spell", "fire", 100, 0, 0, 0, 0, 0, 100, false);
        
        HashMap<String, ArrayList<GearItem>> optimizedGear = optimizer.optimizeGear();
        assertNotNull(optimizedGear.get("best"));
    }

    private void initGear() {
        GearItem bestHat = new Hat("best hat");
        bestHat.addStatBoost(new StatBoost(100, "life", "damage"));
        bestGear.add(bestHat);

        GearItem worstHat = new Hat("worst hat");
        bestHat.addStatBoost(new StatBoost(10, "life", "damage"));
        worstGear.add(worstHat);
    }
}
