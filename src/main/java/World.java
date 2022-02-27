package main.java;

import main.java.organisms.*;

import java.util.ArrayList;
import java.util.Random;

public class World {
    private int width;
    private int height;
    private ArrayList<Organism> organisms;
    private int turn;
    private ArrayList<Action> actionOrder;
    private ArrayList<Class> startingSpecies;
    private Boolean remembers;

    public World(int x, int y, ArrayList<Organism> o){
        this.height=y;
        this.width=x;
        this.organisms=o;
        this.turn=0;
        this.actionOrder=new ArrayList<>();
        this.remembers=false;
        this.startingSpecies=new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Organism> getOrganisms() {
        return organisms;
    }

    public Boolean getRemembers() {
        return remembers;
    }

    public int getTurn() {
        return turn;
    }

    public ArrayList<Class> getStartingSpecies() {
        return startingSpecies;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setOrganisms(ArrayList<Organism> organisms) {
        this.organisms = organisms;
    }

    public void setStartingSpecies(ArrayList<Class> startingSpecies) {
        this.startingSpecies = startingSpecies;
    }

    public void setRemembers(Boolean remembers) {
        this.remembers = remembers;
    }

    public ArrayList<Action> getActionOrder() {
        return actionOrder;
    }

    public void setActionOrder(ArrayList<Action> actionOrder) {
        this.actionOrder = actionOrder;
    }

    public ArrayList<Position> getPositionsOfOrganisms(){
        ArrayList<Position> positions = new ArrayList<>();
        for (Organism o:organisms) {
            if(o!=null&&o.getPosition()!=null)
            positions.add(o.getPosition());
        }
        return positions;
    }

    public Organism getOrganismFromPosition(Position position){
        for (Organism o:organisms
             ) {
            if(position.equals(o.getPosition())) return o;
        }
        return null;
    }

    public void doTurn(){
        if(!remembers){
            setRemembers(true);
            setStartingSpecies(getClassesFromStartOfWorld());
        }
        this.setTurn(getTurn()+1);
        Class cursed =decideWhoToCurse();
        if(cursed!=null) curseOverabundanceOfOrganisms(cursed);
        killOldOrganisms();
        organismsSortByInitiative();
        getActions();
        doActions();
        ageOrganisms();
        rebirth();
        rebirth();
        rebirth();
        rebirth();
        rebirth();
    }

    public void rebirth(){
        Organism o = isItTimeForRebirth();
        Position p;
        if(o!=null){
            p=getRandomUnoccupiedPosition(0);
            o.setPosition(p);
            this.getOrganisms().add(o.getChild());
        }
    }

    public Organism isItTimeForRebirth(){
        Boolean exists=false;
        Organism temp = null;
        for (Class c:getStartingSpecies()) {
            for (Organism o:getOrganisms()
            ) {
                if(o.getClass().equals(c)){
                    exists=true;
                }
                temp = o;
            }
            if(!exists) return temp;
            exists=false;
        }
        return null;
    }

    public void doNTurns(int n){
        while(n>0){
            doTurn();
            n--;
        }
    }

    public ArrayList<Class> getClassesFromStartOfWorld(){
        ArrayList<Class> species = new ArrayList<>();
        for (Organism o:this.getOrganisms()
             ) {
            if(!species.contains(o.getClass())) species.add(o.getClass());
        }
        return species;
    }

    public void killOldOrganisms(){
        organisms.removeIf(o -> o.getLiveLength() <= 0);
    }

    public void getActions(){
        for (Organism o:organisms) {
            this.actionOrder.add(o.decideAction());
        }
    }

    public Class decideWhoToCurse(){
        int mostInstances = 0;
        int temp;
        int tooMuch = this.getHeight()*this.getWidth()/6;
        tooMuch+=4;
        temp = countInstancesOfSpecies(Grass.class);
        if(temp>mostInstances) mostInstances=temp;
        if(mostInstances>tooMuch) return Grass.class;
        temp = countInstancesOfSpecies(Dandelion.class);
        if(temp>mostInstances) mostInstances=temp;
        if(mostInstances>tooMuch) return Dandelion.class;
        temp = countInstancesOfSpecies(Toadstool.class);
        if(temp>mostInstances) mostInstances=temp;
        if(mostInstances>tooMuch) return Toadstool.class;
        temp = countInstancesOfSpecies(Sheep.class);
        if(temp>mostInstances) mostInstances=temp;
        if(mostInstances>tooMuch) return Sheep.class;
        temp = countInstancesOfSpecies(Wolf.class);
        if(temp>mostInstances) mostInstances=temp;
        if(mostInstances>tooMuch) return Wolf.class;
        temp = countInstancesOfSpecies(Alien.class);
        if(temp>mostInstances) mostInstances=temp;
        if(mostInstances>tooMuch) return Alien.class;
        temp = countInstancesOfSpecies(Dummy.class);
        if(temp>mostInstances) mostInstances=temp;
        if(mostInstances>tooMuch) return Dummy.class;
        return null;
    }

    public int countInstancesOfSpecies(Class species){
        int count = 0;
        for (Organism o:organisms
             ) {
            if(o.getClass().equals(species)) count++;
        }
        return count;
    }

    public void curseOverabundanceOfOrganisms(Class cursed){
        int toCurse = countInstancesOfSpecies(cursed)/2+countInstancesOfSpecies(cursed)/4;
        for (Organism o:organisms
             ) {
            if(o.getClass().equals(cursed)){
                o.setLiveLength(0);
                toCurse--;
                if(toCurse<=0) return;
            }
        }
    }

    public void organismsSortByInitiative(){
        this.organisms.sort(new SortByInitiative());
    }

    public void doActions(){
        for (Action a:this.actionOrder) {

            if(a.canAct()) a.makeAction();
        }
        actionOrder=new ArrayList<>();
    }

    public void ageOrganisms(){
        for (Organism o:organisms
             ) {
            o.getOlder();
        }
    }

    public Position getRandomUnoccupiedPosition(int tries){
        ArrayList<Position> positions = getPositionsOfOrganisms();
        int x = new Random().nextInt(this.getWidth());
        int y = new Random().nextInt(this.getHeight());
        Position p = new Position(x,y);
        if(positions.contains(p)) {
            if (tries>10) return null;
            return getRandomUnoccupiedPosition(tries+1);
        }
        return p;
    }
}
