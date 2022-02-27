package main.java.organisms;

import main.java.Position;
import main.java.World;

public class Toadstool extends Plant{
    public Toadstool(Position pos, World w) {
        super(0, 0, pos, 12, 4, 'T', w);
    }

    @Override
    public int getOriginalLiveLength(){
        return 12;
    }

    @Override
    public Organism getChild(){
        return new Toadstool(this.getPosition(), this.getWorld());
    }

    @Override
    public void consequences(Organism o){
        o.setLiveLength(0);
    }
}
