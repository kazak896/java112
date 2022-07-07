package io.github.storage.models;

public class Stillage {

    private String storage_name;
    private int number;

    public String getStorage_name() {
        return storage_name;
    }

    public void setStorage_name(String storage_name) {
        this.storage_name = storage_name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Stillage(String storage_name, int number) {
        this.storage_name = storage_name;
        this.number = number;
    }
}
