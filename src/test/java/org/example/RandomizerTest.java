package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class RandomizerTest {
    Randomizer rnd = new Randomizer();
    @Test
    void checkReturnLengthDoorsList(){
        assertEquals(3, rnd.randomizer(3).size());
        assertNotEquals(3, rnd.randomizer(4).size());
    }
    @Test
    void checkWinnerAndLoserMarker() {
        int win = 0;
        int loser = 0;
        List<Door> doors = rnd.randomizer(3);
        for(Door door: doors){
            if(door.isWinner()){
                win +=1;
            }
            if(!door.isWinner()){
                loser +=1;
            }
        }
        assertThat(1).isEqualTo(win);
        assertThat(doors.size()-1).isEqualTo(loser);
    }
    @Test
    void checkTypeDoors(){
        List<Door> doors = rnd.randomizer(3);
        for(Door door: doors){
            assertInstanceOf(Door.class, door);
        }
    }
}
