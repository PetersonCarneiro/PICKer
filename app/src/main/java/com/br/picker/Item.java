package com.br.picker;

import java.util.Comparator;

public class Item {
    private String plaqueta;
    private String tipo;
    private String localizacao;
    private String situacao;
    public static Comparator comparatorAsc = new Comparator<Item>() {
        @Override
        public int compare(Item item1, Item item2) {
            return item1.getPlaqueta().compareToIgnoreCase(item2.getPlaqueta());
        }
    };
    public static Comparator comparatorDsc = new Comparator<Item>() {
        @Override
        public int compare(Item item1, Item item2) {
            return -1 * item1.getPlaqueta().compareToIgnoreCase(item2.getPlaqueta());
        }
    };

    public Item(String plaqueta, String tipo,String localizacao, String situacao ) {
        this.plaqueta = plaqueta;
        this.tipo = tipo;
        this.localizacao = localizacao;
        this.situacao = situacao;
    }

    public String getPlaqueta() {
        return plaqueta;
    }

    public void setPlaqueta(String plaqueta) {
        this.plaqueta = plaqueta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}

