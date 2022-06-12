/*
  Autor: Patricio Balboa
  Rut: 17.592.019-6
 */

package cl.codelab.certamen2balboa;

public class Plantas {

    private int id;
    private String nombre_p;
    private String nombre_cientifico_planta;
    private byte[] foto;
    private String uso;

    public Plantas(int id, String nombre_p, String nombre_cientifico_planta, String uso) {
        this.id = id;
        this.nombre_p = nombre_p;
        this.nombre_cientifico_planta = nombre_cientifico_planta;
        this.uso = uso;
    }

    public Plantas() {
    }

    public Plantas(int id, String nombre_p, String nombre_cientifico_planta, byte[] foto, String uso) {
        this.id = id;
        this.nombre_p = nombre_p;
        this.nombre_cientifico_planta = nombre_cientifico_planta;
        this.foto = foto;
        this.uso = uso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_p() {
        return nombre_p;
    }

    public void setNombre_p(String nombre_p) {
        this.nombre_p = nombre_p;
    }

    public String getNombre_cientifico_planta() {
        return nombre_cientifico_planta;
    }

    public void setNombre_cientifico_planta(String nombre_cientifico_planta) {
        this.nombre_cientifico_planta = nombre_cientifico_planta;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String toString(){
        return nombre_p;
    }
}
