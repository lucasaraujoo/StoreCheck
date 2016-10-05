package badgears.storecheck.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import badgears.storecheck.Modelos.MCliente;
import badgears.storecheck.Modelos.MProduto;

/**
 * Created by Lucas on 26/09/2016.
 */
public class MDaoProduto extends DaoMain{
    public MDaoProduto(Context contexto) {
        super(contexto);
    }

    private boolean jaCriamosOsProdutos() {

        boolean retorno = false;

        String sQuery = "select count(*) from produtos";

        Cursor c = db.rawQuery(sQuery, null);
        try {
            c.moveToFirst();
            retorno = c.getInt(0) > 0;

        } finally {
            c.close();
        }

        return retorno;

    }


    public ArrayList<MProduto> getProdutos() throws Exception {

        // Retorna todos os produtos;

        ArrayList<MProduto> retorno = null;
        String sQuery = "select * from produtos order by Descricao";

        Cursor c = db.rawQuery(sQuery, null);
        try {
            if (c.getCount() > 0) {
                retorno = new ArrayList<MProduto>();
                c.moveToFirst();
                do {
                    retorno.add(new MProduto(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)));

                } while (c.moveToNext());
            }

        } catch (Exception e) {
            if (retorno != null) {
                retorno.clear();
                retorno = null;
            }
            throw e;

        } finally {
            c.close();
        }

        return retorno;

    }

    public void criaProdutsSeNaoExistir() throws Exception {
        // Preenche a tabela de unidades de medida.

        if (!this.jaCriamosOsProdutos()) {

            ArrayList<MProduto> produtos = new ArrayList<MProduto>();
            try {
                produtos.add(new MProduto(0, "Extrusados", "Snacks","PDB"));
                produtos.add(new MProduto(0,"Ruffles", "Snacks","PDB"));
                produtos.add(new MProduto(0,"Bacozitos", "Snacks","PDB"));
                produtos.add(new MProduto(0,"Cebolitos", "Snacks","PDB"));
                produtos.add(new MProduto(0,"Doritos", "Snacks","PDB"));
                produtos.add(new MProduto(0,"Toddynho 200ml", "Toddynho","PDB"));
                produtos.add(new MProduto(0,"Toddynho light", "Toddynho","PDB"));
                produtos.add(new MProduto(0,"Toddynho Shake", "Toddynho","PDB"));
                produtos.add(new MProduto(0,"Toddynho Garrafa", "Toddynho","PDB"));
                produtos.add(new MProduto(0,"Aveia Tradicional", "Aveia","PDB"));
                produtos.add(new MProduto(0,"Aveia OT Bran", "Aveia","PDB"));
                produtos.add(new MProduto(0,"Aveia Mult Grãos", "Aveia","PDB"));
                produtos.add(new MProduto(0,"Toddy 200gr", "Toddy","PDB"));
                produtos.add(new MProduto(0,"Toddy 400gr", "Toddy","PDB"));
                produtos.add(new MProduto(0,"Toddy 800gr", "Toddy","PDB"));
                produtos.add(new MProduto(0,"Mágico 200gr", "Mágico","PDB"));
                produtos.add(new MProduto(0,"Mágico 400gr", "Mágico","PDB"));
                produtos.add(new MProduto(0,"Barra Tradicional", "Barras","PDB"));
                produtos.add(new MProduto(0,"Barra grega", "Barras", "PDB"));
                produtos.add(new MProduto(0,"Kero Coco 200ml", "Kero coco", "PDB"));
                produtos.add(new MProduto(0,"Kero Coco 33-ml", "Kero Coco", "PDB"));
                produtos.add(new MProduto(0,"Toddy 60gr", "Cookie", "PDB"));
                produtos.add(new MProduto(0,"Toddy 75gr", "Cookie", "PDB"));
                produtos.add(new MProduto(0,"Toddy 150gr", "Cookie", "PDB"));
                produtos.add(new MProduto(0,"Quaker 150gr", "Cookie", "PDB"));
                produtos.add(new MProduto(0,"Quaker 140gr", "Cookie", "PDB"));
                produtos.add(new MProduto(0,"Quaker 40gr", "Cookie", "PDB"));


                String sQuery = "insert into produtos (Id, Descricao, Categoria, Tipo) values (?, ?, ?, ?)";

                SQLiteStatement stmt = null;

                db.beginTransaction();
                try {
                    stmt = db.compileStatement(sQuery);
                    for (int i = 0; i < produtos.size(); i++) {
                        stmt.bindLong(1, i + 1);
                        stmt.bindString(2, produtos.get(i).getDescricao());
                        stmt.bindString(3, produtos.get(i).getCategoria());
                        stmt.bindString(4, produtos.get(i).getTipo());
                        stmt.executeInsert();
                        stmt.clearBindings();
                    }

                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                    stmt = null;

                }


            } finally {
                produtos.clear();
                produtos = null;

            }

        }
    }


    public int getIDProduto(MProduto objProduto) {

        int idProduto = 0;
        String sQuery = "select p.Id from produtos p "+
                "where c.descricao = '"+objProduto.getDescricao().toString()+ "' "+
                "and c.categoria = '"+objProduto.getCategoria().toString() + "'";

        Cursor c = db.rawQuery(sQuery, null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                idProduto = c.getInt(0);
            }

        } finally {
            c.close();

        }

        return idProduto;

    }

}
