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
    private final String CREATE_TABLE_CLIENTES = "CREATE TABLE IF NOT EXISTS clientes ( idclientes INTEGER PRIMARY KEY AUTOINCREMENT, nomeCliente VARCHAR, sobreNomeCliente VARCHAR, emailCliente VARCHAR, telefoneCliente VARCHAR, bicicletaCliente VARCHAR)";
    private final String CREATE_TABLE_REVISAO = "CREATE TABLE IF NOT EXISTS revisao (idrevisao INTEGER AUTOINCREMENT, usuarios_idusuario INT NOT NULL, clientes_idclientes INT NOT NULL, descricaoRevisao VARCHAR, flagUrgencia INT NULL,   categoriaRevisao VARCHAR, PRIMARY KEY (idrevisao, usuarios_idusuario, clientes_idclientes), FOREIGN KEY (usuarios_idusuario) REFERENCES usuarios (idusuario) ON DELETE NO ACTION ON UPDATE NO ACTION, FOREIGN KEY (clientes_idclientes) REFERENCES clientes (idclientes) ON DELETE NO ACTION ON UPDATE NO ACTION)";
    private final String CREATE_TABLE_ITEM_REVISAO = "CREATE TABLE IF NOT EXISTS itemRevisao (idItemRevisao INTEGER, revisao_idrevisao INTEGER NOT NULL, descricaoItemrevisao VARCHAR, PRIMARY KEY (iditemRevisao, revisao_idrevisao), FOREIGN KEY (revisao_idrevisao) REFERENCES revisao (idrevisao) ON DELETE NO ACTION ON UPDATE NO ACTION)";
    private final String CREATE_TABLE_CATEGORIA_REVISAO = "CREATE TABLE IF NOT EXISTS categoriaRevisao (idcategoriaRevisao INTEGER, revisao_idrevisao INTEGER, descricaoCategoria VARCHAR, PRIMARY KEY (idcategoriaRevisao, revisao_idrevisao), FOREIGN KEY (revisao_idrevisao) REFERENCES revisao (idrevisao))";

    public static String TABLE_NAME_USUARIOS = "usuarios";
    public static String TABLE_NAME_CLIENTES = "clientes";
    public static String TABLE_NAME_REVISAO = "revisao";
    public static String TABLE_NAME_ITEM_REVISAO = "itemRevisao";
    public static String TABLE_NAME_CATEGORIA_REVISAO  = "categoriaRevisao";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            Log.i("Log # ", "Entrou no onCreate");
            sqLiteDatabase.execSQL(CREATE_TABLE_USUARIOS);
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
