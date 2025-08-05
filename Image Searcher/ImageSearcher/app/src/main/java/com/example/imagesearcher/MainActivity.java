package com.example.imagesearcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.imagesearcher.adapter.ImagesAdapter;
import com.example.imagesearcher.api.ApiInterface;
import com.example.imagesearcher.api.ImageItem;
import com.example.imagesearcher.api.ImageResponseModel;
import com.example.imagesearcher.api.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvImages;
    SearchView searView;
    MaterialButton btnSingle, btnDouble;
    ProgressBar progressBar;
    ImagesAdapter imagesAdapter;
    List<ImageItem> imagesList = new ArrayList<>();
    String checkView = "single";
    private final String API_KEY = "40166023-8eff70e39ea801251ff5c6203";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvImages = findViewById(R.id.rvImages);
        btnSingle = findViewById(R.id.btnSingle);
        btnDouble = findViewById(R.id.btnDouble);
        searView = findViewById(R.id.searView);
        progressBar = findViewById(R.id.progressBar);

        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkView = "single";
                singleImageMethod();
            }
        });

        btnDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkView = "double";
                doubleImageMethod();
            }
        });

        searView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callApiMethod(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void singleImageMethod(){
        rvImages.setLayoutManager(new LinearLayoutManager(this));
        imagesAdapter = new ImagesAdapter(imagesList, this);
        rvImages.setAdapter(imagesAdapter);
        imagesAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void doubleImageMethod(){
        rvImages.setLayoutManager(new GridLayoutManager(this, 2));
        imagesAdapter = new ImagesAdapter(imagesList, this);
        rvImages.setAdapter(imagesAdapter);
        imagesAdapter.notifyDataSetChanged();
    }

    private void callApiMethod(String searchQuery){
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ImageResponseModel> call = apiInterface.searchImages(API_KEY, searchQuery, 15);
        call.enqueue(new Callback<ImageResponseModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ImageResponseModel> call, Response<ImageResponseModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    imagesList = response.body().getHits();
                    if (checkView.contentEquals("single")){
                        singleImageMethod();
                    }else{
                        doubleImageMethod();
                    }
                    if (imagesList.isEmpty()){
                        Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    // Handle the error
                }
            }

            @Override
            public void onFailure(Call<ImageResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Failure"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                // Handle the failure
            }
        });
    }

}