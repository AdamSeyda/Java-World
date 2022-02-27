package main.java.organisms;

import main.java.*;

public class Wolf extends Animal{
    public Wolf(Position pos, World w) {
        super(8, 5, pos, 18, 16, 'W', w);
        this.getFood().add(new Sheep(null,null));
        this.getFood().add(new Toadstool(null,null));
    }

    /*@Override
    public main.java.Action decideAction(){
        Action action;
        if(getPower()>getPowerToReproduce()){
            action= new ReproduceAction(this);
            return action;
        }
        if(isSpeciesNearby(new Sheep(null,null))){
            action = new AttackAction(this);
            return action;
        }
        action = new MoveAction(this);
        return action;
    }*/

    @Override
    public int getOriginalPower(){
        return 8;
    }

    @Override
    public int getOriginalLiveLength(){
        return 20;
    }

    @Override
    public Organism getChild(){
        return new Wolf(this.getPosition(), this.getWorld());
    }
}
