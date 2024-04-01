package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randomizer {

    public List<Door> randomizer(int length){
        List <Door> doors = new ArrayList<>();
        boolean res = false;
        Random rnd = new Random();

        for(int i = 1; i < length + 1; i++){
            doors.add(new Door(false, i));
        }

        doors.get(rnd.nextInt(length)).setWinner(true);

        for(Door door: doors){
            if(door.isWinner()){
                res = true;
                break;
            }
        }
        return doors;
    }
}
