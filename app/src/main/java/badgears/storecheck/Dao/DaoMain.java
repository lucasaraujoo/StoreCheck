package badgears.storecheck.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Lucas on 23/09/2016.
 */
public class DaoMain {

    protected Context ctx;
    protected SQLiteDatabase db;

    protected static final String NOMEBANCO = "storecheck";

    public DaoMain(Context contexto) {
        ctx = contexto;
        db = ctx.openOrCreateDatabase(NOMEBANCO, Context.MODE_PRIVATE, null);

    }

    public void fechar() {
        db.close();

    }
}
