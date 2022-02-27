package main.java;

import main.java.organisms.Organism;

import java.util.Comparator;

class SortByInitiative implements Comparator<Organism> {
    @Override
    public int compare(Organism a, Organism b){return a.getInitiative()-b.getInitiative();}
}
