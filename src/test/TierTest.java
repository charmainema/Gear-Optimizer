package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.gear.StatBoost;
import main.model.gear.setBonus.Tier;

public class TierTest {
    private Tier testTier;
    private StatBoost boost1;
    private StatBoost boost2;

    @BeforeEach
    void runBefore() {
        testTier = new Tier();
        boost1 = new StatBoost(10, "fire", "damage");
        boost2 = new StatBoost(3, "storm", "resist");
    }

    @Test
    void testConstructor() {
        List<StatBoost> boosts = testTier.getStatBoosts();
        assertEquals(0, boosts.size());
    }

    @Test
    void testAddStatBoost() {
        List<StatBoost> boosts = testTier.getStatBoosts();

        testTier.addStatBoost(boost1);
        assertEquals(1, boosts.size());
        assertEquals(10, boosts.get(0).getBoost());
        assertEquals("fire", boosts.get(0).getSchool());
        assertEquals("damage", boosts.get(0).getType());

        testTier.addStatBoost(boost2);
        assertEquals(2, boosts.size());
        assertEquals(10, boosts.get(0).getBoost());
        assertEquals("fire", boosts.get(0).getSchool());
        assertEquals("damage", boosts.get(0).getType());
        assertEquals(3, boosts.get(1).getBoost());
        assertEquals("storm", boosts.get(1).getSchool());
        assertEquals("resist", boosts.get(1).getType());
    }

    @Test
    void testRemoveStatBoostNotInTier() {
        List<StatBoost> boosts = testTier.getStatBoosts();
        testTier.addStatBoost(boost1);
        assertEquals(1, boosts.size());

        testTier.removeStatBoost(new StatBoost(2, "death", "critical"));
        assertEquals(1, boosts.size());
        assertEquals(boost1, boosts.get(0));
    }

    @Test
    void testRemoveStatBoost() {
        List<StatBoost> boosts = testTier.getStatBoosts();
        testTier.addStatBoost(boost1);
        testTier.addStatBoost(boost2);
        assertEquals(2, boosts.size());

        testTier.removeStatBoost(boost2);
        assertEquals(1, boosts.size());
        assertEquals(boost1, boosts.get(0));

        testTier.removeStatBoost(boost1);
        assertEquals(0, boosts.size());
    }
}
