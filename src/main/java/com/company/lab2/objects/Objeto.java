package com.company.lab2.objects;

public class Objeto {
    String codigo;
    String nombre;
    String tipoExtracion;
    Integer alto;
    Integer largo;
    Integer expesor;
    Integer peso;
    Integer cantidad;
    String fechaRegistro;
    String descripcion;
    String origen;
    String cuCodAsocia;
    String caCodContiene;
    Integer pDniIngresa;
    Character oEs;

    public Objeto(String codigo, String nombre, String tipoExtracion, Integer alto, Integer largo, Integer expesor, Integer peso, Integer cantidad, String fechaRegistro, String descripcion, String origen, String cuCodAsocia, String caCodContiene, Integer pDniIngresa, Character oEs) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipoExtracion = tipoExtracion;
        this.alto = alto;
        this.largo = largo;
        this.expesor = expesor;
        this.peso = peso;
        this.cantidad = cantidad;
        this.fechaRegistro = fechaRegistro;
        this.descripcion = descripcion;
        this.origen = origen;
        this.cuCodAsocia = cuCodAsocia;
        this.caCodContiene = caCodContiene;
        this.pDniIngresa = pDniIngresa;
        this.oEs = oEs;
    }
    public Objeto(String codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
    }
   

    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public String getTipoExtracion() {
        return tipoExtracion;
    }
    public Integer getAlto() {
        return alto;
    }
    public Integer getLargo() {
        return largo;
    }
    public Integer getExpesor() {
        return expesor;
    }
    public Integer getPeso() {
        return peso;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getOrigen() {
        return origen;
    }
    public String getCuCodAsocia() {
        return cuCodAsocia;
    }
    public String getCaCodContiene() {
        return caCodContiene;
    }
    public Integer getPDniIngresa() {
        return pDniIngresa;
    }
    public Character getOEs() {
        return oEs;
    }
    @Override
    public String toString(){
        return "Objeto{" + "codigo=" + codigo + ", nombre=" + nombre + ", tipoExtracion=" + tipoExtracion + ", alto=" + alto + ", largo=" + largo + ", expesor=" + expesor + ", peso=" + peso + ", cantidad=" + cantidad + ", fechaRegistro=" + fechaRegistro + ", descripcion=" + descripcion + ", origen=" + origen + ", cuCodAsocia=" + cuCodAsocia + ", caCodContiene=" + caCodContiene + ", pDniIngresa=" + pDniIngresa + ", oEs=" + oEs + '}';
    }
}
