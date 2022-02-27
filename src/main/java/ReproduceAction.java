package main.java;

import main.java.organisms.Organism;

import java.util.ArrayList;
import java.util.Random;

public class ReproduceAction extends Action{
    public ReproduceAction(Organism o) {
        super(o);
    }

    @Override
    public Boolean canAct() {
        if(organism.isAnyDirectionAroundFree()&&organism.getPower()>organism.getPowerToReproduce()) return true;
        return false;
    }

    @Override
    public void makeAction() {
        if(canAct()){
            Organism child = organism.getChild();
            child.setPosition(chooseRandomDirection());
            organism.getWorld().getOrganisms().add(child);
            organism.setPower(organism.getPower()/2);
        }
        return;
    }

    Position chooseRandomDirection(){
        ArrayList<Position> positions = organism.whichDirectionsAroundAreFree();
        return positions.get(new Random().nextInt(positions.size()));
    }
}
