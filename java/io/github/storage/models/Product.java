package io.github.storage.models;

public class Product {

    private int polka_number;
    private int stillage_number;
    private String name;
    private String quantity;

    public Product(int polka_number, int stillage_number, String name, String quantity) {
        this.polka_number = polka_number;
        this.stillage_number = stillage_number;
        this.name = name;
        this.quantity = quantity;
    }

    public int getPolka_number() {
        return polka_number;
    }

    public void setPolka_number(int polka_number) {
        this.polka_number = polka_number;
    }

    public int getStillage_number() {
        return stillage_number;
    }

    public void setStillage_number(int stillage_number) {
        this.stillage_number = stillage_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
