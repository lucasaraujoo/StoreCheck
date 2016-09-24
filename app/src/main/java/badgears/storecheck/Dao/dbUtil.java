package badgears.storecheck.Dao;

import android.content.Context;

/**
 * Created by Lucas on 23/09/2016.
 */
public class dbUtil {
    private dbHelper dHelper;

    private static final String[] createBDSQL = new String[] {

            "CREATE TABLE clientes ("+
                    "Id INTEGER  PRIMARY KEY AUTOINCREMENT,"+
                    "Datacad     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"+
                    "Nome        TEXT NOT NULL,"+
                    "CPFCNPJ     TEXT,"+
                    "Cidade      TEXT NOT NULL);",

            "CREATE TABLE cotacao ("+
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "Nome       TEXT,"+
                    "Data       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"+
                    "IDCliente  INTEGER NOT NULL,"+
                    "FOREIGN KEY (IDCliente) REFERENCES clientes(Id));"
                    ,

            "CREATE TABLE produtoscotacao ("+
                    "Id INTEGER PRIMARY KEY,"+
                    "Descricao  TEXT NOT NULL,"+
                    "Preco1     REAL NOT NULL,"+
                    "Preco2     REAL NOT NULL"+
                    ");",



    };

    public dbUtil(Context contexto) {
        dHelper = new dbHelper(contexto, "storecheck", 1, createBDSQL, null);

    }

    public void verificaBD() {

        try {
            dHelper.getWritableDatabase();

        } finally {
            dHelper.close();

        }

    }
}
