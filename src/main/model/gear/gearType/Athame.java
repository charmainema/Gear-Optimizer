package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Athame extends GearItem {
    public Athame(String name) {
        super(name, "athame");
    }

    @Override
    public void setGear(PlayerGear gear) {
        gear.setAthame(this);
    }

    @Override
    public void removeItem(PlayerGear gear) {
        gear.removeAthame();;
    }
    
}
