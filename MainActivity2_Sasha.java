package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity  implements MyRecyclerViewAdapter.ItemClickListener {
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    //    recyclerView=findViewById(R.id.recyclerview);
//    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentDetails detailsFragment;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.recycler_layout_land);
            detailsFragment = (FragmentDetails) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (detailsFragment == null) {
                detailsFragment = new FragmentDetails();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, detailsFragment)
                        .commit();
            }
        } else {
            setContentView(R.layout.activity_main2);



        //      setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        String city = (bundle.get("key")).toString();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")// базова частина адреси
                .addConverterFactory(GsonConverterFactory.create())// конвертер
                .build();
        String ApiKey = "3d822b9dce4e57f12b9b3400d480a358";
        FourDayWeather WheatherApi;
        WheatherApi = retrofit.create(FourDayWeather.class);
        WheatherApi.GetForecastByCityName(city, ApiKey).enqueue(new Callback<Weather_class>() {// асинхронний виклик (для синхронного був би метод execute() )
            @Override
            public void onResponse(Call<Weather_class> call, Response<Weather_class> response) {
                if (response.isSuccessful()) {
                    adapter = new MyRecyclerViewAdapter(getApplicationContext(), response.body().getList());
                    adapter.setClickListener(MainActivity2.this::onItemClick);
                    recyclerView.setAdapter(adapter);
                    Log.i("oleksandr", response.body().getCity().getName().toString());
                    for (int i = 0; i < 4; i++) {
                        Log.i(" temp", "" + (Double.valueOf(response.body().getList().get(i).getMain().getTemp().toString()) - 273));
                        Log.i(" getget", response.body().getList().get(i).toString());
                        Log.i(" wind", response.body().getList().get(i).getWind().getSpeed().toString());
                        Log.i(" infrf", (response.body().getList().get(i).getWeather()).get(0).getIcon().toString());
                        //Log.i(" date",  response.body().getList().get(i).getDtTxt());
                        Log.i(" pressure", response.body().getList().get(i).getMain().getPressure().toString());
                    }

                    Log.i("Oleksandr", "OK");
                } else {
                    Log.i("Oleksandr", "no reponse");
                    Log.i("TAG", "Response: " + response.raw().request().url());
                }
            }


            public void onItemClick(List weather) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    FragmentDetails fragmentDetails = new FragmentDetails();
                    fragmentDetails.setData(weather);
                    fragmentDetails.update(weather);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragmentDetails);
                    transaction.commit();
                }
            }



            @Override
            public void onFailure(Call<Weather_class> call, Throwable t) {
                Log.i("Oleksandr", "Failure" + t);

            }
        });


    }
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

}
