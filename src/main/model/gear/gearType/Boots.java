package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Boots extends GearItem {
    public Boots(String name) {
        super(name, "boots");
    }

    @Override
    public void setGear(PlayerGear gear) {
        gear.setBoots(this);
    }

    @Override
    public void removeItem(PlayerGear gear) {
        gear.removeBoots();;
    }
    
}
