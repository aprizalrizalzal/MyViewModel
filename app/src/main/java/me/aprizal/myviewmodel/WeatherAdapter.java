package me.aprizal.myviewmodel;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final ArrayList<WeatherItems> mdata = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<WeatherItems> items) {
        mdata.clear();
        mdata.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_items, parent,false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.WeatherViewHolder holder, int position) {
        holder.bind(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCity;
        TextView textViewTemperature;
        TextView textViewDescription;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCity = itemView.findViewById(R.id.textCity);
            textViewTemperature = itemView.findViewById(R.id.textTemp);
            textViewDescription = itemView.findViewById(R.id.textDesc);
        }

        public void bind(WeatherItems weatherItems) {
            textViewCity.setText(weatherItems.getName());
            textViewTemperature.setText(weatherItems.getTemperature());
            textViewDescription.setText(weatherItems.getDescription());
        }
    }
}
