package io.github.storage.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.github.storage.models.Polka;
import io.github.storage.models.Product;
import io.github.storage.models.Stillage;
import io.github.storage.models.Storage;

public class StorageDb {

    private List<Storage> storageList;
    private List<Stillage> stillageList;
    private List<Polka> polkaList;
    private List<Product> productList;

    public StorageDb(List<Storage> storageList, List<Stillage> stillageList, List<Polka> polkaList, List<Product> productList) {
        this.storageList = storageList;
        this.stillageList = stillageList;
        this.polkaList = polkaList;
        this.productList = productList;
    }

    public void addStorage(Storage storage){
        storageList.add(storage);
    }

    public void removeStorage(int position){
        storageList.remove(position);
    }

    // -------------- STILLAGE -------------------///

    public List<Stillage> getStillageList(){
        return stillageList;
    }

    public int addStillage(String storage_name){
        int stillageCount = stillageList.size();
        int newNumber = stillageList.get(stillageCount - 1).getNumber() + 1;
        stillageList.add(new Stillage(storage_name, newNumber));
        return stillageCount;
    }

    public void removeStillage(int position){
        int stillage_number = stillageList.get(position).getNumber();
        polkaList = polkaList.stream().filter(polka -> polka.getStillage_number() != stillage_number).collect(Collectors.toList());
        productList = productList.stream().filter(product -> product.getStillage_number() != stillage_number).collect(Collectors.toList());
//        for (Product product : productList) {
//            Log.d("TAG", product.getName());
//        }
//        for (Polka polka : polkaList){
//            Log.d("TAG", "Stillage : " + polka.getStillage_number() + "Polka : " + polka.getNumber());
//        }
        stillageList.remove(position);
    }

    // -------------- POLKA ----------------------///


    public List<Polka> getPolkaList(){
        return polkaList;
    }

    public List<Polka> getPolkaListBySt(int stillage_number){
        return polkaList.stream().filter(polka -> polka.getStillage_number() == stillage_number).collect(Collectors.toList());
    }

    public Polka addPolka(int stillage_number, int newNumber){
        Polka polka = new Polka(stillage_number, newNumber);
        polkaList.add(polka);
        return polka;
    }

    public void removePolka(Polka polka){
        List<Product> listToDelete = productList.stream().filter(product ->
                        product.getStillage_number() == polka.getStillage_number() && product.getPolka_number() == polka.getNumber())
                .collect(Collectors.toList());
        productList.removeAll(listToDelete);
        for (Product product : productList) {
            Log.d("TAG", product.getName());
        }
        polkaList.remove(polka);
    }

    // -------------- PRODUCT ----------------------///

    public List<Product> getProducts(int stillage_number, int polka_number) {
        return productList.stream().filter(product ->
                product.getStillage_number() == stillage_number && product.getPolka_number() == polka_number)
                .collect(Collectors.toList());
    }

    public List<Product> getProducts(String name) {
        return productList.stream().filter(product ->
                        product.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product){
        productList.remove(product);
    }



}
