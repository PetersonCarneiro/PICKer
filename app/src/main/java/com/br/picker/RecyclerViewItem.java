package com.br.picker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.br.picker.utils.RecyclerItemClickListener;

import java.util.ArrayList;

public class RecyclerViewItem extends AppCompatActivity {

    private RecyclerView recyclerViewItem;
    private RecyclerView.LayoutManager layoutManager;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_item);

        recyclerViewItem = findViewById(R.id.recyclerViewItem);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewItem.setLayoutManager(layoutManager);
        recyclerViewItem.setHasFixedSize(true);
        recyclerViewItem.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        startingList();

        recyclerViewItem.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        recyclerViewItem, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Item item = itemsList.get(position);

                        Toast.makeText(getApplicationContext(),item.getSituacao(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Item item = itemsList.get(position);

                        Toast.makeText(getApplicationContext(),item.getLocalizacao(),Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
    ActivityResultLauncher<Intent> launcherNewItem = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent intent = result.getData();
                        Bundle bundle = intent.getExtras();

                        if(bundle!=null){
                            String plate = bundle.getString(MainActivity.PLATE);
                            String type = bundle.getString(MainActivity.TYPE);
                            String status = bundle.getString(MainActivity.STATUS);
                            String locale = bundle.getString(MainActivity.LOCALE);


                            Item item = new Item(plate,type,status,locale);

                            itemsList.add(item);

                            itemAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
    );


    public void startingList(){

        itemsList = new ArrayList<>();

        itemAdapter = new ItemAdapter(itemsList);
        recyclerViewItem.setAdapter(itemAdapter);

    }

    public void newItem(View view){

        MainActivity.newItem(this,launcherNewItem);
    }

    public void about(View view){
        AboutActivity.about(this);
    }




}