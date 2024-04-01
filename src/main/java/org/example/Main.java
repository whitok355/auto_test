package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CarInterface firstCar = new CarBMW();
        CarInterface secondCar = new CarLada();
        List<CarInterface> cars = new ArrayList<CarInterface>();
        WriterClass wr = new WriterClass();
        cars.add(firstCar);
        cars.add(secondCar);

        Race race = new Race(cars);

        race.race();

        wr.writeMethod(firstCar.checkInRace());
        wr.writeMethod(secondCar.checkInRace());
        wr.writeMethod(race.checkInRace());

    }
}
