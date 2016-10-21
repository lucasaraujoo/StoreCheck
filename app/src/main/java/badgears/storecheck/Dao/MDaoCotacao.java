package badgears.storecheck.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.Modelos.MCotacaoItem;
import badgears.storecheck.Modelos.MProduto;

/**
 * Created by Lucas on 26/09/2016.
 */
public class MDaoCotacao extends  DaoMain {

    public MDaoCotacao(Context contexto) {
        super(contexto);
    }

    public boolean deletaCotacao (int idCotacao){
        return  db.delete("cotacao", "Id = "+ idCotacao, null) > 0;
    }

    public void atualizaTotalizadores (MCotacao objCotacao){
        boolean retorno = false;
        String attTotais = "select count(c.id) as TotalItens, "+
                        "SUM(CASE WHEN pc.Cotar=1 THEN 1 ELSE 0 END) as EmEstoque, "+
                        "SUM(CASE WHEN pc.Cotar=0 THEN 1 ELSE 0 END) as SemEstoque "+
                        "from cotacao c "+
                        "left join produtoscotacao pc on pc.idCotacao = c.Id "+
                        "where c.Id ="+objCotacao.getID();

        Cursor c = db.rawQuery(attTotais, null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {

                    objCotacao.setiTotItens(c.getInt(c.getColumnIndex("TotalItens")));
                    objCotacao.setiEmEstoque(c.getInt(c.getColumnIndex("EmEstoque")));
                    objCotacao.setiSemEstoque(c.getInt(c.getColumnIndex("SemEstoque")));


                }while(c.moveToNext());

            }

        } finally {
            c.close();

        }


    }

    public boolean VerificarNomeCotacaoDia (String data){
        boolean retorno = false;
        String attTotais = "select * from cotacao c where c.Data='" + data +"'";

        Cursor c = db.rawQuery(attTotais, null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    retorno = true;
                    return retorno;
                }while(c.moveToNext());
            }

        } finally {
            c.close();

        }
return retorno;
    }

    public boolean updatePrecos(ArrayList<MCotacaoItem> listaItens){
        boolean retorno = false;
        String updateCotacaoItens = "update produtoscotacao set "+
                "Preco1 = ?, Preco2 = ? where "+
                "id = ?";

        SQLiteStatement stmt = null;
        MCotacaoItem oItem = null;
        db.beginTransaction();
        try {
            stmt = db.compileStatement(updateCotacaoItens);
            for (int i = 0; i < listaItens.size(); i++) {
                oItem = listaItens.get(i);

                stmt.bindDouble(1, oItem.getPreco1());
                stmt.bindDouble(2, oItem.getPreco2());
                stmt.bindLong(3, oItem.getId());
                stmt.execute();
                stmt.clearBindings();
            }
            db.setTransactionSuccessful();
            retorno = true;

        }finally {
            db.endTransaction();
        }
        return retorno;
    }

    public boolean gravaItensCotacao(MCotacao objCotacao){
        boolean retorno = false;
        String InsertProdutoCotacao = "Insert into produtoscotacao "+
                "(IdCotacao, Idproduto, Preco1, Preco2, Cotar) values "+
                "(?, ?, ?, ?, ?)";

        SQLiteStatement stmt = null;
        MCotacaoItem oItem = null;
        int idCotacaoItem ;

        db.beginTransaction();
        try {
            stmt = db.compileStatement(InsertProdutoCotacao);
            for (int i = 0; i < objCotacao.getItensCotacao().size(); i++) {
                oItem = objCotacao.getItensCotacao().get(i);

                stmt.bindLong(1, objCotacao.getID());
                stmt.bindLong(2, oItem.getoProduto().getId());
                stmt.bindDouble(3, oItem.getPreco1());
                stmt.bindDouble(4, oItem.getPreco2());
                stmt.bindLong(5, oItem.getbCotar() ? 1 : 0);
                idCotacaoItem = (int) stmt.executeInsert();
                oItem.setId(idCotacaoItem);

                stmt.clearBindings();




            }
            db.setTransactionSuccessful();
            retorno = true;
        }finally {
            db.endTransaction();
        }
        return retorno;
    }

    public boolean gravaCotacao(MCotacao objCotacao){

        boolean retorno = false;
        String InsertCliente = "insert into cotacao (Nome, Obs,Data , IDCliente  )"+
                " values (? ,?, ?, ?)";

        int IDCotacao = objCotacao.getID();

        SQLiteStatement stmt = null;

        db.beginTransaction();
        try{
            stmt = db.compileStatement(InsertCliente);
            stmt.bindString(1, objCotacao.getNome());
            stmt.bindString(2,objCotacao.getObs());
            stmt.bindString(3, new SimpleDateFormat("dd/MM/yyyy").format( objCotacao.getData()));
            stmt.bindLong(4, objCotacao.getIDCliente());


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

    public void getItensCotacao(MCotacao oCotacao){
        MCotacaoItem itemCotacao = null;
        MProduto oProduto = null;
        oCotacao.getItensCotacao().clear();
        String sQuery = "select p.Id as IdProduto, ic.Id as IdItem, p.Descricao, p.Categoria, p.Tipo, "+
                    "ic.Preco1, ic.Preco2, ic.Cotar "+
                    "from produtoscotacao ic "+
                    " left join produtos p on p.Id = ic.Idproduto "+
                    "where ic.IdCotacao = "+oCotacao.getID();
        Cursor c = db.rawQuery(sQuery, null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    oProduto  = new MProduto(
                            c.getInt(c.getColumnIndex("IdProduto")),
                            c.getString(c.getColumnIndex("Descricao")),
                            c.getString(c.getColumnIndex("Categoria")),
                            c.getString(c.getColumnIndex("Tipo"))
                    );
                    itemCotacao = new MCotacaoItem(oProduto);
                    itemCotacao.setbCotar(c.getInt(c.getColumnIndex("Cotar")) == 1 );
                    itemCotacao.setPreco1(c.getDouble(c.getColumnIndex("Preco1")));
                    itemCotacao.setPreco2(c.getDouble(c.getColumnIndex("Preco2")));
                    itemCotacao.setId(c.getInt(c.getColumnIndex("IdItem")));

                    oCotacao.getItensCotacao().add(itemCotacao);


                }while(c.moveToNext());

            }

        } finally {
            c.close();

        }
    }

    public ArrayList<MCotacao> getListaCotacao(){
        ArrayList<MCotacao> lista = new ArrayList<MCotacao>();
        MCotacao cotacao = null;
        String sQuery = "select c.Id, c.Nome, c.IDCliente, c.Data, "+
                "count(c.id) as TotalItens, "+
                "SUM(CASE WHEN pc.Cotar=1 THEN 1 ELSE 0 END) as EmEstoque, "+
                "SUM(CASE WHEN pc.Cotar=0 THEN 1 ELSE 0 END) as SemEstoque "+
                "from cotacao c "+
                "left join produtoscotacao pc on pc.idCotacao = c.Id "+
                "group by c.Id, c.Nome, c.IDCliente, c.Data ";

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
                    cotacao.setiTotItens(c.getInt(c.getColumnIndex("TotalItens")));
                    cotacao.setiEmEstoque(c.getInt(c.getColumnIndex("EmEstoque")));
                    cotacao.setiSemEstoque(c.getInt(c.getColumnIndex("SemEstoque")));
                    lista.add(cotacao);
                }while(c.moveToNext());

            }

        } finally {
            c.close();

        }
       return  lista;
    }
}
