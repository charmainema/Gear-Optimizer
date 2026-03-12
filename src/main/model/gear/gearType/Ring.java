package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Ring extends GearItem{
    public Ring(String name) {
        super(name, "ring");
    }

    @Override
    public void setGear(PlayerGear gear) {
        gear.setRing(this);
    }

    @Override
    public void removeItem(PlayerGear gear) {
        gear.removeRing();;
    }
    
}
