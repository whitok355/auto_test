package org.example;

import java.util.Random;

public class CarBMW implements CarInterface {
    private String title = "BMW";
    private int speed ;
    @Override
    public String getTitle(){
        return title;
    }
    @Override
    public int getSpeed() {
        return speed;
    }
    @Override
    public void race() {
        speed = new Random().nextInt(50, 250);
    }
    @Override
    public String checkInRace(){
       return String.format("Машина %s едет со скоростью %s км/ч//", title, speed);
    }
    @Override
    public String toString() {
        return String.format("Авто марки %s, разогналась до %s км/ч", title, speed);
    }
}
