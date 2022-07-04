/*
  Autor: Patricio Balboa
  Rut: 17.592.019-6
 */

package cl.codelab.certamen2balboa;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BDBalboa extends SQLiteOpenHelper {



    private static final int VERSION_BASEDATOS = 1;
    private static final String NOMBRE_BASEDATOS="PlantasMedicinales.sqlite";

    public BDBalboa(Context context){
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }




    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE CientificoBalboa" + "(_rut INT PRIMARY KEY, nombre TEXT, apellido TEXT, sexo TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE PlantasBalboa" +  "(_idplanta INT PRIMARY KEY, nombre_p TEXT, nombre_cientifico_planta TEXT, foto BLOB, uso_p TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE RecoleccionBalboa" + "(_id INT PRIMARY KEY, fecha TEXT, idplanta INT, rut INT, comment TEXT, foto_lugar BLOB, localizacion TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CientificoBalboa");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PlantasBalboa");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS RecoleccionBalboa");
    }



    // Manejadores Cientifico
    public boolean addCientifico(int rut, String nombre, String apellido, String sexo){
        boolean sw1 = true;
        SQLiteDatabase db= getWritableDatabase();

        if(db!=null){
            ContentValues valores = new ContentValues();
            valores.put("_rut",rut);
            valores.put("nombre",nombre);
            valores.put("apellido",apellido);
            valores.put("sexo",sexo);

            try{
                db.insert("CientificoBalboa",null,valores);
                db.close();
            }catch (Exception e){
                db.close();
                sw1=false;
            }

        }else {
            sw1=false;

            this.onCreate(db);
        }

        return sw1;
    }
    public ArrayList<Cientifico> getListCientifico(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM CientificoBalboa ORDER by nombre DESC",null);
        ArrayList <Cientifico> lista = new ArrayList<Cientifico>();
        try{

            while(c.moveToNext()) {
                Cientifico datos = new Cientifico(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
                lista.add(datos);

            }
            c.close();
            return lista;
        }catch (Exception e){
            return null;
        }
    }
    public Cursor getCursorCientifico(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM CientificoBalboa ORDER by nombre DESC",null);

        try{

            if(c.moveToFirst()){

                return c;

            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }
    public Cientifico getCientifico(int rut){
        SQLiteDatabase db = getReadableDatabase();
        Cientifico datos= new Cientifico();
        try{
            Cursor c = db.rawQuery("SELECT * FROM CientificoBalboa WHERE _rut='"+rut+"'",null);
            if(c.moveToFirst()){

                datos = new Cientifico(c.getInt(0),c.getString(1),c.getString(2), c.getString(3));

                c.close();
                return datos;
            }else{
                this.close();
                c.close();
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }
    public boolean updateCientifico(int rut, String nombre, String apellido, String sexo){
        boolean sw1 = true;
        SQLiteDatabase db= getWritableDatabase();

        if(db!=null){
            ContentValues valores = new ContentValues();

            valores.put("nombre",nombre);
            valores.put("apellido",apellido);
            valores.put("sexo",sexo);

            try{
                String[] args= new String[]{String.valueOf(rut)};
                db.update("CientificoBalboa",valores,"_rut=?",args);
                db.close();
            }catch (Exception e){
                db.close();
                sw1=false;
            }

        }else {
            sw1=false;
        }

        return sw1;
    }
    public boolean  deleteCientifico(int rut){

        boolean sw1 = true;
        SQLiteDatabase db= getWritableDatabase();

        if(db!=null){


            try{
                String[] args= new String[]{String.valueOf(rut)};
                db.delete("CientificoBalboa","_rut=?",args);
                db.close();
            }catch (Exception e){
                db.close();
                sw1=false;
            }

        }else {
            sw1=false;
        }

        return sw1;
    }

    // Manejadores Plantas
    public boolean addPlantas(int idplanta, String nombre_p, String nombre_cientifico_planta,byte[] foto, String uso_p){
        boolean sw1 = true;
        SQLiteDatabase db= getWritableDatabase();

        if(db!=null){
            ContentValues valores = new ContentValues();
            valores.put("_idplanta", idplanta);
            valores.put("nombre_p", nombre_p);
            valores.put("nombre_cientifico_planta",nombre_cientifico_planta);
            valores.put("foto",foto);
            valores.put("uso_p",uso_p);

            try{
                db.insert("PlantasBalboa",null,valores);
                db.close();
            }catch (Exception e){
                db.close();
                sw1=false;
            }

        }else {
            onCreate(db);
        }

        return sw1;
    }
    public ArrayList<Plantas> getListPlantas(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM PlantasBalboa ORDER by nombre_p DESC",null);
        ArrayList <Plantas> lista = new ArrayList<Plantas>();
        try{

            while(c.moveToNext()) {
                Plantas datos = new Plantas(c.getInt(0),c.getString(1),c.getString(2), c.getBlob(3),c.getString(4));
                lista.add(datos);

            }
            c.close();
            return lista;
        }catch (Exception e){
            return null;
        }
    }
    public Plantas getPlantas(int id){
        SQLiteDatabase db = getReadableDatabase();
        Plantas datos= new Plantas();
        try{
            Cursor c = db.rawQuery("SELECT * FROM PlantasBalboa WHERE _idplanta="+id+"",null);
            if(c.moveToFirst()){
                //byte[] bytes = c.getBlob(c.getColumnIndex("IMAGEN"));
                datos = new Plantas(c.getInt(0),c.getString(1),c.getString(2),c.getBlob(3),c.getString(4));

                c.close();
                return datos;
            }else{

                c.close();
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }
    public boolean updatePlantas(int idplanta, String nombre_p, String nombre_cientifico_planta,byte[] foto, String uso_p){
        boolean sw1 = true;
        SQLiteDatabase db= getWritableDatabase();

        if(db!=null){
            ContentValues valores = new ContentValues();
            valores.put("_idplanta", idplanta);
            valores.put("nombre_p", nombre_p);
            valores.put("nombre_cientifico_planta",nombre_cientifico_planta);
            valores.put("foto",foto);
            valores.put("uso_p",uso_p);

            try{
                String[] args= new String[]{String.valueOf(idplanta)};
                db.update("PlantasBalboa",valores,"_idplanta=?",args);
                db.close();
            }catch (Exception e){
                db.close();
                sw1=false;
            }

        }else {
            sw1=false;
        }

        return sw1;
    }
    public boolean deletePlantas(int idplanta){

        boolean sw1 = true;
        SQLiteDatabase db= getWritableDatabase();

        if(db!=null){


            try {

                String[] args= new String[]{String.valueOf(idplanta)};
                db.delete("PlantasBalboa","_idplanta=?",args);
                db.close();
            }catch (Exception e){
                db.close();
                sw1=false;
            }

        }else {
            sw1=false;
        }

        return sw1;
    }

    // Manejadores Recoleccion
    public boolean addRecoleccion(int id,String fecha,int idplanta, int rut, byte[] foto_lugar, String comentario, String localizacion){
        boolean sw1 = true;
        SQLiteDatabase db= getWritableDatabase();

        if(db!=null){
            ContentValues valores = new ContentValues();
            valores.put("_id", id);
            valores.put("fecha", fecha);
            valores.put("idplanta", idplanta);
            valores.put("rut",rut);
            valores.put("foto_lugar",foto_lugar);
            valores.put("comment",comentario);
            valores.put("localizacion",localizacion);

                try {
                    db.insert("RecoleccionBalboa", null, valores);
                    db.close();
                } catch (Exception e) {
                    db.close();
                    sw1 = false;
                }


        }else {
            onCreate(db);
        }

        return sw1;
    }
    public ArrayList<Recoleccion> getListRecoleccion(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM RecoleccionBalboa ORDER by fecha DESC",null);
        ArrayList <Recoleccion> lista = new ArrayList<Recoleccion>();
        try{

            while(c.moveToNext()) {
                Recoleccion datos = new Recoleccion(c.getInt(0), c.getString(1),c.getInt(2),c.getInt(3), c.getString(4),c.getBlob(5), c.getString(6));
                lista.add(datos);

            }
            c.close();
            return lista;
        }catch (Exception e){
            return null;
        }
    }
    public Recoleccion getRecoleccion(int id){
        SQLiteDatabase db = getReadableDatabase();
        Recoleccion datos= new Recoleccion();
        try{

            Cursor c = db.rawQuery("SELECT * FROM RecoleccionBalboa WHERE _id='"+id+"'",null);
            if(c.moveToFirst()){
                //byte[] bytes = c.getBlob(c.getColumnIndex("IMAGEN"));
                datos = new Recoleccion(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3),c.getString(4),c.getBlob(5), c.getString(6) );

                c.close();
                return datos;
            }else{

                c.close();
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }
    public boolean updateRecoleccion(int id,String fecha,int idplanta, int rut, byte[] foto_lugar, String comentario, String localizacion){
        boolean sw1 = true;
        SQLiteDatabase db= getWritableDatabase();

        if(db!=null){
            ContentValues valores = new ContentValues();

            valores.put("fecha", fecha);
            valores.put("idplanta", idplanta);
            valores.put("rut",rut);
            valores.put("foto_lugar",foto_lugar);
            valores.put("comment",comentario);
            valores.put("localizacion",localizacion);
            try{
                String[] args= new String[]{String.valueOf(id)};
                db.update("RecoleccionBalboa",valores,"_id=?",args);
                db.close();
            }catch (Exception e){
                db.close();
                sw1=false;
            }

        }else {
            sw1=false;
        }

        return sw1;
    }
    public boolean deleteRecoleccion(int id){

        boolean sw1 = true;
        SQLiteDatabase db= getWritableDatabase();

        if(db!=null){


            try{
                String[] args= new String[]{String.valueOf(id)};
                db.delete("RecoleccionBalboa","_id=?",args);
                db.close();
            }catch (Exception e){
                db.close();
                sw1=false;
            }

        }else {
            sw1=false;
        }

        return sw1;
    }

}
