package main.java.organisms;

import main.java.NothingAction;
import main.java.Position;
import main.java.World;

import java.util.Random;

public class Dummy extends Plant{
    public Dummy(Position pos, World w) {
        super(0, 0, pos, 0, 10000, 'D', w);
    }

    @Override
    public main.java.Action decideAction() {
        return new NothingAction(this);
    }

    @Override
    public void getOlder(){
        this.setLiveLength(0);
    }
}
