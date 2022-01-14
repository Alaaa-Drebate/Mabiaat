package com.example.mabiaat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mabiaat.R;
import com.example.mabiaat.offlinedata.Myappdatabas;
import com.example.mabiaat.offlinedata.Representative;
import com.example.mabiaat.offlinedata.Sales;

import java.util.List;


public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder>{


    Context context;
    private List<Sales> sales;
    Myappdatabas myappdatabas;

    // RecyclerView recyclerView;
    public SalesAdapter(Context context, List<Sales> sales) {
        this.context = context;
        this.sales = sales;
        myappdatabas = Myappdatabas.getDatabase(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.sales_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Sales sales1 = sales.get(position);

        holder.month.setText(String.valueOf(sales1.getMonth()));

        holder.year.setText(String.valueOf(sales1.getYear()));

        Representative representative = myappdatabas.representativesDao().getRepresentativeById(sales1.getRepresentativeId());
        holder.repName.setText(representative.getName());

        holder.north.setText("" + sales1.getNorth() + " S.P");
        holder.south.setText("" + sales1.getSouth() + " S.P");
        holder.east.setText("" + sales1.getEast() + " S.P");
        holder.west.setText("" + sales1.getWest() + " S.P");
        holder.lebanon.setText("" + sales1.getLebanon() + " S.P");


    }

    @Override
    public int getItemCount() {
        return sales.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView year;
        public TextView month;
        public TextView repName;
        public TextView north;
        public TextView south;
        public TextView east;
        public TextView west;
        public TextView lebanon;

        public ViewHolder(View itemView) {
            super(itemView);
            this.month = itemView.findViewById(R.id.month);
            this.year = itemView.findViewById(R.id.year);
            this.repName = itemView.findViewById(R.id.name);
            this.north = itemView.findViewById(R.id.north);
            this.south = itemView.findViewById(R.id.south);
            this.east = itemView.findViewById(R.id.east);
            this.west = itemView.findViewById(R.id.west);
            this.lebanon = itemView.findViewById(R.id.lebanon);
        }
    }


}
