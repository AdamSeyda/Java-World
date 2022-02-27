package main.java.organisms;

import main.java.Position;
import main.java.World;

public class Grass extends Plant{
    public Grass(Position pos, World w) {
        super(1, 0, pos, 8, 3, 'G', w);
    }

    @Override
    public Organism getChild(){
        return new Grass(this.getPosition(),this.getWorld());
    }

    @Override
    public int getOriginalLiveLength(){
        return 6;
    }
}
