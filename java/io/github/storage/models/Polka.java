package io.github.storage.models;

import java.util.List;

public class Polka {
    private int stillage_number;
    private int number;

    public Polka(int stillage_number, int number) {
        this.stillage_number = stillage_number;
        this.number = number;
    }

    public int getStillage_number() {
        return stillage_number;
    }

    public void setStillage_number(int stillage_number) {
        this.stillage_number = stillage_number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
