package main.java.organisms;

import main.java.Action;
import main.java.NothingAction;
import main.java.Position;
import main.java.World;

import java.util.ArrayList;
import java.util.Random;

public class Alien extends Animal{
    ArrayList<Position> positionsOfNearbyOrganisms;
    ArrayList<Organism> stoppedOrganisms;
    ArrayList<PositionAndOrganism> positionsOfStoppedOrganisms;
    public Alien(Position pos, World w) {
        super(1, 10000, pos, 10000, 10000, 'A', w);
        this.positionsOfNearbyOrganisms = new ArrayList<>();
        this.stoppedOrganisms = new ArrayList<>();
        this.positionsOfStoppedOrganisms = new ArrayList<>();
    }

    @Override
    public main.java.Action decideAction() {
        if(this.getInitiative()==0){
            return new NothingAction(this);
        }
        int x = new Random().nextInt(10);;
        if(this.getPosition()==null){
            if(x==5) return new AppearAction(this);
        } else {
            if(x==1){
                return new DisappearAction(this);
            } else {
                int temp = getActionAmountFromOrganisms()+getAmountOfReturnStoppedTimeActions();
                this.getWorld().getActionOrder().add(temp,new ReturnTimeAction(this));
                return new StopTimeAction(this);
            }
        }
        return new NothingAction(this);
    }

    @Override
    public void getOlder(){
    }

    public int getActionAmountFromOrganisms(){
        int i = 0;
        for (Organism ignored :this.getWorld().getOrganisms()
             ) { i++; }
        i=i-1;
        return i;
    }

    public int getAmountOfReturnStoppedTimeActions(){
        int i = 0;
        for (Action a:this.getWorld().getActionOrder()
             ) {
            if(a.getClass().equals(ReturnTimeAction.class))
                i++;
        }
        return i;
    }

    public Position chooseRandomValidPosition(){
        World world = this.getWorld();
        int x = world.getWidth();
        int y = world.getHeight();
        Position p = new Position(new Random().nextInt(x),new Random().nextInt(y));
        for (Organism o:world.getOrganisms()
        ) {
            if(o.getPosition()!=null) if(o.getPosition().equals(p)) p = new Position(new Random().nextInt(x),new Random().nextInt(y));
        }
        for (Organism o:world.getOrganisms()
        ) {
            if(o.getPosition()!=null) if(o.getPosition().equals(p)) p = new Position(new Random().nextInt(x),new Random().nextInt(y));
        }
        for (Organism o:world.getOrganisms()
        ) {
            if(o.getPosition()!=null) if(o.getPosition().equals(p)) p = new Position(new Random().nextInt(x),new Random().nextInt(y));
        }
        return p;
    }

    public ArrayList<Organism> getNearbyOrganisms(){
        ArrayList<Position> positions;
        ArrayList<Organism> nearbyOrganisms = new ArrayList<>();
        ArrayList<Position> allOrganismsPositions = this.getWorld().getPositionsOfOrganisms();
        positions = removeOutOfBoundPosition(getNearbyPositions());
        for (Position p:positions
             ) {
            if(allOrganismsPositions.contains(p)) nearbyOrganisms.add(this.getWorld().getOrganismFromPosition(p));
        }
        return nearbyOrganisms;
    }

    public ArrayList<Position> getNearbyPositions(){
        ArrayList<Position> positions = new ArrayList<>();
        if(this.getPosition()==null) return positions;
        int alienX = this.getPosition().getWidth();
        int alienY = this.getPosition().getHeight();
        for(int y = -2; y<3; y++){
            for(int x = -2; x<3; x++){
                positions.add(new Position(alienX-x,alienY-y));
            }
        }
        return positions;
    }

    public ArrayList<Position> removeOutOfBoundPosition(ArrayList<Position> positions){
        ArrayList<Position> positions1 = new ArrayList<>(positions);
        for (Position p:positions
             ) {
            if(p.getWidth()<0||p.getHeight()<0||
                    p.getWidth()>=this.getWorld().getWidth()||
                    p.getHeight()>=this.getWorld().getHeight()){
                positions1.remove(p);
            }
        }
        return positions1;
    }

    public class StopTimeAction extends Action{

        public StopTimeAction(Organism o) {
            super(o);
        }

        @Override
        public void makeAction(){
            stoppedOrganisms = getNearbyOrganisms();
            positionsOfStoppedOrganisms = new ArrayList<>();
            for (Organism o:stoppedOrganisms
                 ) {
                positionsOfNearbyOrganisms.add(o.getPosition());
                positionsOfStoppedOrganisms.add(new PositionAndOrganism(o,o.getPosition()));
                o.setPosition(null);
            }
            for (Position p:positionsOfNearbyOrganisms
                 ) {
                getWorld().getOrganisms().add(new Dummy(p, getWorld()));
            }

        }

        @Override
        public Boolean canAct(){
            return getPosition() != null;
        }
    }

    public class ReturnTimeAction extends Action{

        public ReturnTimeAction(Organism o) {
            super(o);
        }

        @Override
        public void makeAction(){
            stoppedOrganisms = getNearbyOrganisms();
            if(positionsOfStoppedOrganisms==null||positionsOfNearbyOrganisms.isEmpty()) return;
            ArrayList<PositionAndOrganism> temp = new ArrayList<>(positionsOfStoppedOrganisms);
            Organism dummy;
            for (Position p:positionsOfNearbyOrganisms
            ) {
                dummy = getWorld().getOrganismFromPosition(p);
                if(dummy!=null)if(dummy.getClass().equals(Dummy.class)) getWorld().getOrganisms().remove(dummy);
            }
            for (Organism o:stoppedOrganisms
            ) {
                for (PositionAndOrganism poa:positionsOfStoppedOrganisms
                     ) {
                    if(poa.getOrganism().equals(o)) {
                        o.setPosition(poa.position);
                    }
                }
            }
            positionsOfStoppedOrganisms=temp;
        }

    }

    public class DisappearAction extends Action{

        public DisappearAction(Organism o) {
            super(o);
        }

        @Override
        public void makeAction(){
            setPrevPosition(getPosition());
            setPosition(null);
            return;
        }

        @Override
        public Boolean canAct(){
            return getPosition() != null;
        }
    }

    public class AppearAction extends Action{

        public AppearAction(Organism o) {
            super(o);
        }

        public void makeAction(){
            Position p = chooseRandomValidPosition();
            if(p!=null){
                setPosition(p);
                setPrevPosition(null);
            }
            return;
        }

        public Boolean canAct(){
            return getPosition() == null;
        }
    }

    private class PositionAndOrganism{
        private Organism organism;
        private Position position;

        public PositionAndOrganism(Organism o, Position p){
            this.organism=o;
            this.position=p;
        }

        public Organism getOrganism() {
            return organism;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public void setOrganism(Organism organism) {
            this.organism = organism;
        }
    }
}
