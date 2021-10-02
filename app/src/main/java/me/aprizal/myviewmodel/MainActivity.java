package me.aprizal.myviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WeatherAdapter adapter;
    private EditText edtCity;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCity = findViewById(R.id.editCity);
        progressBar = findViewById(R.id.progressBar);
        Button btnCity = findViewById(R.id.btnCity);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        btnCity.setOnClickListener(view -> {
            String city = edtCity.getText().toString();
            if (TextUtils.isEmpty(city)) return;
            showLoading(true);
            mainViewModel.setWeather(city);
        });

        mainViewModel.getWeathers().observe(this, weatherItems -> {
            if (weatherItems != null) {
                adapter.setData(weatherItems);
                showLoading(false);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
/*
    private void setWeather(String city) {
        final ArrayList<WeatherItems> listItems = new ArrayList<>();
        String apiKey = "b73455de914a7280ffae82d716064c11";
        String url = "https://api.openweathermap.org/data/2.5/group?id=" + city + "&units=metric&appid=" + apiKey;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //parsing json
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems();
                        weatherItems.setId(weather.getInt("id"));
                        weatherItems.setName(weather.getString("name"));
                        weatherItems.setCurrentWeather(weather.getJSONArray("weather").getJSONObject(0).getString("main"));
                        weatherItems.setDescription(weather.getJSONArray("weather").getJSONObject(0).getString("description"));
                        double tempInKelvin = weather.getJSONObject("main").getDouble("temp");
                        double tempInCelsius = tempInKelvin - 273;
                        weatherItems.setTemperature(new DecimalFormat("##.##").format(tempInCelsius));
                        listItems.add(weatherItems);
                    }
                    //set data ke adapter
                    adapter.setData(listItems);
                    showLoading(false);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
*/

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}