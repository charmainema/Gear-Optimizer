package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Mount extends GearItem {
    public Mount(String name) {
        super(name, "mount");
    }

    @Override
    public void setGear(PlayerGear gear) {
        gear.setMount(this);
    }

    @Override
    public void removeItem(PlayerGear gear) {
        gear.removeMount();;
    }
    
}
