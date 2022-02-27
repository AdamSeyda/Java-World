package main.java.organisms;

import main.java.*;

public class Sheep extends Animal{
    public Sheep(Position pos, World w) {
        super(3, 3, pos, 10, 7, 'S', w);
        this.getFood().add(new Grass(null,null));
        this.getFood().add(new Dandelion(null,null));
        this.getFood().add(new Toadstool(null,null));
    }

    /*@Override
    public main.java.Action decideAction(){
        Action action;
        if(getPower()>getPowerToReproduce()){
            action= new ReproduceAction(this);
            return action;
        }
        if(isSpeciesNearby(new Grass(null,null))){
            action = new AttackAction(this);
            return action;
        }

        action = new MoveAction(this);
        return action;
    }*/

    @Override
    public int getOriginalPower(){
        return 3;
    }

    @Override
    public int getOriginalLiveLength(){
        return 10;
    }

    @Override
    public Organism getChild(){
        return new Sheep(this.getPosition(), this.getWorld());
    }

}
