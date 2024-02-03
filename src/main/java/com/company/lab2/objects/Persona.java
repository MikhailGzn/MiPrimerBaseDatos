package com.company.lab2.objects;

public class Persona {
        private Integer dni;
        private String nombre;
        private String apellido;        
        private String email;
        private String telefono;
        private Integer cantObjetos;
        public Persona(Integer dni, String nombre, String apellido, String email, String telefono ) {
            this.dni=dni;
            this.nombre = nombre;
            this.apellido = apellido;            
            this.email = email;
            this.telefono = telefono;
        }
        public Persona(String nombre, String apellido, Integer cantObjetos) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.cantObjetos = cantObjetos;
        }

        public Integer getDni() {
            return dni;
        }       
        public String getNombre() {
            return nombre;
        }
        public String getApellido() {
            return apellido;
        }
        public String getEmail() {
            return email;
        }
        public String getTelefono() {
            return telefono;
        }
        public Integer getCantObjetos() {
            return cantObjetos;
        }
}
