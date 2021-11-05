package modelDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import model.DbHelper;
import model.Usuarios;

public class UsuariosDAO {
    DbHelper dbHelper;
    public UsuariosDAO(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public int addUsuario(Usuarios usu) {
        int newRowId = 0;
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nomeUsuario", usu.getNomeUsuario());
            values.put("emailUsuario", usu.getEmailUsuario());
            values.put("senhaUsuario", usu.getSenhaUsuario());

            newRowId = (int) db.insert(DbHelper.TABLE_NAME_USUARIOS, null, values);

        }catch (Exception e){
            e.printStackTrace();
        }
        return newRowId;
    }

    public ArrayList<Usuarios> login(Usuarios usuario) {
        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        try{

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM usuarios WHERE 1 = 1 AND emailUsuario = ? AND senhaUsuario = ?";

            Cursor cursor =  db.rawQuery(sql, new String[]{usuario.getEmailUsuario(), usuario.getSenhaUsuario()}, null);

            while (cursor.moveToNext()){
                int indiceIdUsuario = cursor.getColumnIndex("idUsuario");
                int indiceNomeUsuario = cursor.getColumnIndex("nomeUsuario");
                int indiceEmailUsuario = cursor.getColumnIndex("emailUsuario");
                int indiceSenhaUsuario = cursor.getColumnIndex("senhaUsuario");

                Usuarios usu = new Usuarios();
                usu.setIdUsuario(Integer.parseInt(cursor.getString(indiceIdUsuario)));
                usu.setNomeUsuario(cursor.getString(indiceNomeUsuario));
                usu.setEmailUsuario(cursor.getString(indiceEmailUsuario));
                usu.setSenhaUsuario(cursor.getString(indiceSenhaUsuario));

                listaUsuarios.add(usu);

            }

            cursor.close();
            db.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return listaUsuarios;
    }
}
