package org.example;

import java.util.Date;

public class RaceNumber {

    public String getRaceNumber(){
        Date date = new Date();
        return String.format("%s", date);
    }
}
