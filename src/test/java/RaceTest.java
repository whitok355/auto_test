import org.example.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class RaceTest {

    static Stream<Arguments> getMockCars(){
        List<CarInterface> cars = new ArrayList<>();
        CarInterface firstCar = Mockito.mock(CarBMW.class);
        CarInterface secondCar = Mockito.mock(CarLada.class);
        Mockito.when(firstCar.getSpeed()).thenReturn(100);
        Mockito.when(secondCar.getSpeed()).thenReturn(200);
        Mockito.when(firstCar.getTitle()).thenReturn("BMW");
        Mockito.when(secondCar.getTitle()).thenReturn("Lada");
        cars.add(firstCar);
        cars.add(secondCar);

        return Stream.of(
            Arguments.of(cars)
        );
    }
    @ParameterizedTest
    @MethodSource("getMockCars")
    public void checkCalcDifferenceSpeed(List<CarInterface> cars){
        CarInterface race = new Race(cars);
        race.race();
        assertEquals(100, race.getSpeed());
    }
    @ParameterizedTest
    @MethodSource("getMockCars")
    public void checkWinnerAndLoser(List<CarInterface> cars){
        CarInterface race = new Race(cars);
        race.race();

        assertInstanceOf(CarLada.class, ((Race) race).getWinner());
        assertInstanceOf(CarBMW.class, ((Race) race).getLoser());
    }
    @Test
    public void checkMsgCar(){
        CarInterface firstCar = new CarBMW();
        CarInterface secondCar = new CarLada();
        List<CarInterface> cars = new ArrayList<CarInterface>();
        cars.add(firstCar);
        cars.add(secondCar);

        for(CarInterface car: cars){
            String msg = String.format("Машина %s едет со скоростью %s км/ч//",  car.getTitle(),  car.getSpeed());
            assertEquals(msg, car.checkInRace());
        }
    }
    @ParameterizedTest
    @MethodSource("getMockCars")
    public void checkMsgRaceUserNull(List<CarInterface> cars){
        CarInterface race = new Race(cars);

        String msgWInnerAndLoserNull = "\n---Результат заезда---\nНичья\nВ результате заезда оппоненты финишировали одновременно";
        assertEquals(msgWInnerAndLoserNull, race.checkInRace());
    }

    @ParameterizedTest
    @MethodSource("getMockCars")
    public void checkMsgRaceUserNotNull(List<CarInterface> cars) {
        CarInterface race = new Race(cars);
        race.race();
        String msgWInnerAndLoserNotNull = String.format("\n---    Результат заезда от %s    ---\n%s обогнав \n%s \nна %s км/ч",
                race.getTitle(),
                ((Race) race).getWinner().toString(),
                ((Race) race).getLoser().toString(),
                race.getSpeed());
        assertEquals(msgWInnerAndLoserNotNull, race.checkInRace());
    }
}
