package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Amulet extends GearItem {
    public Amulet(String name) {
        super(name, "amulet");
    }

    @Override
    public void setGear(PlayerGear gear) {
        gear.setAmulet(this);    
    }

    @Override
    public void removeItem(PlayerGear gear) {
        gear.removeAmulet();;
    }
}
