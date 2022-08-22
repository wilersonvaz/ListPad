package modelDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listpad.MainActivity;

import java.util.ArrayList;

import model.Categoria;
import model.DbHelper;

public class CategoriaDAO {
    DbHelper dbHelper;

    public CategoriaDAO(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public CategoriaDAO() {

    }

    public int addCategoria(Categoria cat){
        int resultado = 0;

        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("usuarios_idUsuario", cat.getUsuarios().getIdUsuario());
            values.put("descricaoCategoria", cat.getDescricaoCategoria());

            resultado = (int) db.insert(DbHelper.TABLE_NAME_CATEGORIA, null, values);

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultado;
    }

    public ArrayList<Categoria> listarCategorias() {
        ArrayList<Categoria> listaCegorias = new ArrayList<>();
        try{
            SQLiteDatabase db = dbHelper.getReadableDatabase();

//            String sql= "SELECT A.* FROM "+DbHelper.TABLE_NAME_CATEGORIA+" A WHERE 1 = 1 AND A.usuarios_idUsuario = ?";
//            Cursor cursor = db.rawQuery(sql, new String[]{ String.valueOf(MainActivity.idUsuario) },null);

            String sql= "SELECT A.* FROM "+DbHelper.TABLE_NAME_CATEGORIA+" A WHERE 1 = 1";
            Cursor cursor = db.rawQuery(sql, null);

            if(cursor.getCount()> 0){
                while (cursor.moveToNext()){
                    int indiceIdCategoria = cursor.getColumnIndex("idCategoriaLista");
                    int indiceDescricaoCategoria = cursor.getColumnIndex("descricaoCategoria");

                    Log.i("Log # ", cursor.getString(indiceDescricaoCategoria));
                    Categoria cat = new Categoria();
                    cat.setIdCategoria(cursor.getInt(indiceIdCategoria));
                    cat.setDescricaoCategoria(cursor.getString(indiceDescricaoCategoria));

                    listaCegorias.add(cat);

                }
            }

            cursor.close();
            db.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return listaCegorias;
    }

    public ArrayList<Categoria> populaCategoria(Categoria cat) {
        ArrayList<Categoria> lista = new ArrayList<>();
        try{
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String sql = "SELECT * FROM "+DbHelper.TABLE_NAME_CATEGORIA+" WHERE 1 = 1 AND idCategoriaLista = ?";
            ContentValues values = new ContentValues();
            values.put("idCategoriaLista", cat.getIdCategoria());

            Cursor cursor = db.rawQuery(sql, new String[]{ String.valueOf(cat.getIdCategoria()) },null);

            if(cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    int indIdCategoria = cursor.getColumnIndex("idCategoriaLista");
                    int indDescricaoCategoria= cursor.getColumnIndex("descricaoCategoria");

                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(cursor.getInt(indIdCategoria));
                    categoria.setDescricaoCategoria(cursor.getString(indDescricaoCategoria));
                    lista.add(categoria);

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    public int updateCategoria(Categoria cat) {
        int retorno = 0;
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("descricaoCategoria", cat.getDescricaoCategoria());
//            values.put("idCategoriaLista", cat.getIdCategoria());

            retorno = db.update(DbHelper.TABLE_NAME_CATEGORIA, values, "idCategoriaLista = ? ", new String[]{String.valueOf( cat.getIdCategoria() ) });

        }catch (Exception e){
            e.printStackTrace();
        }
        return retorno;
    }

    public int excluirCategoria(Categoria cat) {
        int retorno = 0;
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            retorno =  db.delete(DbHelper.TABLE_NAME_CATEGORIA, "idCategoriaLista = ? AND usuarios_idUsuario = ?  ", new String[]{ String.valueOf( cat.getIdCategoria() ), String.valueOf(MainActivity.idUsuario)});

        }catch (Exception e){
            e.printStackTrace();
        }
        return retorno;
    }

    public int countCategorias() {
        int retorno = 0;
        try{
//            String sql= "SELECT A.* FROM "+DbHelper.TABLE_NAME_CATEGORIA+" A WHERE 1 = 1 AND A.usuarios_idUsuario = ?";
//            Cursor cursor = db.rawQuery(sql, new String[]{ String.valueOf(MainActivity.idUsuario) },null);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql= "SELECT A.idCategoriaLista FROM "+DbHelper.TABLE_NAME_CATEGORIA+" A";

            Cursor cursor = db.rawQuery(sql, null);
            retorno = cursor.getCount();

            cursor.close();
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return retorno;
    }
}
