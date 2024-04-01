package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GamaTest {
    static Randomizer rnd = new Randomizer();
    static Stream<Arguments> generateArguments(){
        return Stream.of(
                Arguments.of(1000, rnd.randomizer(3)),
                Arguments.of(2000, rnd.randomizer(4)),
                Arguments.of(1, rnd.randomizer(5))
        );
    }
    static Stream<Arguments> generateArgument(){
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(true, 1));
        doors.add(new Door(false, 2));
        doors.add(new Door(false, 3));
        return Stream.of(
                Arguments.of(1, doors)
        );
    }
    @ParameterizedTest
    @MethodSource("generateArguments")
    void checkQuantityIteration(int iter, List<Door> doors){
        Game game = new Game(doors, iter);
        game.start();
        assertEquals(iter, game.getCalcCycle());
    }
    @Test
    void checkToString(){
        Game game = new Game(rnd.randomizer(3), 1);
        game.start();
        int win = game.getWinnerPoint();
        int loser = game.getCycle() - win;
        int calcCycle = game.getCalcCycle();
        String msg = String.format("Произведено %s розыгрышей, из них побед %s и поражений %s", calcCycle, win, loser);
        assertEquals(msg, game.toString());
    }
    @ParameterizedTest
    @MethodSource("generateArgument")
    void checkCheckAnswerMethod(int iter, List<Door> doors){
        Game game = new Game(doors, iter);
        assertTrue(game.checkAnswer(0));
        assertFalse(game.checkAnswer(1));
        assertFalse(game.checkAnswer(2));
    }
}
