package badgears.storecheck.Dao;

import android.content.Context;
import android.util.Log;

/**
 * Created by Lucas on 23/09/2016.
 */
public class dbUtil {
    private dbHelper dHelper;

    private static final String[] createBDSQL = new String[] {

            "CREATE TABLE clientes ("+
                    "Id INTEGER  PRIMARY KEY AUTOINCREMENT,"+
                    "Nome        TEXT NOT NULL,"+
                    "Datacad     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"+
                    "CPFCNPJ     TEXT,"+
                    "Telefone    TEXT,"+
                    "Cidade      TEXT NOT NULL);",

            "CREATE TABLE cotacao ("+
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "Nome       TEXT,"+
                    "Data       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"+
                    "IDCliente  INTEGER NOT NULL,"+
                    "FOREIGN KEY (IDCliente) REFERENCES clientes(Id));"
                    ,



            "CREATE TABLE produtos ("+
                    "Id INTEGER PRIMARY KEY,"+
                    "Descricao  TEXT NOT NULL,"+
                    "Categoria     TEXT NOT NULL,"+
                    "Tipo     TEXT NOT NULL"+
                    ");",

            "CREATE TABLE produtoscotacao ("+
                    "Id INTEGER PRIMARY KEY,"+
                    "IdCotacao  INTEGER NOT NULL,"+
                    "Idproduto  INTEGER NOT NULL,"+
                    "Preco1     REAL NOT NULL,"+
                    "Preco2     REAL NOT NULL,"+
                    "Cotar      Integer NOT NULL,"+
                    "FOREIGN KEY (IdCotacao) REFERENCES cotacao(Id));"
            ,


    };

    public dbUtil(Context contexto) {
        dHelper = new dbHelper(contexto, "storecheck", 1, createBDSQL, null);
        Log.i("teste", "fuichamado");
    }

    public void verificaBD() {

        try {
            dHelper.getWritableDatabase();

        } finally {
            dHelper.close();

        }

    }
}
