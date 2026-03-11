package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.simulator.Spell;
import main.model.simulator.player.Player;

public class PlayerTest {
    private Player player;
    private Player enemy1;
    private Player enemy2;
    private Spell spell1;
    private Spell spell2;

    @BeforeEach
    void runBefore() {
        player = new Player(100, 4000, 1000, null);
        enemy1 = new Player(100, 3000, 1500, null);
        enemy2 = new Player(90, 2000, 1000, null);
        spell1 = new Spell("spell1", "life", 300, 100, 70, 50, 5, 10, 100, true);
        spell2 = new Spell("spell2", "death", 1000, 0, 0, 65, 7, 10, 100, false);
    }

    @Test
    void testSetLevel() {
        assertEquals(100, player.getLevel());
        player.setLevel(24);
        assertEquals(24, player.getLevel());
    }

    @Test
    void testAddSpell() {
        player.addSpell(spell1);
        assertEquals(1, player.getSpells().size());
        assertEquals(spell1, player.getSpells().get(0));

        player.addSpell(spell2);
        assertEquals(2, player.getSpells().size());
        assertEquals(spell1, player.getSpells().get(0));
        assertEquals(spell2, player.getSpells().get(1));
    }

    @Test
    void testAddPipFull() {
        player.addPips(Player.MAX_PIPS);
        assertEquals(Player.MAX_PIPS, player.getPips());
    }

    @Test
    void testAddPip() {
        assertEquals(0, player.getPips());
        player.addPips(1);
        assertEquals(1, player.getPips());
    }

    @Test
    void testAddPowerPip() {
        player.addPips(2);
        assertEquals(2, player.getPips());
    }

    @Test
    void testUpdateHandNoSpells() {
        assertEquals(0, player.getCurrentHand().size());
        player.updateHand(spell1);
        player.updateHand(spell2);
        assertEquals(0, player.getCurrentHand().size());
    }

    @Test
    void testUpdateHandNullSpell() {
        player.addSpell(spell1);
        player.addSpell(spell2);
        assertEquals(0, player.getCurrentHand().size());

        player.updateHand(null);
        assertEquals(7, player.getCurrentHand().size());

        for (Spell spell : player.getCurrentHand()) {
            assertTrue(player.getSpells().contains(spell));
        }
    }

    @Test
    void testUpdateHandEmptyHand() {
        player.addSpell(spell1);
        player.addSpell(spell2);
        assertTrue(player.getCurrentHand().isEmpty());

        player.updateHand(spell1);
        assertEquals(7, player.getCurrentHand().size());

        for (Spell spell : player.getCurrentHand()) {
            assertTrue(player.getSpells().contains(spell));
        }
    }

    @Test
    void testUpdateHandNullSpellFullHand() {
        player.addSpell(spell1);
        player.updateHand(spell1);
        assertEquals(7, player.getCurrentHand().size());

        for (Spell spell : player.getCurrentHand()) {
            assertTrue(spell == spell1);
        }

        player.removeSpell(spell1);
        player.addSpell(spell2);
        player.updateHand(null);
        assertEquals(7, player.getCurrentHand().size());

        for (Spell spell : player.getCurrentHand()) {
            assertTrue(spell == spell2);
        }
    }

    @Test
    void testUpdateHandNewSpell() {
        player.addSpell(spell1);
        player.addSpell(spell2);
        assertEquals(0, player.getCurrentHand().size());

        player.updateHand(spell1);
        assertEquals(7, player.getCurrentHand().size());

        for (Spell spell : player.getCurrentHand()) {
            assertTrue(player.getSpells().contains(spell));
        }

        Spell newSpell = new Spell("new spell", "ice", 800, 0, 0, 0, 5, 10, 90, false);
        player.removeSpell(spell1);
        player.removeSpell(spell2);
        player.addSpell(newSpell);
        player.updateHand(player.getCurrentHand().get(0));
        assertTrue(player.getCurrentHand().contains(newSpell));
    }

    @Test
    void testGetShieldFactorNoShields() {
        ArrayList<Integer> shields = new ArrayList<>();
        player.getShields().put("life", shields);
        assertEquals(0, player.getShieldFactor("life"));
    }

    @Test
    void testGetWardFactorNoWards() {
        ArrayList<Integer> wards = new ArrayList<>();
        player.getWards().put("life", wards);
        assertEquals(0, player.getWardFactor("life"));
    }

    @Test
    void testGetWardFactorNoSchool() {
        ArrayList<Integer> wards = new ArrayList<>();
        player.getWards().put("life", wards);
        assertEquals(0, player.getWardFactor("death"));
    }

