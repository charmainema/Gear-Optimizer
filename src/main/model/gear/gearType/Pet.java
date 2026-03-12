package main.model.gear.gearType;

import main.model.gear.GearItem;
import main.model.simulator.player.PlayerGear;

public class Pet extends GearItem {
    public Pet(String name) {
        super(name, "pet");
    }

    @Override
    public void setGear(PlayerGear gear) {
        gear.setPet(this);
    }

    @Override
    public void removeItem(PlayerGear gear) {
        gear.removePet();;
    }
    
}
