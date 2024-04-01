package org.example;

import java.util.List;
public class Race implements CarInterface {
    private List<CarInterface> cars;
    private CarInterface winner = null;
    private CarInterface loser = null;
    private String title = new RaceNumber().getRaceNumber();

    public Race(List<CarInterface> cars){
        this.cars= cars;
    }
    public CarInterface getWinner(){
        return winner;
    }
    public CarInterface getLoser(){
        return loser;
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getSpeed() {
        return winner.getSpeed() - loser.getSpeed();
    }
    @Override
    public void race() {
        for(int i = 0; i < cars.size(); i++){
            cars.get(i).race();
            if(winner == null){
                winner = cars.get(0);
            } else{
                if(cars.get(i).getSpeed() > winner.getSpeed()){
                    loser = winner;
                    winner = cars.get(i);
                } else if(cars.get(i).getSpeed() == winner.getSpeed()){
                    winner = null;
                    loser = null;
                } else {
                    loser = cars.get(i);
                }
            }
        }
    }
    @Override
    public String checkInRace(){
        if(winner == null && loser == null){
          return "\n---Результат заезда---\nНичья\nВ результате заезда оппоненты финишировали одновременно";
        } else{
            return String.format("\n---    Результат заезда от %s    ---\n%s обогнав \n%s \nна %s км/ч", title, winner.toString(), loser.toString(), getSpeed());
        }
    }
}