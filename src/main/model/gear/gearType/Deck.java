package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Deck extends GearItem {
    public Deck(String name) {
        super(name, "deck");
    }

    @Override
    public void setGear(PlayerGear gear) {
        gear.setDeck(this);
    }

    @Override
    public void removeItem(PlayerGear gear) {
        gear.removeDeck();;
    }
    
}
