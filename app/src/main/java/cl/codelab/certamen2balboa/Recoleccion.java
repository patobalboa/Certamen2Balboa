package cl.codelab.certamen2balboa;

public class Recoleccion {

    private int id;
    private String fecha;
    private int cod_planta;
    private int cod_cientifico;
    private String comentario;
    private byte[] foto_lugar;
    private String localizacion;

    public Recoleccion() {
    }

    public Recoleccion(int id, String fecha, int cod_planta, int cod_cientifico, String comentario, byte[] foto_lugar, String localizacion) {
        this.id = id;
        this.fecha = fecha;
        this.cod_planta = cod_planta;
        this.cod_cientifico = cod_cientifico;
        this.comentario = comentario;
        this.foto_lugar = foto_lugar;
        this.localizacion = localizacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCod_planta() {
        return cod_planta;
    }

    public void setCod_planta(int cod_planta) {
        this.cod_planta = cod_planta;
    }

    public int getCod_cientifico() {
        return cod_cientifico;
    }

    public void setCod_cientifico(int cod_cientifico) {
        this.cod_cientifico = cod_cientifico;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public byte[] getFoto_lugar() {
        return foto_lugar;
    }

    public void setFoto_lugar(byte[] foto_lugar) {
        this.foto_lugar = foto_lugar;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
}
