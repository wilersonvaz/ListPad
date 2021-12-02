package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "listPad";
    public static final int DATA_BASE_VERSION = 1;
    private final String CREATE_TABLE_USUARIOS = "CREATE TABLE IF NOT EXISTS usuarios (idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,  nomeUsuario VARCHAR, emailUsuario VARCHAR, senhaUsuario VARCHAR)";

    private final String CREATE_TABLE_CATEGORIA_LISTA = "CREATE TABLE IF NOT EXISTS categoriaLista (     INTEGER PRIMARY KEY AUTOINCREMENT,usuarios_idUsuario int,descricaoCategoria VARCHAR, FOREIGN KEY (usuarios_idUsuario) REFERENCES categoriaLista (idUsuario) ON DELETE CASCADE ON UPDATE CASCADE)";
    private final String CREATE_TABLE_LISTA = "CREATE TABLE IF NOT EXISTS lista (idLista INTEGER PRIMARY KEY AUTOINCREMENT,usuarios_idUsuario INTEGER ,categoria_idCategoria INTEGER ,descricaoLista VARCHAR, flagUrgencia INT DEFAULT 0,FOREIGN KEY (usuarios_idUsuario)  REFERENCES usuarios (idUsuario)  ON DELETE CASCADE  ON UPDATE CASCADE,FOREIGN KEY (categoria_idCategoria)  REFERENCES categoriaLista(idCategoriaLista)  ON DELETE CASCADE ON UPDATE CASCADE)";
    private final String CREATE_TABLE_ITENS_LISTA = "CREATE TABLE IF NOT EXISTS itensLista (idItensLista INTEGER PRIMARY KEY AUTOINCREMENT,lista_idLista INT NOT NULL,itemLista VARCHAR,flagFinalizado INT DEFAULT 0,FOREIGN KEY (lista_idLista)  REFERENCES lista (idLista)  ON DELETE CASCADE ON UPDATE CASCADE)";

    public static String TABLE_NAME_USUARIOS = "usuarios";
    public static String TABLE_NAME_LISTA = "lista";
    public static String TABLE_NAME_CATEGORIA  = "categoriaLista";
    public static String TABLE_NAME_ITEM_LISTA  = "itensLista";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            Log.i("Log # ", "Entrou no onCreate");
            sqLiteDatabase.execSQL(CREATE_TABLE_USUARIOS);
            sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORIA_LISTA);
            sqLiteDatabase.execSQL(CREATE_TABLE_LISTA);
            sqLiteDatabase.execSQL(CREATE_TABLE_ITENS_LISTA);


//            sqLiteDatabase.execSQL(CREATE_TABLE_CLIENTES);
//            sqLiteDatabase.execSQL(CREATE_TABLE_USUARIOS);
//            sqLiteDatabase.execSQL(CREATE_TABLE_ITEM_REVISAO);
//            sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORIA_REVISAO);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            Log.i("Log # ", "Entrou no onUpdate");
            String sql = "DROP TABLE usuarios";
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
