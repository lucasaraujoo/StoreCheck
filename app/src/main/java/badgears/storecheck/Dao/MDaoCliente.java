package badgears.storecheck.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;
import java.util.Date;

import badgears.storecheck.Modelos.MCliente;

/**
 * Created by Lucas on 26/09/2016.
 */
public class MDaoCliente extends DaoMain{
    public MDaoCliente(Context contexto) {
        super(contexto);
    }

    public boolean gravaCliente(MCliente objCliente){
        
        boolean retorno = false;
        String InsertCliente = "insert into clientes (Nome ,Datacad ,CPFCNPJ ,Segmentacao , Telefone, Cidade  )"+
                                " values (? , ? ,?, ? ,?, ?)";

        int IDCliente = objCliente.getId();

        SQLiteStatement stmt = null;

        db.beginTransaction();
        try{
            stmt = db.compileStatement(InsertCliente);
            stmt.bindString(1, objCliente.getNome());
            stmt.bindString(2, new SimpleDateFormat("dd/MM/yyyy").format( new Date()));
            stmt.bindString(3, objCliente.getCPFCNPJ());
            stmt.bindString(4, objCliente.getSegmento());
            stmt.bindString(5, objCliente.getTelefone());
            stmt.bindString(6, objCliente.getCidade());

            IDCliente = (int) stmt.executeInsert();
            objCliente.setId(IDCliente);

            stmt.clearBindings();

            db.setTransactionSuccessful();

            retorno = true;
        } finally {
            db.endTransaction();
        }
        return retorno;
    }

    public int getIDCliente(MCliente objCliente) {

        int idCliente = 0;
        String sQuery = "select c.Id from clientes c "+
                "where c.Nome = '"+objCliente.getNome().toString()+ "' "+
                "and c.Telefone = '"+objCliente.getTelefone().toString() + "'";

        Cursor c = db.rawQuery(sQuery, null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                idCliente = c.getInt(0);
            }

        } finally {
            c.close();

        }

        return idCliente;

    }

}
