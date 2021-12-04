package modelDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listpad.MainActivity;

import java.util.ArrayList;

import model.DbHelper;
import model.ItensLista;
import model.Lista;

public class ItensListaDAO {
    DbHelper dbHelper;


    public ItensListaDAO(DbHelper dbHelper) {
        this.dbHelper= dbHelper;
    }

    public ArrayList<ItensLista> listaItens(ItensLista listaItens) {
        ArrayList<ItensLista> itensLista = new ArrayList<>();
        try{
            String sql = "SELECT A.descricaoLista, A.usuarios_idUsuario, B.* from "+DbHelper.TABLE_NAME_LISTA+" A left join "+DbHelper.TABLE_NAME_ITEM_LISTA+" B ON (A.idLista = B.lista_idLista) WHERE 1 = 1 AND A.idLista = ? AND A.usuarios_idUsuario = ?";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(listaItens.getIdItem()), String.valueOf( MainActivity.idUsuario )});

            if(cursor.getCount()>0){

                while (cursor.moveToNext()){

                    int indiceIdItensLista = cursor.getColumnIndex("idItensLista");
                    int indiceIdLista = cursor.getColumnIndex("lista_idLista");
                    int indiceDescricaoLista = cursor.getColumnIndex("descricaoLista");
                    int indiceItemLista = cursor.getColumnIndex("itemLista");
                    int indiceFlagFinalizado = cursor.getColumnIndex("flagFinalizado");

                    Lista lista = new Lista();
                    lista.setIdLista(cursor.getInt(indiceIdLista));
                    lista.setDescricaoLista(cursor.getString(indiceDescricaoLista));

                    ItensLista il = new ItensLista();
                    il.setIdItem(cursor.getInt(indiceIdItensLista));
                    il.setLista(lista);
                    il.setItemLista(cursor.getString(indiceItemLista));
                    il.setFlagFinalizado(cursor.getInt(indiceFlagFinalizado));

                    itensLista.add(il);

                }
            }else{
                Log.i("Log # ", "Não encontrou uma lista");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return itensLista;
    }

    public int adicionaItem(ItensLista itensLista) {
        int retorno =0;
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("lista_idLista", itensLista.getLista().getIdLista());
            values.put("itemLista", itensLista.getItemLista());

            retorno = (int) db.insert(DbHelper.TABLE_NAME_ITEM_LISTA, null, values);

        }catch (Exception e){
            e.printStackTrace();
        }
        return retorno;
    }

    public int updateItemLista(ItensLista itensLista) {
        int retorno = 0;
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("itemLista", itensLista.getItemLista());

            retorno = db.update(DbHelper.TABLE_NAME_ITEM_LISTA, values, "idItensLista = ? AND lista_idLista = ?", new String[]{String.valueOf(itensLista.getIdItem()), String.valueOf(itensLista.getLista().getIdLista())});

        }catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
    }

    public ArrayList<ItensLista>  populaItensLista(ItensLista itensLista) {
        ArrayList<ItensLista> iListas = new ArrayList<>();
        try{
            String sql = "SELECT * FROM "+DbHelper.TABLE_NAME_ITEM_LISTA+" A INNER JOIN "+DbHelper.TABLE_NAME_LISTA+" B ON (A.lista_idLista = B.idLista) INNER JOIN "+DbHelper.TABLE_NAME_USUARIOS+" C ON (B.usuarios_idUsuario = C.idUsuario) WHERE 1 = 1 AND A.idItensLista = ? AND B.idLista = ? AND C.idUsuario = ?";

            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(itensLista.getIdItem()), String.valueOf(itensLista.getLista().getIdLista()), String.valueOf(MainActivity.idUsuario) }, null);

            if(cursor.getCount() > 0){

                while(cursor.moveToNext()){
                    int indiceIdItem = cursor.getColumnIndex("idItensLista");
                    int indiceItemLista = cursor.getColumnIndex("itemLista");

                    ItensLista it = new ItensLista();
                    it.setIdItem(cursor.getInt(indiceIdItem));
                    it.setItemLista(cursor.getString(indiceItemLista));

                    iListas.add(it);

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return iListas;
    }

    public int finalizaItem(ItensLista itLista) {
        int retorno = 0;
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("flagFinalizado", itLista.getFlagFinalizado());

            retorno = db.update(DbHelper.TABLE_NAME_ITEM_LISTA, values, "idItensLista = ? ", new String[]{String.valueOf(itLista.getIdItem())});

        }catch(Exception e){
            e.printStackTrace();
        }
        return  retorno;
    }

    public int excluirItem(ItensLista itLista) {
        int retorno = 0;
        try{
          SQLiteDatabase db = dbHelper.getWritableDatabase();

          retorno = db.delete(DbHelper.TABLE_NAME_ITEM_LISTA, "idItensLista = ? AND lista_idLista = ?", new  String[]{String.valueOf(itLista.getIdItem()), String.valueOf( itLista.getLista().getIdLista() )});
        }catch (Exception e){
            e.printStackTrace();
        }
        return retorno;
    }
}
