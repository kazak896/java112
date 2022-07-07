package io.github.storage.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.storage.R;
import io.github.storage.models.Polka;
import io.github.storage.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<Product> productList = new ArrayList<>();
    private ProductAdapter.OnItemClickListener listener;
    String quantity;

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_storage, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductHolder holder, int position) {

        Product product = productList.get(position);
        holder.tvNumber.setText(product.getName());
        quantity = product.getQuantity() + " шт.";
        holder.tvQuantity.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public int setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
        return productList.size();
    }

    public void addProduct(Product product){
        productList.add(product);
        notifyItemInserted(productList.size() - 1);
    }


    public void removeProduct(int position){
        productList.remove(position);
        notifyItemRemoved(position);
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        private TextView tvNumber;
        private TextView tvQuantity;
        private ImageView ivDelete;


        public ProductHolder(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvQuantity.setVisibility(View.VISIBLE);
            ivDelete = itemView.findViewById(R.id.btn_delete);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(productList.get(position));
                }
            });
            ivDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(productList.get(position), position);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);

        void onDeleteClick(Product product, int position);
    }

    public void setOnItemClickListener(ProductAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
