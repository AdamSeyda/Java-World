package main.java;

import main.java.organisms.Animal;
import main.java.organisms.Organism;

import java.util.ArrayList;
import java.util.Random;

public class MoveAction extends Action {

    public MoveAction(Organism o) {
        super(o);
    }

    @Override
    public void makeAction() {
        if(canAct()){
            Position position = chooseRandomDirection();
            Animal c = (Animal) organism;
            c.setPrevPosition(c.getPosition());
            organism.setPosition(position);
        }
    }

    @Override
    public Boolean canAct() {
        if(organism.isAnyDirectionAroundFree()) return true;
        return false;
    }

    Position chooseRandomDirection(){
        ArrayList<Position> positions = organism.whichDirectionsAroundAreFree();
        return positions.get(new Random().nextInt(positions.size()));
    }

}
