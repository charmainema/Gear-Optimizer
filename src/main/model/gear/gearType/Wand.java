package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Wand extends GearItem {
    public Wand(String name) {
        super(name, "wand");
    }

    @Override
    public void setGear(PlayerGear gear) {
        gear.setWand(this);
    }

    @Override
    public void removeItem(PlayerGear gear) {
        gear.removeWand();;
    }

}
