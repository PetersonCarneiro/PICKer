package com.br.picker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LocaleAdapter extends BaseAdapter {
    Context context;
    List<Locale> locales;

    private static class LocaleHolder{
        public TextView textViewNome;
    }

    public LocaleAdapter(Context context, List<Locale> locales) {
        this.context = context;
        this.locales = locales;
    }

    @Override
    public int getCount() {
        return locales.size();
    }

    @Override
    public Object getItem(int i) {
        return locales.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }



}
