package com.example.mabiaat.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mabiaat.R;
import com.example.mabiaat.RepresentativeDetailsActivity;
import com.example.mabiaat.UpdateRepresentativeData;
import com.example.mabiaat.offlinedata.Myappdatabas;
import com.example.mabiaat.offlinedata.Representative;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RepresentativesAdapter extends RecyclerView.Adapter<RepresentativesAdapter.ViewHolder>{


    Context context;
    private List<Representative> representatives;
    Myappdatabas myappdatabas;

    // RecyclerView recyclerView;
    public RepresentativesAdapter(Context context, List<Representative> representatives) {
        this.context = context;
        this.representatives = representatives;
        this.myappdatabas = Myappdatabas.getDatabase(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.rep_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Representative representative = representatives.get(position);

        Glide.with(context)
                .load(Uri.parse(representative.getImageURL()))
                .into(holder.image);



        holder.name.setText(representative.getName());

        //call on click to notify main activity to switch to volunteer details fragment
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(context, RepresentativeDetailsActivity.class);
                info.putExtra("id", representative.getId());
                info.putExtra("name", representative.getName());
                info.putExtra("image", representative.getImageURL());
                info.putExtra("area", representative.getMainArea());

                context.startActivity(info);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            LayoutInflater factory = LayoutInflater.from(context);
            final View view = factory.inflate(R.layout.options_dialog, null);
            final AlertDialog optionsDialog = new AlertDialog.Builder(context).create();
            optionsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            optionsDialog.setView(view);

            Button update = view.findViewById(R.id.update);
            Button delete = view.findViewById(R.id.delete);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent update = new Intent(context, UpdateRepresentativeData.class);
                    update.putExtra("id", representative.getId());
                    update.putExtra("name", representative.getName());
                    update.putExtra("image", representative.getImageURL());
                    update.putExtra("area", representative.getMainArea());

                    context.startActivity(update);

                    optionsDialog.dismiss();

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myappdatabas.representativesDao().deleteRepresentative(representative);
                    removeItem(position);
                    optionsDialog.dismiss();
                }
            });
            optionsDialog.show();

            return true;
        });



    }

    private void removeItem(int position) {
        representatives.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return representatives.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image;
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.image);
            this.name = itemView.findViewById(R.id.name);
        }
    }


}
