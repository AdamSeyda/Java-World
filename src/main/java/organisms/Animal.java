package main.java.organisms;

import main.java.*;

import java.util.ArrayList;


public class Animal extends Organism{
    private Position prevPosition;
    private ArrayList<Organism> food;
    public Animal(int pow, int in, Position pos, int liveL, int pTR, char sign, World w) {
        super(pow, in, pos, liveL, pTR, sign, w);
        this.food = new ArrayList<>();
    }

    public Position getPrevPosition() {
        return prevPosition;
    }

    public void setPrevPosition(Position prevPosition) {
        this.prevPosition = prevPosition;
    }

    public ArrayList<Organism> getFood() {
        return food;
    }

    public void setFood(ArrayList<Organism> food) {
        this.food = food;
    }

    @Override
    public main.java.Action decideAction(){
        Action action;
        if(getPower()>getPowerToReproduce()){
            action= new ReproduceAction(this);
            return action;
        }
        for (Organism food: this.getFood()
             ) {
            if(isSpeciesNearby(food)){
                action = new AttackAction(this);
                return action;
            }
        }
        action = new MoveAction(this);
        return action;
    }
}
