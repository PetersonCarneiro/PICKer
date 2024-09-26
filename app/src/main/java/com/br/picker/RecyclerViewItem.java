package com.br.picker;

import static com.br.picker.InputActivity.RESULTBARCODE;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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

import com.br.picker.DAO.ItemDatabase;
import com.br.picker.model.Item;
import com.br.picker.utils.RecyclerItemClickListener;
import com.br.picker.utils.UtilsGUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                    deleteItem(mode);
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
        setTitle(getString(R.string.item_list));

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

                                view.setBackgroundColor(Color.GRAY);

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

                            long id = bundle.getLong(NewItemActivity.ID);

                            ItemDatabase database = ItemDatabase.getDataBase(RecyclerViewItem.this);

                            Item itemInsert = database.itemDao().queryForId(id);

                            itemsList.add(itemInsert);

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

                            long id = bundle.getLong(NewItemActivity.ID);

                            ItemDatabase database = ItemDatabase.getDataBase(RecyclerViewItem.this);

                            Item itemEdit = database.itemDao().queryForId(id);

                            itemsList.set(posicaoSelecionada,itemEdit);

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

        ItemDatabase database = ItemDatabase.getDataBase(this);

        if(orderAsc){
            itemsList = (ArrayList<Item>) database.itemDao().queryAllAscending();
        }else{
            itemsList = (ArrayList<Item>) database.itemDao().queryAlLDownward();
        }
        itemAdapter = new ItemAdapter(this,itemsList);
        recyclerViewItem.setAdapter(itemAdapter);
    }

    public void newItem(View view) {
        NewItemActivity.newItem(this, launcherNewItem);
    }

    public void deleteItem( final ActionMode mode) {


       final  Item item = itemsList.get(posicaoSelecionada);

        String msg = getString(R.string.do_you_really_want_to_delete) + "\n" + "\""+ item.getPlaqueta() + "\"";

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        ItemDatabase database = ItemDatabase.getDataBase(RecyclerViewItem.this);

                        int qtdAlterada = database.itemDao().delete(item);
                        if(qtdAlterada>0){
                            itemsList.remove(posicaoSelecionada);
                            itemAdapter.notifyDataSetChanged();
                            mode.finish();

                        }else{
                            UtilsGUI.alert(RecyclerViewItem.this,R.string.error_trying_to_delete);
                        }

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        UtilsGUI.ConirmationAlert(this,msg,listener);
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