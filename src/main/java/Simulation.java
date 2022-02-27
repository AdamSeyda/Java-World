package main.java;

import main.java.organisms.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {


        //upewnij sie ze nie mozna organizmów poza świat wrzucić


        ArrayList<World> worlds = new ArrayList<>();
        World presetWorld = new World(40,15,new ArrayList<>());
        ArrayList<Organism> presetOrganisms = new ArrayList<>();
        presetOrganisms.add(new Grass(new Position(1,1),presetWorld));
        presetOrganisms.add(new Grass(new Position(39,13),presetWorld));
        presetOrganisms.add(new Grass(new Position(5,5),presetWorld));
        presetOrganisms.add(new Grass(new Position(15,10),presetWorld));
        presetOrganisms.add(new Grass(new Position(30,1),presetWorld));
        presetOrganisms.add(new Sheep(new Position(3,3),presetWorld));
        presetOrganisms.add(new Sheep(new Position(10,10),presetWorld));
        presetOrganisms.add(new Toadstool(new Position(0,14),presetWorld));
        presetOrganisms.add(new Wolf(new Position(20,14),presetWorld));
        presetOrganisms.add(new Toadstool(new Position(39,0),presetWorld));
        presetOrganisms.add(new Dandelion(new Position(20,0),presetWorld));
        //presetOrganisms.add(new Alien(null,presetWorld));
        presetWorld.setOrganisms(presetOrganisms);
        worlds.add(presetWorld);
        simulation(0, worlds);
        System.out.println("Dziękujemy za uwagę!");
    }

    public static void simulation(int skip, ArrayList<World> worlds) throws InterruptedException {
        int choice;
        if(skip!=1){
            printIntro();
        }
        printMainMenu();
        choice = makeMainMenuChoice(worlds);
        if(choice==-1) return;
        simulation(1,worlds);
    }

    public static void printIntro() throws InterruptedException {
        System.out.println("Witaj w symulatorze świata 2D!");
        NANOSECONDS.sleep(1000000000);
        System.out.println("Możesz tutaj tworzyć swoje światy,");
        NANOSECONDS.sleep(1000000000);
        System.out.println("I patrzeć jak się rozwijają!");
        NANOSECONDS.sleep(1000000000);
    }

    public static void printMainMenu() {
        System.out.println("Podaj numer by wybrać opcję:");
        System.out.println("1 - stwórz nowy świat");
        System.out.println("2 - wybierz świat do obejrzenia");
        System.out.println("3 - wyjdź z symulacji");
    }

    public static int takeUserChoice(){
        Scanner myObj = new Scanner(System.in);
        int userChoice = -1;
        try {
            userChoice = myObj.nextInt();
        }
        catch(Exception e) {
            System.out.println("Niepoprawna opcja!");
        }
        return userChoice;
    }

    public static void createNewWorld(ArrayList<World> worlds){
        int x = printTextAndAskToMakeChoice("Podaj szerokość świata:");
        int y = printTextAndAskToMakeChoice("Podaj wysokość świata:");
        World createdWorld = new World(x,y, new ArrayList<>());
        ArrayList<Organism> organisms = addChosenOrganisms(createdWorld);
        createdWorld.setOrganisms(organisms);
        worlds.add(createdWorld);
    }

    public static ArrayList<Organism> addChosenOrganisms(World world){
        ArrayList<Organism> organisms = new ArrayList<>();
        Organism o;
        int choice = printTextAndAskToMakeChoice("Podaj typ organizmu który chcesz dodać:\n" +
                "0 - żaden\n1 - trawa\n2 - mlecz\n3 - muchomor\n4 - owca\n5 - wilk\n6 - kosmita");
        int quantity = printTextAndAskToMakeChoice("Podaj ile razy chcesz dodać ten organizm:");
        while(quantity>0){
            o = decideOrganismPosition(choice, organisms, world);
            if(o==null) return organisms;
            if(!o.getClass().equals(Alien.class)&&o.getPosition()==null) return organisms;
            organisms.add(o);
            quantity--;
        }
        choice = printTextAndAskToMakeChoice("Czy chcesz dodać więcej organizmów?\n1 - tak\n2 - nie");
        if(choice==1) organisms.addAll(addChosenOrganisms(world));
        return organisms;
    }

    public static int printTextAndAskToMakeChoice(String text){
        System.out.println(text);
        return takeUserChoice();
    }

    public static Organism decideOrganismPosition(int choice, ArrayList<Organism> organisms, World world){
        if(choice==6) return new Alien(null,world);
        int x = printTextAndAskToMakeChoice("Podaj współrzędną x:");
        int y = printTextAndAskToMakeChoice("Podaj współrzędną y:");
        Position p = new Position(x,y);
        int positionValidity = checkIfPositionValid(p, organisms);

        switch (positionValidity){
            case 1:
                decideOrganismPosition(choice,organisms,world);
            case 2:
                p = chooseRandomValidPosition(p,organisms, world);
            case 3:
                return null;
        }

        return switch (choice) {
            case 1 -> new Grass(p, world);
            case 2 -> new Dandelion(p, world);
            case 3 -> new Toadstool(p, world);
            case 4 -> new Sheep(p, world);
            case 5 -> new Wolf(p, world);
            default -> null;
        };
    }

    public static int checkIfPositionValid(Position p, ArrayList<Organism> organisms){
        for (Organism o:organisms) {
            if(o.getPosition().equals(p)){
                System.out.println("Już istnieje organizm na danej pozycji.");
                int userInput = printTextAndAskToMakeChoice("1 - chcę spróbować podać inną pozycję\n" +
                        "2 - chcę by komputer za mnie wybrał pozycję\n" +
                        "3 - rezygnuję z dodania organizmu");
                switch (userInput){
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    default:
                        return 3;
                }
            }
        }
        return 0;
    }

    public static Position chooseRandomValidPosition(Position p, ArrayList<Organism> organisms, World world){
        int x = world.getWidth();
        int y = world.getHeight();
        p = new Position(new Random().nextInt(x),new Random().nextInt(y));
        for (Organism o:organisms
             ) {
            if(o.getPosition().equals(p)) p = new Position(new Random().nextInt(x),new Random().nextInt(y));
        }
        for (Organism o:organisms
             ) {
            if(o.getPosition().equals(p)) p = new Position(new Random().nextInt(x),new Random().nextInt(y));
        }
        for (Organism o:organisms
             ) {
            if(o.getPosition().equals(p)) p = new Position(new Random().nextInt(x),new Random().nextInt(y));
        }
        return p;
    }

    public static int makeMainMenuChoice(ArrayList<World> worlds){
        int userChoice = takeUserChoice();
        switch (userChoice){
            case 1:
                createNewWorld(worlds);
                return 1;
            case 2:
                chooseWorldToWatch(worlds);
                return 2;
            case 3:
                return -1;
            default:
                System.out.println("Niepoprawny wybór!");
                printMainMenu();
                return makeMainMenuChoice(worlds);

        }
    }

    public static int printExistingWorlds(ArrayList<World> worlds){
        int i=0;
        for (World w:worlds
             ) {
            System.out.println("World " + i);
            i++;
        }
        return i;
    }

    public static void chooseWorldToWatch(ArrayList<World> worlds){
        System.out.println("Który świat chcesz zobaczyć?");
        int i = printExistingWorlds(worlds);
        System.out.println(i + " - żaden, powróć do menu");
        int userChoice = takeUserChoice();
        if(userChoice==i) return;
        else watchChosenWorld(worlds.get(userChoice));
    }

    public static void watchChosenWorld(World world){
        int x = world.getWidth();
        int y = world.getHeight();
        int userChoice;
        int temp;
        Map currentMap = new Map(new char[x][y]);
        currentMap.updateMap(world);
        currentMap.printMap(x,y);
        userChoice = watchedWorldChoice();
        System.out.println(userChoice + " - wybrana opcja");
        switch (userChoice){
            case 1:
                world.doTurn();
                break;
            case 2:
                temp = printTextAndAskToMakeChoice("Wybierz ile chcesz czekać:");
                world.doNTurns(temp);
                break;
            case 3:
                return;
        }
        watchChosenWorld(world);
    }

    public static int watchedWorldChoice(){
        System.out.println("Wybierz komendę:");
        System.out.println("1 - poczekaj jedną turę");
        System.out.println("2 - poczekaj wybraną ilość tur");
        System.out.println("3 - powrót do menu");
        int userChoice = takeUserChoice();
        if (!(userChoice>0&&userChoice<4)) return watchedWorldChoice();
        else return userChoice;
    }
}
