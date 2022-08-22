package modelDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listpad.MainActivity;

import java.util.ArrayList;

import model.Categoria;
import model.DbHelper;
import model.Lista;

public class ListaDAO {
    DbHelper dbHelper;

    public ListaDAO(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public ListaDAO() {

    }

    public int addLista(Lista lista) {
        int resultado = 0;
        try{

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("usuarios_idUsuario", lista.getIdUsuario());
            values.put("categoria_idCategoria", lista.getCategoria().getIdCategoria());
            values.put("descricaoLista", lista.getDescricaoLista() );
            values.put("flagUrgencia", lista.getFlagUrgencia());
            resultado = (int) db.insert(DbHelper.TABLE_NAME_LISTA, null, values);

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    public ArrayList<Lista> listarlistas(){
        ArrayList<Lista> lista = new ArrayList<>();
        try{
            SQLiteDatabase db = dbHelper.getReadableDatabase();

//            String sql = "SELECT DISTINCT A.idLista, A.descricaoLista, A.flagUrgencia, B.idCategoriaLista, B.descricaoCategoria FROM "+DbHelper.TABLE_NAME_LISTA+" A INNER JOIN "+DbHelper.TABLE_NAME_CATEGORIA+" B ON (A.categoria_idCategoria = B.idCategoriaLista) WHERE 1 = 1 AND A.usuarios_idUsuario= ? ORDER BY A.flagUrgencia  DESC";
//            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(MainActivity.idUsuario)}, null);
            String sql = "SELECT DISTINCT A.idLista, A.descricaoLista, A.flagUrgencia, B.idCategoriaLista, B.descricaoCategoria FROM "+DbHelper.TABLE_NAME_LISTA+" A INNER JOIN "+DbHelper.TABLE_NAME_CATEGORIA+" B ON (A.categoria_idCategoria = B.idCategoriaLista) WHERE 1 = 1 ORDER BY A.flagUrgencia  DESC";
            Cursor cursor = db.rawQuery(sql, null);

            if(cursor.getCount() > 0){

                while (cursor.moveToNext()){
                    int indiceIdLista = cursor.getColumnIndex("idLista");
                    int indiceDescricaoLista = cursor.getColumnIndex("descricaoLista");
                    int indiceFlagUrgencia = cursor.getColumnIndex("flagUrgencia");
                    int indiceIdCategoria = cursor.getColumnIndex("idCategoriaLista");
                    int indiceDescricaoCategoria = cursor.getColumnIndex("descricaoCategoria");

                    Categoria cat = new Categoria();
                    cat.setIdCategoria(cursor.getInt(indiceIdCategoria));
                    cat.setDescricaoCategoria(cursor.getString(indiceDescricaoCategoria));

                    Lista list = new Lista();
                    list.setIdLista(cursor.getInt(indiceIdLista));
                    list.setDescricaoLista(cursor.getString(indiceDescricaoLista));
                    list.setCategoria(cat);
                    list.setFlagUrgencia(cursor.getInt(indiceFlagUrgencia));

                    lista.add(list);

                }

            }

            cursor.close();
            db.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return  lista;
    }

    public ArrayList<Lista> populaLista(Lista lista) {
        ArrayList<Lista> populaLlista = new ArrayList<>();
        try{
            SQLiteDatabase db = dbHelper.getReadableDatabase();

//            String sql = "SELECT DISTINCT A.idLista, A.descricaoLista, A.flagUrgencia, B.idCategoriaLista, B.descricaoCategoria FROM "+DbHelper.TABLE_NAME_LISTA+" A INNER JOIN "+DbHelper.TABLE_NAME_CATEGORIA+" B ON (A.categoria_idCategoria = B.idCategoriaLista) WHERE 1 = 1 AND A.usuarios_idUsuario= ? AND A.idLista = ? ORDER BY A.flagUrgencia, A.idLista DESC";
//            Cursor cursor = db.rawQuery(sql, new String[]{ String.valueOf( MainActivity.idUsuario ), String.valueOf( lista.getIdLista() )}, null);

            String sql = "SELECT DISTINCT A.idLista, A.descricaoLista, A.flagUrgencia, B.idCategoriaLista, B.descricaoCategoria FROM "+DbHelper.TABLE_NAME_LISTA+" A INNER JOIN "+DbHelper.TABLE_NAME_CATEGORIA+" B ON (A.categoria_idCategoria = B.idCategoriaLista) WHERE 1 = 1 AND A.idLista = ? ORDER BY A.flagUrgencia, A.idLista DESC";
            Cursor cursor = db.rawQuery(sql, new String[]{ String.valueOf( lista.getIdLista() )}, null);

            if(cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    int indiceIdLista = cursor.getColumnIndex("idLista");
                    int indiceDescricaoLista = cursor.getColumnIndex("descricaoLista");
                    int indiceIdCategoria = cursor.getColumnIndex("idCategoriaLista");
                    int indiceDescricaoCategoria = cursor.getColumnIndex("descricaoCategoria");
                    int indiceFlagUrgencia = cursor.getColumnIndex("flagUrgencia");

                    Categoria cat = new Categoria();
                    cat.setIdCategoria(cursor.getInt(indiceIdCategoria));
                    cat.setDescricaoCategoria(cursor.getString(indiceDescricaoCategoria));

                    Lista l = new Lista();
                    l.setIdLista(cursor.getInt(indiceIdLista));
                    l.setDescricaoLista(cursor.getString(indiceDescricaoLista));
                    l.setCategoria(cat);
                    l.setFlagUrgencia(cursor.getInt(indiceFlagUrgencia));

                    populaLlista.add(l);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return populaLlista;
    }

    public int updateLista(Lista lista) {
        int retultado = 0;

        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("idLista", lista.getIdLista());
            values.put("usuarios_idUsuario", lista.getIdUsuario());
            values.put("descricaoLista", lista.getDescricaoLista());
            values.put("flagUrgencia", lista.getFlagUrgencia());

            retultado = (int) db.update(DbHelper.TABLE_NAME_LISTA, values, "usuarios_idUsuario = ? AND idLista = ? ", new String[]{String.valueOf(lista.getIdUsuario()), String.valueOf(lista.getIdLista())});
        }catch (Exception e){
            e.printStackTrace();
        }

        return retultado;
    }

    public int excluirLista(Lista l) {
        int retorno = 0;
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            retorno = db.delete(DbHelper.TABLE_NAME_LISTA, "idLista = ? AND usuarios_idUsuario = ? ", new String[]{String.valueOf(l.getIdLista()), String.valueOf(MainActivity.idUsuario)});

        }catch (Exception e){
            e.printStackTrace();
        }

        return retorno;
    }
}
