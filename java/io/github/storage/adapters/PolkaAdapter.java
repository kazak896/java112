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
import io.github.storage.models.Polka;
import io.github.storage.models.Stillage;

public class PolkaAdapter extends RecyclerView.Adapter<PolkaAdapter.PolkaHolder> {

    private List<Polka> polkaList = new ArrayList<>();
    private PolkaAdapter.OnItemClickListener listener;


    @NonNull
    @Override
    public PolkaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_storage, parent, false);
        return new PolkaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PolkaHolder holder, int position) {

        Polka polka = polkaList.get(position);
        holder.tvNumber.setText(String.valueOf("Полка № " + polka.getNumber()));
    }

    @Override
    public int getItemCount() {
        return polkaList.size();
    }


    public void setPolkaList(List<Polka> polkaList) {
        this.polkaList = polkaList;
        notifyDataSetChanged();
    }

    public void addPolka(Polka polka){
        polkaList.add(polka);
        notifyItemInserted(polkaList.size() - 1);
    }

    public int getLastNumber(){
        int size = polkaList.size();
        if (size != 0){
            return polkaList.get(size - 1).getNumber() + 1;
        } else return 1;

    }
    public void removePolka(int position){
        polkaList.remove(position);
        notifyItemRemoved(position);
    }

    class PolkaHolder extends RecyclerView.ViewHolder {
        private TextView tvNumber;
        private TextView tvQuantity;
        private ImageView ivDelete;


        public PolkaHolder(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_number);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            ivDelete = itemView.findViewById(R.id.btn_delete);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(polkaList.get(position));
                }
            });
            ivDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(polkaList.get(position), position);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Polka polka);

        void onDeleteClick(Polka polka, int position);
    }

    public void setOnItemClickListener(PolkaAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
