package com.example.tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<Countries> list;
    public MyAdapter(Context context, List<Countries> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.single_row,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyAdapter.MyViewHolder holder, int position) {
        Countries country= list.get(position);
        holder.name.setText(country.getCountry());
        holder.tConfirmed.setText("Total Confirmed:"+country.getTotalConfirmed());
        holder.tDeath.setText("Total Death:"+country.getTotalDeaths());
        holder.tRecovered.setText("Total Recovered:"+country.getTotalRecovered());
    }
    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,tConfirmed,tDeath,tRecovered;
        public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            name=(itemView.findViewById(R.id.country_name));
            tConfirmed=(itemView.findViewById(R.id.country_total_confirmed_cases));
            tDeath=(itemView.findViewById(R.id.country_total_death_cases));
            tRecovered=(itemView.findViewById(R.id.country_total_recovered_cases));
        }
    }
}
