package com.br.picker;

public class Item {
    private String plaqueta;
    private String tipo;
    private String localizacao;
    private String situacao;

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

