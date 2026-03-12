package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Hat extends GearItem {
    public Hat(String name) {
        super(name, "hat");
    }

    @Override
    public void setGear(PlayerGear gear) {
        gear.setHat(this);
    }

    @Override
    public void removeItem(PlayerGear gear) {
        gear.removeHat();;
    }
}
