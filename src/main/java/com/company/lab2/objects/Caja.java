package com.company.lab2.objects;

public class Caja {
    String codigo;
    String fecha;
    String lugar;
    Integer pesoTotal;

    public Caja(String codigo, String fecha, String lugar) {
        this.codigo=codigo;
        this.fecha = fecha;
        this.lugar = lugar;
    }
    public Caja(String codigo, String lugar){
        this.codigo=codigo;
        this.lugar=lugar;
    }
    public Caja(String codigo,Integer pesoTotal){
        this.codigo=codigo;
        this.pesoTotal=pesoTotal;
    }
    public String getCodigo() {
        return codigo;
    }
    public String getFecha() {
        return fecha;
    }
    public String getLugar() {
        return lugar;
    }
    public Integer getPesoTotal() {
        return pesoTotal;
    }
}
