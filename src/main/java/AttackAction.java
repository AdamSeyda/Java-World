package main.java;

import main.java.organisms.Animal;
import main.java.organisms.Organism;

import java.util.ArrayList;
import java.util.Random;

public class AttackAction extends Action{
    public AttackAction(Organism o) {
        super(o);
    }

    @Override
    public void makeAction() {
        if(canAct()){
            Organism target = chooseTarget();
            if(target!=null){
                target.consequences(organism);
                organism.setPosition(target.getPosition());
                organism.getWorld().getOrganisms().remove(target);
            }
        }
        return;
    }

    @Override
    public Boolean canAct() {
        if(anyValidTargets()) return true;
        return false;
    }

    Boolean anyValidTargets(){
        ArrayList<Position> positions = organism.whichDirectionsBlockedByOrganisms();
        if(positions==null)return false;
        for (Position p:positions
             ) {
            Organism target = organism.getWorld().getOrganismFromPosition(p);
            if(target!=null){
                if(isTargetWeakEnoughToAttack(target)){
                    if(!isTargetEndangered(target)) return true;
                }
            }
        }
        return false;
    }

    Boolean isTargetEndangered(Organism target){
        ArrayList<Organism> organisms = organism.getWorld().getOrganisms();
        int count = 0;
        for (Organism o:organisms
             ) {
            if(o.getClass().equals(target.getClass())) count++;
        }
        if(count<(organism.getWorld().getWidth()*organism.getWorld().getHeight())/20+2) return true;
        return false;
    }

    Boolean isTargetWeakEnoughToAttack(Organism target){
        if(target.getPower()<organism.getPower()) return true;
        return false;
    }

    Organism chooseTarget(){
        ArrayList<Position> positions = whichTargetsAreValid();
        Position p = null;
        if(!positions.isEmpty()) p = positions.get(new Random().nextInt(positions.size()));
        if(p==null) return null;
        return organism.getWorld().getOrganismFromPosition(p);
    }

    ArrayList<Position> whichTargetsAreValid(){
        Animal o = (Animal) organism;
        ArrayList<Position> positions = organism.whichDirectionsBlockedByOrganisms();
        ArrayList<Position> positions1 = new ArrayList<>(positions);
        Organism organismAtPosition;
        Boolean preferredFood;
        for (Position p:positions) {
            preferredFood = false;
            organismAtPosition = organism.getWorld().getOrganismFromPosition(p);
            if(!isTargetWeakEnoughToAttack(organismAtPosition)) positions1.remove(p);
            for (Organism food:o.getFood()
                 ) {
                if (organismAtPosition.getClass().equals( food.getClass())) preferredFood=true;
            }
            if (!preferredFood) positions1.remove(p);
        }
        return positions1;
    }

    Organism getTargetFromDirection(int i){
        return organism.getWorld().getOrganismFromPosition(organism.getPositionFromDirection(i));
    }
}
