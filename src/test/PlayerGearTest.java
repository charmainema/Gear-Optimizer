package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.GearItem;
import main.model.StatBoost;
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
        hat = new GearItem("abyssmal warrior's mask", "hat");
        robe = new GearItem("abyssmal warrior's suit", "robe");
        boots = new GearItem("abyssmal warrior's boots", "boots");
        wand = new GearItem("abyssmal eel whip", "wand");
        athame = new GearItem("hierarch's ancient one athame", "athame");
        amulet = new GearItem("4th age balance talisman", "amulet");
        ring = new GearItem("cool fairy kei ring", "ring");
        deck = new GearItem("alphoi aethyrium hand", "deck");
        pet = new GearItem("storm hound", "pet");
        mount = new GearItem("reindeer sleigh", "mount");

        boost1 = new StatBoost(100, "life", "damage");
        boost2 = new StatBoost(10, "fire", "resist");
        boost3 = new StatBoost(300, null, "health");

        initStats();
        player = new Player(100, 24, 24, null);
        gear = new PlayerGear(player);
    }

    @Test
    void testSettersGetters() {
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

        assertEquals(hat, gear.getHat());
        assertEquals(robe, gear.getRobe());
        assertEquals(boots, gear.getBoots());
        assertEquals(wand, gear.getWand());
        assertEquals(athame, gear.getAthame());
        assertEquals(amulet, gear.getAmulet());
        assertEquals(ring, gear.getRing());
        assertEquals(deck, gear.getDeck());
        assertEquals(pet, gear.getPet());
        assertEquals(mount, gear.getMount());

        assertEquals(400, player.getPlayerStats().getStat("damage", "life"));
        assertEquals(20, player.getPlayerStats().getStat("resist", "fire"));
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
