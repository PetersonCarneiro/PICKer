package com.br.picker;

import static com.br.picker.InputActivity.RESULTBARCODE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.br.picker.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;

public class RecyclerViewItem extends AppCompatActivity {

    private RecyclerView recyclerViewItem;
    private RecyclerView.LayoutManager layoutManager;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> itemsList;
    private ActionMode actionMode;
    private View viewSelecionado;
    private int posicaoSelecionada;

    public static final String ARQUIVE = "com.br.picker.sharedpreference.PREFERENCE";

    public static  final String ORDER_ASC = "ORDER_ASC";

    private  boolean orderAsc = true;


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.edit_menu_inflate, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.idMenuAlterar) {
                editItem();
                mode.finish();
                return true;
            } else {
                if (idMenuItem == R.id.idMenuDelete) {
                    deleteItem();
                    mode.finish();
                    return true;
                }
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionado != null) {
                viewSelecionado.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode = null;
            viewSelecionado = null;
            recyclerViewItem.setEnabled(true);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if(idMenuItem == R.id.idMenuNew){
            NewItemActivity.newItem(this,launcherNewItem);
            return true;
        }else
            if(idMenuItem == R.id.menuItemOrder){
                savePreferenceOrderAsc(!orderAsc);
                orderList();
                return true;
            }else
                if (idMenuItem == R.id.idMenuAbout){
                    AboutActivity.about(this);
                    return true;
                }else{
                    return super.onOptionsItemSelected(item);
                }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_item);
        setTitle("Lista de Itens");

        recyclerViewItem = findViewById(R.id.recyclerViewItem);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewItem.setLayoutManager(layoutManager);
        recyclerViewItem.setHasFixedSize(true);
        recyclerViewItem.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        readPreferenceOrderAsc();

        startingList();

        recyclerViewItem.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        recyclerViewItem,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Item item = itemsList.get(position);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Item item = itemsList.get(position);

                                if (actionMode != null) {
                                    return;
                                }
                                posicaoSelecionada = position;

                                view.setBackgroundColor(Color.LTGRAY);

                                viewSelecionado = view;
                                recyclerViewItem.setEnabled(false);

                                actionMode = startSupportActionMode(mActionModeCallback);
                            }
                        })
        );

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.edit_menu_inflate, menu);
    }

    ActivityResultLauncher<Intent> launcherNewItem = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            String plate = bundle.getString(NewItemActivity.PLATE);
                            String type = bundle.getString(NewItemActivity.TYPE);
                            String status = bundle.getString(NewItemActivity.STATUS);
                            String locale = bundle.getString(NewItemActivity.LOCALE);

                            Item item = new Item(plate, type, locale, status);

                            itemsList.add(item);

                            orderList();
                        }
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> launcherEditItem = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            String plate = bundle.getString(NewItemActivity.PLATE);
                            String type = bundle.getString(NewItemActivity.TYPE);
                            String status = bundle.getString(NewItemActivity.STATUS);
                            String locale = bundle.getString(NewItemActivity.LOCALE);

                            Item item = itemsList.get(posicaoSelecionada);
                            item.setPlaqueta(plate);
                            item.setTipo(type);
                            item.setSituacao(status);
                            item.setLocalizacao(locale);

                            posicaoSelecionada = -1;

                            orderList();
                        }
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> launcheresultItem = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            String resulBarCode = bundle.getString(RESULTBARCODE);


                            Item item = itemsList.get(posicaoSelecionada);
                            item.setPlaqueta(resulBarCode);

                            posicaoSelecionada = -1;

                            itemAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
    );


    public void startingList() {

        itemsList = new ArrayList<>();

        itemsList.add(new Item("1234","Mesa","Barração","Localizado"));

        itemAdapter = new ItemAdapter(itemsList);
        recyclerViewItem.setAdapter(itemAdapter);
    }

    public void newItem(View view) {
        NewItemActivity.newItem(this, launcherNewItem);
    }

    public void deleteItem() {
        itemsList.remove(posicaoSelecionada);

        itemAdapter.notifyDataSetChanged();
    }

    private void editItem() {
        Item item = itemsList.get(posicaoSelecionada);

        NewItemActivity.editItem(this, launcherEditItem, item);
    }

    private void resultItem() {
        Item item = itemsList.get(posicaoSelecionada);

        NewItemActivity.editItem(this, launcherEditItem, item);
    }


    public void about(View view) {

        AboutActivity.about(this);

    }

    private void orderList(){
        if(orderAsc){
            Collections.sort(itemsList,Item.comparatorAsc);
        }else{
            Collections.sort(itemsList,Item.comparatorDsc);
        }
        itemAdapter.notifyDataSetChanged();
    }

    private void readPreferenceOrderAsc(){
        SharedPreferences shared = getSharedPreferences(ARQUIVE, Context.MODE_PRIVATE);
        orderAsc = shared.getBoolean(ORDER_ASC,orderAsc);
    }

    private void savePreferenceOrderAsc(boolean newValue){
        SharedPreferences shared = getSharedPreferences(ARQUIVE,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putBoolean(ORDER_ASC,newValue);

        orderAsc = newValue;
    }

}