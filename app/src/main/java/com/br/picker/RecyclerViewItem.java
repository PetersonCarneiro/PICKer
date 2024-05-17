package com.br.picker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ArrayList<Item> items;

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
                        Item item = items.get(position);

                        Toast.makeText(getApplicationContext(),item.getSituacao(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Item item = items.get(position);

                        Toast.makeText(getApplicationContext(),item.getLocalizacao(),Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    public void startingList(){
        int[] plaquetas = getResources().getIntArray(R.array.plaquetas);
        String[] tipo = getResources().getStringArray(R.array.tipos);
        String[] local = getResources().getStringArray(R.array.local);
        String[] status = getResources().getStringArray(R.array.status);

        items = new ArrayList<>();

        for(int cont = 0 ; cont < plaquetas.length; cont++){
            items.add(new Item(plaquetas[cont],tipo[cont],local[cont],status[cont]));
        }

        itemAdapter = new ItemAdapter(items);
        recyclerViewItem.setAdapter(itemAdapter);

    }




}