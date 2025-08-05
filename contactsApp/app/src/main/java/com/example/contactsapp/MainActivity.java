package com.example.contactsapp;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;


public class MainActivity extends AppCompatActivity {
    private FrameLayout main_frame;
    private Button addbtn;
    private dbviewModel dbViewModel;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final contactAdapter adapter = new contactAdapter();
        recyclerView.setAdapter(adapter);

        dbViewModel = new ViewModelProvider(this).get(dbviewModel.class);

        dbViewModel.getAllcontacts().observe(this, new Observer<List<database>>() {
            @Override
            public void onChanged(List<database> db) {
                adapter.setDb(db);
            }
        });
    }
}