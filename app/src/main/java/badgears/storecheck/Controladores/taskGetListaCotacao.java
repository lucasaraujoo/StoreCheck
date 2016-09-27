package badgears.storecheck.Controladores;

import android.app.ProgressDialog;
import android.database.SQLException;
import android.os.AsyncTask;

import java.util.ArrayList;

import badgears.storecheck.Dao.MDaoCotacao;
import badgears.storecheck.MainActivity;
import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.R;

/**
 * Created by Lucas on 27/09/2016.
 */
public class taskGetListaCotacao extends AsyncTask<Void, Void, ArrayList<MCotacao>> {

    private ArrayList<MCotacao> listaCotacao = null;
    private MainActivity contexto;
    private ProgressDialog progresso;

    public taskGetListaCotacao(MainActivity ctx) {
        contexto = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progresso = ProgressDialog.show(contexto, contexto.getString(R.string.app_name),
                "Aguarde, Pesquisando Lista de Cotações....");
    }

    @Override
    protected ArrayList<MCotacao> doInBackground(Void... params) {
        MDaoCotacao oDao = new MDaoCotacao(contexto);

        try {
            listaCotacao = oDao.getListaCotacao();

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            oDao.fechar();
            progresso.dismiss();
        }


        return listaCotacao;
    }

    @Override
    protected void onPostExecute(ArrayList<MCotacao> oLista) {
        super.onPostExecute(oLista);
        contexto.setListaDeCotacoes(oLista);
    }
}
