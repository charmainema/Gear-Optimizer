package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.gear.StatBoost;
import main.model.gear.setBonus.SetBonus;
import main.model.gear.setBonus.Tier;

public class SetBonusTest {
    private SetBonus testSetBonus;
    private Tier tier1;
    private Tier tier2;
    private Tier tier3;

    @BeforeEach
    void runBefore() {
        testSetBonus = new SetBonus("Dragoon Set");
        tier1 = new Tier();
        tier2 = new Tier();
        tier3 = new Tier();
    }

    @Test
    void testConstructor() {
        assertEquals("Dragoon Set", testSetBonus.getName());
        assertEquals(0, testSetBonus.getTiers().size());
        assertEquals(0, testSetBonus.getTierConditions().size());
    }

    @Test
    void testAddTier() {
        List<Tier> tiers = testSetBonus.getTiers();

        tier1.addStatBoost(new StatBoost(3, "life", "accuracy"));
        testSetBonus.addTier(tier1);

        assertEquals(1, tiers.size());
        assertEquals(3, tiers.get(0).getStatBoosts().get(0).getBoost());
        assertEquals("life", tiers.get(0).getStatBoosts().get(0).getSchool());
        assertEquals("accuracy", tiers.get(0).getStatBoosts().get(0).getType());

        testSetBonus.addTier(tier2);
        testSetBonus.addTier(tier3);
        assertEquals(3, tiers.size());
        assertEquals(tier1, tiers.get(0));
        assertEquals(tier2, tiers.get(1));
        assertEquals(tier3, tiers.get(2));
    }

    @Test
    void testRemoveTier() {
        List<Tier> tiers = testSetBonus.getTiers();

        testSetBonus.addTier(tier1);
        testSetBonus.addTier(tier2);
        testSetBonus.addTier(tier3);
        assertEquals(3, tiers.size());

        testSetBonus.removeTier(tier2);
        assertEquals(2, tiers.size());
        assertEquals(tier1, tiers.get(0));
        assertEquals(tier3, tiers.get(1));

        testSetBonus.removeTier(tier1);
        assertEquals(1, tiers.size());
        assertEquals(tier3, tiers.get(0));

        testSetBonus.removeTier(tier3);
        assertEquals(0, tiers.size());
    }

    @Test
    void testAddTierCondition() {
        List<Integer> conditions = testSetBonus.getTierConditions();

        testSetBonus.addTierCondition(2);
        assertEquals(1, conditions.size());
        assertEquals(2, conditions.get(0));

        testSetBonus.addTierCondition(5);
        testSetBonus.addTierCondition(8);
        assertEquals(3, conditions.size());
        assertEquals(2, conditions.get(0));
        assertEquals(5, conditions.get(1));
        assertEquals(8, conditions.get(2));
    }

    @Test
    void testRemoveTierCondition() {
        List<Integer> conditions = testSetBonus.getTierConditions();

        testSetBonus.addTierCondition(8);
        testSetBonus.addTierCondition(10);
        testSetBonus.addTierCondition(1);
        assertEquals(3, conditions.size());

        testSetBonus.removeTierCondition(2);
        assertEquals(2, conditions.size());
        assertEquals(8, conditions.get(0));
        assertEquals(10, conditions.get(1));

        testSetBonus.removeTierCondition(1);
        assertEquals(1, conditions.size());
        assertEquals(8, conditions.get(0));

        testSetBonus.removeTierCondition(0);
        assertEquals(0, conditions.size());
    }

    @Test
    void testSetName() {
        testSetBonus.setName("Celestian Totality Set");
        assertEquals("Celestian Totality Set", testSetBonus.getName());
    }
}
