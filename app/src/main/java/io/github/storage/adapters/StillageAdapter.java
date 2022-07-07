package io.github.storage.adapters;

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
import io.github.storage.models.Stillage;

public class StillageAdapter extends RecyclerView.Adapter<StillageAdapter.StillageHolder> {

    private List<Stillage> stillageList = new ArrayList<>();
    private StillageAdapter.OnItemClickListener listener;


    @NonNull
    @Override
    public StillageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_storage, parent, false);
        return new StillageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StillageHolder holder, int position) {

        Stillage stillage = stillageList.get(position);
        holder.tvNumber.setText(String.valueOf("Стеллаж № " + stillage.getNumber()));

    }

    @Override
    public int getItemCount() {
        return stillageList.size();
    }


    public void setStillageList(List<Stillage> stillageList) {
        this.stillageList = stillageList;
        notifyDataSetChanged();
    }

    class StillageHolder extends RecyclerView.ViewHolder {
        private TextView tvNumber;
        private TextView tvQuantity;
        private ImageView ivDelete;


        public StillageHolder(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            ivDelete = itemView.findViewById(R.id.btn_delete);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(stillageList.get(position));
                }
            });
            ivDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Stillage stillage);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(StillageAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