    @Test
    void testCastSpellNoEnemies() {
        
    }

    @Test
    void testCastSpellOneEnemyNoAoe() {
        enemy1.getPlayerStats().updateStats("resist", "life", 10);
        player.getPlayerStats().updateStats("pierce", "life", 50);

        player.addEnemy(enemy1);
        player.addSpell(spell1);

        player.addPips(Player.MAX_PIPS);
        
        assertEquals(14, player.getPips());
        player.updateHand(null);
        player.castSpell(spell1);

        HashMap<String, ArrayList<Integer>> shields = player.getShields();
        HashMap<String, ArrayList<Integer>> wards = player.getWards();

        assertEquals(2580, enemy1.getPlayerStats().getStat("health", null));
        assertEquals(4100, player.getPlayerStats().getStat("health", null));

        assertTrue(shields.containsKey("life"));
        assertEquals(1, shields.get("life").size());
        assertEquals(70, shields.get("life").get(0));

        assertTrue(wards.containsKey("life"));
        assertEquals(1, wards.get("life").size());
        assertEquals(50, wards.get("life").get(0));

        assertEquals(Player.MAX_PIPS - spell1.getRequiredPips(), player.getPips());
    }

    @Test
    void testCastSpellAoe() {
        enemy1.getPlayerStats().updateStats("resist", "life", 10);
        enemy2.getPlayerStats().updateStats("resist", "life", 10);
        player.getPlayerStats().updateStats("pierce", "life", 50);

        player.addEnemy(enemy1);
        player.addEnemy(enemy2);
        player.addSpell(spell1);
        assertEquals(2, player.getEnemies().size());

        player.addPips(Player.MAX_PIPS);
        assertEquals(14, player.getPips());
        player.updateHand(null);
        player.castSpell(spell1);

        HashMap<String, ArrayList<Integer>> shields = player.getShields();
        HashMap<String, ArrayList<Integer>> wards = player.getWards();

        assertEquals(2580, enemy1.getPlayerStats().getStat("health", null));
        assertEquals(1580, enemy2.getPlayerStats().getStat("health", null));
        assertEquals(4100, player.getPlayerStats().getStat("health", null));

        assertTrue(shields.containsKey("life"));
        assertEquals(1, shields.get("life").size());
        assertEquals(70, shields.get("life").get(0));

        assertTrue(wards.containsKey("life"));
        assertEquals(1, wards.get("life").size());
        assertEquals(50, wards.get("life").get(0));

        assertEquals(Player.MAX_PIPS - spell1.getRequiredPips(), player.getPips());
    }

    @Test
    void testCastSpellCrit() {
        enemy1.getPlayerStats().updateStats("resist", "life", 10);
        player.getPlayerStats().updateStats("pierce", "life", 50);
        player.getPlayerStats().updateStats("critical", "life", 100);

        player.addEnemy(enemy1);
        player.addSpell(spell1);

        player.addPips(Player.MAX_PIPS);
        assertEquals(14, player.getPips());
        player.updateHand(null);
        player.castSpell(spell1);

        assertEquals(2160, enemy1.getPlayerStats().getStat("health", null));
        assertEquals(4100, player.getPlayerStats().getStat("health", null));
    }

    @Test
    void testCastSpellMultipleSpells() {
        // spell("spell2", "death", 1000, 0, 0, 65, 7, 10, 100, false);
        enemy1.getPlayerStats().updateStats("resist", "life", 10);
        player.getPlayerStats().updateStats("pierce", "life", 50);
        player.getPlayerStats().updateStats("critical", "life", 100);
        player.getPlayerStats().updateStats("damage", "death", 1);

        player.addEnemy(enemy1);
        player.addSpell(spell1);

        enemy1.addEnemy(player);
        Spell lifeShield = new Spell("life shield", "life", 0, 0, 75, 0, 0, 0, 0, false);
        enemy1.addSpell(lifeShield);

        player.addPips(Player.MAX_PIPS);
        assertEquals(14, player.getPips());

        player.updateHand(null);
        enemy1.updateHand(null);

        player.removeSpell(spell1);
        player.addSpell(spell2);
        player.castSpell(spell1);

        player.removeSpell(spell2);
        player.addSpell(spell1);
        player.castSpell(spell2);

        assertEquals(1150, enemy1.getPlayerStats().getStat("health", null));
        assertEquals(4100, player.getPlayerStats().getStat("health", null));

        enemy1.castSpell(lifeShield);
        player.castSpell(spell1);
        
        assertEquals(460, enemy1.getPlayerStats().getStat("health", null));
        assertEquals(4200, player.getPlayerStats().getStat("health", null));
    }
}
