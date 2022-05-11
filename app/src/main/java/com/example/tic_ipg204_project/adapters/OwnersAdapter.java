package com.example.tic_ipg204_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tic_ipg204_project.R;
import com.example.tic_ipg204_project.common.model.Owner;

import java.util.List;

public class OwnersAdapter extends RecyclerView.Adapter<OwnersAdapter.UserViewHolder>{

    private List<Owner> owners;
    private Context context;
    private OwnerInteraction ownerInteraction;

    public OwnersAdapter(Context context, List<Owner> owners, OwnerInteraction ownerInteraction) {

        this.owners = owners;
        this.context = context;
        this.ownerInteraction = ownerInteraction;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.owner_item, parent, false);
        return new UserViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        Owner owner = owners.get(position);
        holder.setListener(owner , position);

        holder.id.setText("Id : "+String.valueOf(owner.getOwnerId()));
        holder.name.setText("Name : "+owner.getOwnerName());
        holder.description.setText("Description : "+owner.getDescription());
    }

    @Override
    public int getItemCount() {
        return owners.size();
    }
    public interface OwnerInteraction {
        void onClickItem(Owner owner , int position);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView name, id, description  ;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            id = itemView.findViewById(R.id.item_id);
            description = itemView.findViewById(R.id.item_description);
        }
        public void setListener(final Owner owner , int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ownerInteraction.onClickItem(owner , position);
                }
            });
        }
    }
}
