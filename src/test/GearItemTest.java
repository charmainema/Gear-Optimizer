package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.gear.GearItem;
import main.model.gear.StatBoost;
import main.model.gear.gearType.Robe;
import main.model.gear.setBonus.SetBonus;

public class GearItemTest {
    private GearItem testGearItem;

    @BeforeEach
    void runBefore() {
        testGearItem = new Robe("cool robe");
    }

    @Test
    void testConstructor() {
        assertEquals("cool robe", testGearItem.getName());
        assertEquals("robe", testGearItem.getType());
        assertEquals(0, testGearItem.getStatBoosts().size());
        assertEquals(null, testGearItem.getSetBonus());
    }

    @Test
    void testAddStatBoost() {
        ArrayList<StatBoost> stats = testGearItem.getStatBoosts();
        
        testGearItem.addStatBoost(new StatBoost(3, "myth", "damage"));
        assertEquals(1, stats.size());
        assertEquals(3, stats.get(0).getBoost());
        assertEquals("myth", stats.get(0).getSchool());
        assertEquals("damage", stats.get(0).getType());

        testGearItem.addStatBoost(new StatBoost(5, "balance", "power pip"));
        assertEquals(2, stats.size());
        assertEquals(3, stats.get(0).getBoost());
        assertEquals("myth", stats.get(0).getSchool());
        assertEquals("damage", stats.get(0).getType());
        assertEquals(5, stats.get(1).getBoost());
        assertEquals("balance", stats.get(1).getSchool());
        assertEquals("power pip", stats.get(1).getType());
    }

    @Test
    void testAddStatBoostSameTypeAndSchool() {
        ArrayList<StatBoost> stats = testGearItem.getStatBoosts();
        
        testGearItem.addStatBoost(new StatBoost(8, "death", "pierce"));
        testGearItem.addStatBoost(new StatBoost(2, "death", "damage"));
        testGearItem.addStatBoost(new StatBoost(3, "life", "damage"));
        assertEquals(3, stats.size());
        assertEquals(8, stats.get(0).getBoost());
        assertEquals("death", stats.get(0).getSchool());
        assertEquals("pierce", stats.get(0).getType());
        assertEquals(2, stats.get(1).getBoost());
        assertEquals("death", stats.get(1).getSchool());
        assertEquals("damage", stats.get(1).getType());
        assertEquals(3, stats.get(2).getBoost());
        assertEquals("life", stats.get(2).getSchool());
        assertEquals("damage", stats.get(2).getType());

        testGearItem.addStatBoost(new StatBoost(5, "death", "damage"));
        assertEquals(3, stats.size());
        assertEquals(8, stats.get(0).getBoost());
        assertEquals("death", stats.get(0).getSchool());
        assertEquals("pierce", stats.get(0).getType());
        assertEquals(5, stats.get(1).getBoost());
        assertEquals("death", stats.get(1).getSchool());
        assertEquals("damage", stats.get(1).getType());
        assertEquals(3, stats.get(2).getBoost());
        assertEquals("life", stats.get(2).getSchool());
        assertEquals("damage", stats.get(2).getType());
    }

    @Test
    void testRemoveSetBonus() {
        testGearItem.setSetBonus(new SetBonus("set bonus 1"));
        assertEquals("set bonus 1", testGearItem.getSetBonus().getName());

        testGearItem.removeSetBonus();
        assertEquals(null, testGearItem.getSetBonus());
    }
}
