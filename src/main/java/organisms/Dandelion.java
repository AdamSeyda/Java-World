package main.java.organisms;

import main.java.Position;
import main.java.World;

public class Dandelion extends Plant{
    public Dandelion(Position pos, World w) {
        super(1, 0, pos, 6, 2, 'D', w);
    }

    @Override
    public int getOriginalLiveLength(){
        return 6;
    }

    @Override
    public Organism getChild(){
        return new Dandelion(this.getPosition(), this.getWorld());
    }
}
