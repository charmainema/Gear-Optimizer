package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.StatBoost;
import main.model.gear.GearItem;
import main.model.gear.gearType.Amulet;
import main.model.gear.gearType.Athame;
import main.model.gear.gearType.Boots;
import main.model.gear.gearType.Deck;
import main.model.gear.gearType.Hat;
import main.model.gear.gearType.Mount;
import main.model.gear.gearType.Pet;
import main.model.gear.gearType.Ring;
import main.model.gear.gearType.Robe;
import main.model.gear.gearType.Wand;
import main.model.simulator.player.Player;
import main.model.simulator.player.PlayerGear;

public class PlayerGearTest {
    private PlayerGear gear;
    private Player player;
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
    private StatBoost boost1;
    private StatBoost boost2;
    private StatBoost boost3;

    @BeforeEach
    void runBefore() {
        hat = new Hat("abyssmal warrior's mask");
        robe = new Robe("abyssmal warrior's suit");
        boots = new Boots("abyssmal warrior's boots");
        wand = new Wand("abyssmal eel whip");
        athame = new Athame("hierarch's ancient one athame");
        amulet = new Amulet("4th age balance talisman");
        ring = new Ring("cool fairy kei ring");
        deck = new Deck("alphoi aethyrium hand");
        pet = new Pet("storm hound");
        mount = new Mount("reindeer sleigh");

        boost1 = new StatBoost(100, "life", "damage");
        boost2 = new StatBoost(10, "fire", "resist");
        boost3 = new StatBoost(300, null, "health");

        initStats();
        player = new Player(100, 24, 24);
        gear = new PlayerGear(player);
    }

    @Test
    void testSettersGetters() {
        GearItem hat2 = new Hat("hat 2");
        hat2.addStatBoost(boost2);

        gear.setHat(hat);
        gear.setRobe(robe);
        gear.setBoots(boots);
        gear.setWand(wand);
        gear.setAthame(athame);
        gear.setAmulet(amulet);
        gear.setRing(ring);
        gear.setDeck(deck);
        gear.setPet(pet);
        gear.setMount(mount);
        gear.setHat(hat2);

        assertEquals(hat2, gear.getHat());
        assertEquals(robe, gear.getRobe());
        assertEquals(boots, gear.getBoots());
        assertEquals(wand, gear.getWand());
        assertEquals(athame, gear.getAthame());
        assertEquals(amulet, gear.getAmulet());
        assertEquals(ring, gear.getRing());
        assertEquals(deck, gear.getDeck());
        assertEquals(pet, gear.getPet());
        assertEquals(mount, gear.getMount());

        assertEquals(300, player.getPlayerStats().getStat("damage", "life"));
        assertEquals(30, player.getPlayerStats().getStat("resist", "fire"));
        assertEquals(1224, player.getPlayerStats().getStat("health", null));
    }

    private void initStats() {
        hat.addStatBoost(boost1);
        robe.addStatBoost(boost1);
        boots.addStatBoost(boost1);
        wand.addStatBoost(boost1);
        athame.addStatBoost(boost2);
        amulet.addStatBoost(boost2);
        ring.addStatBoost(boost3);
        deck.addStatBoost(boost3);
        pet.addStatBoost(boost3);
        mount.addStatBoost(boost3);
    }
}
