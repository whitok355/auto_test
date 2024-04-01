package org.example;
import java.util.List;
import java.util.Random;

public class Game {
    private List<Door> doors;
    private int cycle;
    private int calcCycle = 0;
    private int winnerPoint = 0;
    private int answer;
    public int getCycle() {
        return cycle;
    }
    public int getCalcCycle() {
        return calcCycle;
    }
    public int getWinnerPoint() {
        return winnerPoint;
    }
    public Game(List<Door> doors, int cycle) {
        this.doors = doors;
        this.cycle = cycle;
    }
    public void start(){
        Random rnd = new Random();
        do{
            answer = rnd.nextInt(1, 3);
            if(checkAnswer(answer)){
                winnerPoint += 1;
            }
            calcCycle++;
        } while(calcCycle < cycle);
    }
    public boolean checkAnswer(int answer){
        return doors.get(answer).isWinner();
    }
    @Override
    public String toString() {
        return String.format("Произведено %s розыгрышей, из них побед %s и поражений %s", calcCycle, winnerPoint, (cycle-winnerPoint));
    }
}
