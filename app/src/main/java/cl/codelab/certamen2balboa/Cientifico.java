/*
  Autor: Patricio Balboa
  Rut: 17.592.019-6
 */

package cl.codelab.certamen2balboa;

public class Cientifico {
    private int rut;
    private String nombre;
    private String apellido;
    private String sexo;


    public Cientifico() {
    }

    public Cientifico(int rut, String nombre, String apellido, String sexo) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


    @Override
    public String toString(){
        return nombre +" "+ apellido;
    }
}
