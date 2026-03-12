package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Robe extends GearItem {
    public Robe(String name) {
        super(name, "robe");
    }


    @Override
    public void setGear(PlayerGear gear) {
        gear.setRobe(this);
    }


    @Override
    public void removeItem(PlayerGear gear) {
        gear.removeRobe();;
    }
    
}
