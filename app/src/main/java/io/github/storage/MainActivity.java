package io.github.storage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import io.github.storage.adapters.PolkaAdapter;
import io.github.storage.adapters.ProductAdapter;
import io.github.storage.adapters.StillageAdapter;
import io.github.storage.data.StorageDb;
import io.github.storage.databinding.ActivityMainBinding;
import io.github.storage.databinding.BotSheetProductBinding;
import io.github.storage.models.Polka;
import io.github.storage.models.Product;
import io.github.storage.models.Stillage;
import io.github.storage.models.Storage;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    StillageAdapter stillageAdapter;
    StorageDb storageDb;
    public static final String STORAGE1 = "Storage Main";

    BottomSheetDialog dialogPolkaList;
    ActivityMainBinding bindingPolka;
    PolkaAdapter polkaAdapter;

    BottomSheetDialog dialogProductList;
    ActivityMainBinding bindingProduct;
    ProductAdapter productAdapter;

    BottomSheetDialog dialogAddProduct;
    BotSheetProductBinding bindingAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storageDb = initDb();
        init();
        listeners();


    }

    private void init(){
        stillageAdapter = new StillageAdapter();
        binding.rvMain.setAdapter(stillageAdapter);
        stillageAdapter.setStillageList(storageDb.getStillageList());
        binding.textTitle.setText(STORAGE1);
    }
    private void listeners(){
        binding.btnClose.setOnClickListener(v -> {
            if (binding.textTitle.getVisibility() == View.GONE) {
                binding.textTitle.setVisibility(View.VISIBLE);
                binding.etSearch.setVisibility(View.GONE);
            } else onBackPressed();
        });
        binding.btnAdd.setOnClickListener(v -> {
            int position = storageDb.addStillage(STORAGE1);
            stillageAdapter.notifyItemInserted(position);
        });
        stillageAdapter.setOnItemClickListener(new StillageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Stillage stillage) {
                getPolkas(stillage);
            }

            @Override
            public void onDeleteClick(int position) {
                storageDb.removeStillage(position);
                stillageAdapter.notifyItemRemoved(position);
                showToast("Удалено !");
            }
        });
        binding.btnSearch.setOnClickListener(v -> {
            if (binding.textTitle.getVisibility() == View.VISIBLE) {
                binding.textTitle.setVisibility(View.GONE);
                binding.etSearch.setVisibility(View.VISIBLE);
            }else {
                String searchText = binding.etSearch.getText().toString();
                if (searchText.trim().isEmpty()) {
                    showToast("Введите текст для поиска !");
                    return;
                }
                showProducts(searchText);
            }
        });
    }

    private void showProducts(String search){
        if (dialogProductList == null) {
            //setting bottom sheet dialog
            dialogProductList = new BottomSheetDialog(MainActivity.this);
            bindingProduct = DataBindingUtil.inflate(
                    LayoutInflater.from(MainActivity.this),
                    R.layout.activity_main,
                    findViewById(R.id.container),
                    false);
            dialogProductList.setContentView(bindingProduct.getRoot());
            productAdapter = new ProductAdapter();
            bindingProduct.rvMain.setAdapter(productAdapter);
            bindingProduct.btnClose.setOnClickListener(v -> dialogProductList.dismiss());
            productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Product product) {
                    showToast("Стеллаж : " + product.getStillage_number() + "\nПолка : " + product.getPolka_number());
                }

//https://github.com/kazak896/java112.git
                @Override
                public void onDeleteClick(Product product, int position) {
                    storageDb.removeProduct(product);
                    productAdapter.removeProduct(position);
                }
            });

        }
        bindingProduct.btnAdd.setVisibility(View.INVISIBLE);
        bindingProduct.textTitle.setText("Результат поиска");
        //productAdapter.setOnItemClickListener();
        if (productAdapter.setProductList(storageDb.getProducts(search)) == 0) {
            showToast("Ничего не найдено ...");
        }
        dialogProductList.show();
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private StorageDb initDb(){
        List<Storage> storageList= new ArrayList<>();
        storageList.add(new Storage(STORAGE1));
        List<Stillage> stillageList = new ArrayList<>();
        stillageList.add(new Stillage(STORAGE1, 1));
        stillageList.add(new Stillage(STORAGE1, 2));
        List<Polka> polkaList = new ArrayList<>();
        polkaList.add(new Polka(1, 1));
        polkaList.add(new Polka(2, 1));
        polkaList.add(new Polka(1, 2));
        List<Product> productList = new ArrayList<>();

        productList.add(new Product(1, 1, "Масло", "50"));
        productList.add(new Product(1, 1, "Фильтр", "35"));
        productList.add(new Product(2, 1, "Свечи", "150"));


        return new StorageDb(
                storageList,
                stillageList,
                polkaList,
                productList
        );
    }

    //test
    private void getPolkas(Stillage stillage) {
        if (dialogPolkaList == null) {
            //setting bottom sheet dialog
            dialogPolkaList = new BottomSheetDialog(MainActivity.this);
            bindingPolka = DataBindingUtil.inflate(
                    LayoutInflater.from(MainActivity.this),
                    R.layout.activity_main,
                    findViewById(R.id.container),
                    false);
            dialogPolkaList.setContentView(bindingPolka.getRoot());
            polkaAdapter = new PolkaAdapter();
            bindingPolka.rvMain.setAdapter(polkaAdapter);
            bindingPolka.btnClose.setOnClickListener(v -> dialogPolkaList.dismiss());
            bindingPolka.btnSearch.setVisibility(View.INVISIBLE);
        }
        bindingPolka.btnAdd.setOnClickListener(v -> polkaAdapter.addPolka(storageDb.addPolka(stillage.getNumber(), polkaAdapter.getLastNumber())));
        polkaAdapter.setOnItemClickListener(new PolkaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Polka polka) {
                getProducts(polka);
            }

            @Override
            public void onDeleteClick(Polka polka, int position) {
                storageDb.removePolka(polka);
                polkaAdapter.removePolka(position);
            }
        });
        bindingPolka.textTitle.setText("Cтеллаж № " + stillage.getNumber());
        polkaAdapter.setPolkaList(storageDb.getPolkaListBySt(stillage.getNumber()));
        dialogPolkaList.show();
    }
    String title;
    private void getProducts(Polka polka) {
        if (dialogProductList == null) {
            //setting bottom sheet dialog
            dialogProductList = new BottomSheetDialog(MainActivity.this);
            bindingProduct = DataBindingUtil.inflate(
                    LayoutInflater.from(MainActivity.this),
                    R.layout.activity_main,
                    findViewById(R.id.container),
                    false);
            dialogProductList.setContentView(bindingProduct.getRoot());
            productAdapter = new ProductAdapter();
            bindingProduct.rvMain.setAdapter(productAdapter);
            bindingProduct.btnClose.setOnClickListener(v -> dialogProductList.dismiss());
            productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Product product) {
                    showToast("Стеллаж : " + product.getStillage_number() + "\nПолка : " + product.getPolka_number());
                }

                @Override
                public void onDeleteClick(Product product, int position) {
                    storageDb.removeProduct(product);
                    productAdapter.removeProduct(position);
                }
            });

        }
        bindingProduct.btnAdd.setOnClickListener(v -> addProduct(polka));
        bindingProduct.btnAdd.setVisibility(View.VISIBLE);
        //productAdapter.setOnItemClickListener();
        title = "St№" + polka.getStillage_number() + " / P№" + polka.getNumber();
        bindingProduct.textTitle.setText(title);
        productAdapter.setProductList(storageDb.getProducts(polka.getStillage_number(), polka.getNumber()));
        dialogProductList.show();
    }



    private void addProduct(Polka polka){
        if (dialogAddProduct == null) {
            //setting bottom sheet dialog
            dialogAddProduct = new BottomSheetDialog(MainActivity.this);
            bindingAddProduct = DataBindingUtil.inflate(
                    LayoutInflater.from(MainActivity.this),
                    R.layout.bot_sheet_product,
                    findViewById(R.id.product_container),
                    false);
            dialogAddProduct.setContentView(bindingAddProduct.getRoot());
            bindingAddProduct.btnClose.setOnClickListener(v -> dialogAddProduct.dismiss());
        }
        title = "St№" + polka.getStillage_number() + " / P№" + polka.getNumber();
        bindingAddProduct.etName.setText("");
        bindingAddProduct.etQuantity.setText("");
        bindingAddProduct.textTitle.setText(title);
        bindingAddProduct.btnSave.setOnClickListener(v -> saveProduct(polka));
        dialogAddProduct.show();
    }
    private void saveProduct(Polka polka){
        String name = bindingAddProduct.etName.getText().toString();
        String quantity = bindingAddProduct.etQuantity.getText().toString();
        if (name.trim().isEmpty() || quantity.trim().isEmpty()){
            showToast("Не все данные введены !");
            return;
        }
        Product product = new Product(polka.getNumber(), polka.getStillage_number(), name, quantity);
        storageDb.addProduct(product);
        productAdapter.addProduct(product);
        dialogAddProduct.dismiss();
    }

}