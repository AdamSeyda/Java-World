package main.java;

import main.java.organisms.Organism;
import java.util.ArrayList;

public class Map {
    private char currentMap[][];

    public Map(char[][] currentMap) {
        this.currentMap = currentMap;
    }

    public char[][] getCurrentMap() {
        return currentMap;
    }

    public void updateMap(World world){
        int widthBorder = world.getWidth();
        int heightBorder = world.getHeight();
        ArrayList<Position> positions;
        positions = world.getPositionsOfOrganisms();
        Organism o = null;
        for(int y=0;y<heightBorder;y++){
            for(int x = 0;x<widthBorder;x++){
                for (Position p:positions) {
                    if(p.equals(new Position(x,y))){
                        o=world.getOrganismFromPosition(new Position(x,y));
                        currentMap[x][y]=o.getSign();
                        break;
                    }
                }
                if(o!=null){
                    if (!o.getPosition().equals(new Position(x,y))) currentMap[x][y]=',';
                } else {
                    currentMap[x][y]=',';
                }
            }
        }
        return;
    }

    public void printMap(int width, int height){
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                System.out.print(currentMap[x][y]);
            }
            System.out.println();
        }
    }
}
