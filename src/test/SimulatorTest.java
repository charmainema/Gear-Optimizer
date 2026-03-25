package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.gear.GearItem;
import main.model.gear.StatBoost;
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

        assertEquals(1, sim.getEnemies().size());
        assertEquals(enemy, sim.getEnemies().get(0));
        
        assertEquals(1, enemy.getEnemies().size());
        assertEquals(sim.getMainPlayer(), enemy.getEnemies().get(0));
    }

    @Test
    void testSimulateSingleRoundPlayerWin() {
        Player enemy = new Player(100, 1000, 10);
        sim.addEnemy(enemy);
        sim.getMainPlayer().addSpell(new Spell("S'more machine", "fire", 1110, 0, 0, 0, 1, 0, 100, false));
        enemy.addSpell(new Spell("weak spell", "ice", 10, 0, 0, 0, 1, 0, 100, false));
        
        HashMap<String, Double> battleStats = sim.simulate();
        assertEquals(1110.0, battleStats.get("max damage"));
        assertEquals(1110.0, battleStats.get("total damage"));
        assertEquals(1.0, battleStats.get("total rounds"));
        assertEquals(0, battleStats.get("damage received"));
        assertEquals(0, battleStats.get("total healing"));
        assertEquals(0, battleStats.get("total blocked"));
    }

    @Test
    void testSimulateMultipleRoundPlayerWin() {
        Player enemy = new Player(100, 1120, 10);
        sim.addEnemy(enemy);
        sim.getMainPlayer().addSpell(new Spell("S'more machine", "fire", 1110, 0, 0, 0, 1, 0, 100, false));
        enemy.addSpell(new Spell("weak spell", "ice", 10, 0, 0, 0, 1, 0, 100, false));
        
        HashMap<String, Double> battleStats = sim.simulate();
        assertEquals(1110.0, battleStats.get("max damage"));
        assertEquals(2220.0, battleStats.get("total damage"));
        assertEquals(2.0, battleStats.get("total rounds"));
        assertEquals(10, battleStats.get("damage received"));
        assertEquals(0, battleStats.get("total healing"));
        assertEquals(0, battleStats.get("total blocked"));

        assertEquals(3990, sim.getMainPlayer().getPlayerStats().getStat("health", null));
        assertFalse(sim.getMainPlayer().isDead());

        assertEquals(-1100, enemy.getPlayerStats().getStat("health", null));
        assertTrue(enemy.isDead());
        assertEquals(0, sim.getEnemies().size());
        assertEquals(0, enemy.getEnemies().size());
    }
}
