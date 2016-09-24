package badgears.storecheck.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lucas on 23/09/2016.
 */
public class dbHelper extends SQLiteOpenHelper {
    private String[] scriptSQLCreate;
    private String scriptSQLDelete;
    private Context contexto;

    public dbHelper(Context contexto, String nomeBanco, int versaoBanco, String[] scriptSQLCreate, String scriptSQLDelete) {
        super(contexto, nomeBanco, null, versaoBanco);
        this.contexto = contexto;
        this.scriptSQLCreate = scriptSQLCreate;
        this.scriptSQLDelete = scriptSQLDelete;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criando BD.
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
        int qtdScript = this.scriptSQLCreate.length;
        for (int i = 0; i < qtdScript; i++) {
            String sql = this.scriptSQLCreate[i];
            db.execSQL(sql);
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrades no BD aqui.
    }
}
