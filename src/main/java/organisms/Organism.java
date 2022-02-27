package main.java.organisms;

import main.java.*;

import java.util.ArrayList;
import java.util.Random;

public class Organism{
    private int power;
    private int initiative;
    private Position position;
    private int liveLength;
    private int powerToReproduce;
    private char sign;
    private World world;

    public Organism(int pow, int in, Position pos, int liveL, int pTR, char sign, World w){
        this.power=pow;
        this.initiative=in;
        this.position=pos;
        this.liveLength=liveL;
        this.powerToReproduce=pTR;
        this.sign=sign;
        this.world=w;
    }

    public int getPower() {
        return power;
    }

    public int getInitiative() {
        return initiative;
    }

    public Position getPosition() {
        return position;
    }

    public int getLiveLength() {
        return liveLength;
    }

    public int getPowerToReproduce() {
        return powerToReproduce;
    }

    public char getSign() {
        return sign;
    }

    public World getWorld() {
        return world;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setLiveLength(int liveLength) {
        this.liveLength = liveLength;
    }

    public void setPowerToReproduce(int powerToReproduce) {
        this.powerToReproduce = powerToReproduce;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public main.java.Action decideAction(){
        if(getPower()>getPowerToReproduce()) return new ReproduceAction(this);
        return new NothingAction(this);
    }

    public boolean isAnyDirectionAroundFree(){
        ArrayList<Position> positions = whichDirectionsAroundAreFree();
        if(positions==null) return false;
        if(!positions.isEmpty()) return true;
        return false;
    }

    public ArrayList<Position> whichDirectionsAroundAreFree(){
        ArrayList<Position> positions = whichDirectionsBlockedByOrganisms();
        ArrayList<Position> positions1 = whichDirectionsNotBlockedByBorder();
        if(positions==null)return null;
        positions1.removeIf(positions::contains);
        return positions1;
    }

    public ArrayList<Position> whichDirectionsNotBlockedByOrganisms(){
        int x = this.getPosition().getWidth();
        int y = this.getPosition().getHeight();
        ArrayList<Position> positions = new ArrayList<>();
        Position northwest = new Position(x-1,y+1);
        Position north = new Position(x,y+1);
        Position northeast = new Position(x+1,y+1);
        Position west = new Position(x-1,y);
        Position east = new Position(x+1,y);
        Position southwest = new Position(x-1,y-1);
        Position south = new Position(x,y-1);
        Position southeast = new Position(x+1,y-1);
        positions.add(northwest);
        positions.add(north);
        positions.add(northeast);
        positions.add(west);
        positions.add(east);
        positions.add(southwest);
        positions.add(south);
        positions.add(southeast);
        positions.removeIf(p -> this.getWorld().getPositionsOfOrganisms().contains(p));
        return positions;
    }

    public ArrayList<Position> whichDirectionsBlockedByOrganisms(){
        if(this.getPosition()==null) return null;
        int x = this.getPosition().getWidth();
        int y = this.getPosition().getHeight();
        ArrayList<Position> positions = new ArrayList<>();
        Position northwest = new Position(x-1,y+1);
        Position north = new Position(x,y+1);
        Position northeast = new Position(x+1,y+1);
        Position west = new Position(x-1,y);
        Position east = new Position(x+1,y);
        Position southwest = new Position(x-1,y-1);
        Position south = new Position(x,y-1);
        Position southeast = new Position(x+1,y-1);
        positions.add(northwest);
        positions.add(north);
        positions.add(northeast);
        positions.add(west);
        positions.add(east);
        positions.add(southwest);
        positions.add(south);
        positions.add(southeast);
        positions.removeIf(p -> !(this.getWorld().getPositionsOfOrganisms().contains(p)));
        return positions;
    }

    public ArrayList<Position> whichDirectionsNotBlockedByBorder(){
        if(this.getPosition()==null)return null;
        int x = this.getPosition().getWidth();
        int y = this.getPosition().getHeight();
        ArrayList<Position> positions = new ArrayList<>();
        Position northwest = new Position(x-1,y+1);
        Position north = new Position(x,y+1);
        Position northeast = new Position(x+1,y+1);
        Position west = new Position(x-1,y);
        Position east = new Position(x+1,y);
        Position southwest = new Position(x-1,y-1);
        Position south = new Position(x,y-1);
        Position southeast = new Position(x+1,y-1);
        positions.add(northwest);
        positions.add(north);
        positions.add(northeast);
        positions.add(west);
        positions.add(east);
        positions.add(southwest);
        positions.add(south);
        positions.add(southeast);
        if(x==0){
            positions.remove(northwest);
            positions.remove(west);
            positions.remove(southwest);
        }
        if(x==this.getWorld().getWidth()-1){
            positions.remove(northeast);
            positions.remove(east);
            positions.remove(southeast);
        }
        if(y==0){
            if(x!=0) positions.remove(southwest);
            if(x!=this.getWorld().getWidth()-1) positions.remove(southeast);
            positions.remove(south);
        }
        if(y==this.getWorld().getHeight()-1){
            if(x!=0) positions.remove(northwest);
            if(x!=this.getWorld().getWidth()-1) positions.remove(northeast);
            positions.remove(north);
        }
        return positions;
    }

    public Position getPositionFromDirection(int direction){
        switch (direction){
            case 0:
                //northwest
                return new Position(this.getPosition().getWidth()-1,this.getPosition().getHeight()+1);
            case 1:
                //north
                return new Position(this.getPosition().getWidth(),this.getPosition().getHeight()+1);
            case 2:
                //northeast
                return new Position(this.getPosition().getWidth()+1,this.getPosition().getHeight()+1);
            case 3:
                //west
                return new Position(this.getPosition().getWidth()-1,this.getPosition().getHeight());
            case 5:
                //east
                return new Position(this.getPosition().getWidth()+1,this.getPosition().getHeight());
            case 6:
                //southwest
                return new Position(this.getPosition().getWidth()-1,this.getPosition().getHeight()-1);
            case 7:
                //south
                return new Position(this.getPosition().getWidth(),this.getPosition().getHeight()-1);
            case 8:
                //southeast
                return new Position(this.getPosition().getWidth()+1,this.getPosition().getHeight()-1);
            default:
                return null;
        }
    }

    public Boolean isSpeciesNearby(Organism o){
        ArrayList<Position> positions = whichDirectionsBlockedByOrganisms();
        if(positions==null) return false;
        Organism potentialInstanceOfSpecies;
        for (Position p:positions
             ) {
            potentialInstanceOfSpecies = this.getWorld().getOrganismFromPosition(p);
            if(potentialInstanceOfSpecies!=null){
                if(potentialInstanceOfSpecies.getClass().equals(o.getClass())) return true;
            }
        }
        return false;
    }

    public void getOlder(){
        this.power = (this.power+1);
        if(this.getWorld().countInstancesOfSpecies(this.getClass())<6) return;
        this.liveLength = (this.liveLength-1);
    }

    public int getOriginalPower(){
        return 0;
    }

    public int getOriginalLiveLength(){
        return 0;
    }

    public Organism getChild(){
        return new Organism(this.getOriginalPower(), this.getInitiative(), this.getPosition(),
                this.getOriginalLiveLength(),this.getPowerToReproduce(), this.getSign(), this.getWorld());
    }

    public void consequences(Organism o){
        return;
    }

}
