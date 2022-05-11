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
import com.example.tic_ipg204_project.common.model.OutLay;
import com.example.tic_ipg204_project.common.model.Owner;
import com.example.tic_ipg204_project.common.sqlhleper.MyDbAdapter;

import java.util.List;

public class OutlaysAdapter extends RecyclerView.Adapter<OutlaysAdapter.UserViewHolder>{

    private List<OutLay> outlays;
    private Context context;
    private OutlayInteraction outlayInteraction;
    private MyDbAdapter myDbAdapter;
    public OutlaysAdapter(Context context, List<OutLay> outlays, OutlayInteraction outlayInteraction) {

        this.outlays = outlays;
        this.context = context;
        this.outlayInteraction = outlayInteraction;
        myDbAdapter =new MyDbAdapter(context);

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.outlay_item, parent, false);
        return new UserViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        OutLay outLay = outlays.get(position);
        holder.setListener(outLay , position);

        Owner owner = myDbAdapter.getOwnerById(outLay.getOwnerId());
        Material material = myDbAdapter.getMaterialById(outLay.getMaterialId());

        holder.tvOwner.setText("Owner ID : "+String.valueOf(outLay.getOwnerId())+" - Owner Name : "+owner.getOwnerName());
        holder.tvMaterial.setText("Material ID : "+outLay.getMaterialId()+" - Material Name : "+material.getMaterialName());
        holder.tvPrice.setText("Name : "+outLay.getPrice());
        holder.tvDescription.setText("Description : "+outLay.getDescription());
        holder.tvDate.setText("Date : "+outLay.getDate());
    }

    @Override
    public int getItemCount() {
        return outlays.size();
    }
    public interface OutlayInteraction {
        void onClickItem(OutLay outLay , int position);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView tvMaterial, tvOwner, tvPrice, tvDescription,tvDate;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaterial = itemView.findViewById(R.id.tv_material);
            tvOwner = itemView.findViewById(R.id.tv_owner);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
        public void setListener(final OutLay outLay , int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    outlayInteraction.onClickItem(outLay , position);
                }
            });
        }
    }
}
