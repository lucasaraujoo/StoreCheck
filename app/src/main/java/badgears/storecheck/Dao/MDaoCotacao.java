package badgears.storecheck.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import badgears.storecheck.Modelos.MCotacao;

/**
 * Created by Lucas on 26/09/2016.
 */
public class MDaoCotacao extends  DaoMain {

    public MDaoCotacao(Context contexto) {
        super(contexto);
    }

    public boolean gravaCotacao(MCotacao objCotacao){

        boolean retorno = false;
        String InsertCliente = "insert into cotacao (Nome ,Data , IDCliente  )"+
                " values (? ,?, ?)";
        //// TODO: 26/09/2016 Fazer Update Cotacao
        int IDCotacao = objCotacao.getID();

        SQLiteStatement stmt = null;

        db.beginTransaction();
        try{
            stmt = db.compileStatement(InsertCliente);
            stmt.bindString(1, objCotacao.getNome());
            stmt.bindString(2, new SimpleDateFormat("dd/MM/yyyy").format( objCotacao.getData()));
            stmt.bindLong(3, objCotacao.getIDCliente());


            IDCotacao = (int) stmt.executeInsert();
            objCotacao.setID(IDCotacao);

            stmt.clearBindings();

            db.setTransactionSuccessful();

            retorno = true;
        } finally {
            db.endTransaction();
        }
        return retorno;
    }

    public ArrayList<MCotacao> getListaCotacao(){
        ArrayList<MCotacao> lista = new ArrayList<MCotacao>();
        MCotacao cotacao = null;
        String sQuery = "select c.Id, c.Nome, c.IDCliente, c.Data from cotacao c ";

        Cursor c = db.rawQuery(sQuery, null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    cotacao = new MCotacao();
                    cotacao.setID(c.getInt(c.getColumnIndex("Id")));
                    cotacao.setNome(c.getString(c.getColumnIndex("Nome")));
                    cotacao.setIDCliente(c.getInt(c.getColumnIndex("IDCliente")));
                    cotacao.setData(new Date( c.getLong(c.getColumnIndex("Data"))));
                    lista.add(cotacao);
                }while(c.moveToNext());

            }

        } finally {
            c.close();

        }
       return  lista;
    }
}