package com.example.tic_ipg204_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.common.model.Material;
import com.example.tic_ipg204_project.common.model.Owner;

import java.util.List;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.UserViewHolder>{

    private List<Material> materials;
    private Context context;
    private MaterialInteraction materialInteraction;

    public MaterialsAdapter(Context context, List<Material> materials, MaterialInteraction materialInteraction) {

        this.materials = materials;
        this.context = context;
        this.materialInteraction = materialInteraction;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.owner_item, parent, false);
        return new UserViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        Material material = materials.get(position);
        holder.setListener(material , position);

        holder.id.setText("Id : "+String.valueOf(material.getMaterialId())+" - "+"\nis a service : "+(material.isService()?"YES":"NO"));
        holder.name.setText("Name : "+material.getMaterialName());
        holder.description.setText("Description : "+material.getDescription());
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }
    public interface MaterialInteraction {
        void onClickItem(Material material , int position);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView name, id, description  ;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            id = itemView.findViewById(R.id.item_id);
            description = itemView.findViewById(R.id.item_description);
        }
        public void setListener(final Material material , int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialInteraction.onClickItem(material , position);
                }
            });
        }
    }
}
