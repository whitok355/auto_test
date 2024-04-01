package org.example;

public class Door {
    //region
    private boolean isWinner;
    private int id;
    private boolean isOpen;
    public boolean isWinner() {
        return isWinner;
    }
    public int getId() {
        return id;
    }
    public boolean isOpen() {
        return isOpen;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
    public Door(boolean isWinner, int id){
        this.isWinner = isWinner;
        this.id = id;
        isOpen = false;
    }
    //endregion
    @Override
    public String toString() {
        return String.format("Дверь id %s выигрышная: %s", id, isWinner);
    }
}
