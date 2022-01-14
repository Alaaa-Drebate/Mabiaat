package com.example.mabiaat.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mabiaat.CommissionDetailsActivity;
import com.example.mabiaat.R;
import com.example.mabiaat.offlinedata.Commission;
import com.example.mabiaat.offlinedata.Myappdatabas;
import com.example.mabiaat.offlinedata.Representative;


import java.util.List;

public class CommissionsAdapter extends RecyclerView.Adapter<CommissionsAdapter.ViewHolder>{


    Context context;
    private List<Commission> commissions;
    Myappdatabas myappdatabas;

    // RecyclerView recyclerView;
    public CommissionsAdapter(Context context, List<Commission> commissions) {
        this.context = context;
        this.commissions = commissions;
        myappdatabas = Myappdatabas.getDatabase(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.commission_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Commission commission = commissions.get(position);

        holder.month.setText(String.valueOf(commission.getMonth()));

        holder.year.setText(String.valueOf(commission.getYear()));

        Representative representative = myappdatabas.representativesDao().getRepresentativeById(commission.getRepresentativeId());
        holder.repName.setText(representative.getName());

        holder.commission.setText(String.valueOf(commission.getCommission()) + " S.P");

        holder.itemView.setOnClickListener(v -> {
            Intent details = new Intent(context, CommissionDetailsActivity.class);
            details.putExtra("commission_id", commission.getId());
            context.startActivity(details);
        });


    }

    @Override
    public int getItemCount() {
        return commissions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView year;
        public TextView month;
        public TextView repName;
        public TextView commission;

        public ViewHolder(View itemView) {
            super(itemView);
            this.month = itemView.findViewById(R.id.month);
            this.year = itemView.findViewById(R.id.year);
            this.repName = itemView.findViewById(R.id.name);
            this.commission = itemView.findViewById(R.id.commission);
        }
    }


}