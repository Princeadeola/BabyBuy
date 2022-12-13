package com.example.babybuy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babybuy.Modal.CheckListModalClass;
import com.example.babybuy.R;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {
    private List<CheckListModalClass> itemList;

    public CheckListAdapter(List<CheckListModalClass> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int itemImageResource = itemList.get(position).getItemImage();
        String itemName = itemList.get(position).getItemName();
        int itemAddImage = itemList.get(position).getAddItemImage();

        holder.setDate(itemImageResource, itemName, itemAddImage);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemNameTxt;
        private ImageView addImageIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.checkListItemImageID);
            itemNameTxt = itemView.findViewById(R.id.itemNameID);
            addImageIcon = itemView.findViewById(R.id.addItemImageIconID);

        }

        public void setDate(int itemImageResource, String itemName, int itemAddImage) {
            itemImage.setImageResource(itemImageResource);
            itemNameTxt.setText(itemName);
            addImageIcon.setImageResource(itemAddImage);
        }
    }
}
