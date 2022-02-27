package main.java;

import main.java.organisms.Organism;

public abstract class Action {
    Organism organism;

    public Action(Organism o){
        this.organism=o;
    }

    public Organism getOrganism() {
        return organism;
    }

    public void makeAction(){
        return;
    }

    public Boolean canAct(){
        return true;
    }


}
