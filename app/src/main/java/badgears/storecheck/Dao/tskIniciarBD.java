package badgears.storecheck.Dao;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * Created by Lucas on 23/09/2016.
 */
public class tskIniciarBD extends AsyncTask<Void, Integer, Void> {

    private Context contexto;
    private ProgressDialog progresso;
    private Exception eErro = null;

    public tskIniciarBD(Context ctx) {
        this.contexto = ctx;
    }


    @Override
    protected void onPreExecute() {
        progresso = ProgressDialog.show(contexto, "StoreCheck",
                "Aguarde, criando tabelas ....");
    }


    @Override
    protected Void doInBackground(Void... params) {

        dbUtil bdUtil = new dbUtil(this.contexto);
        try {
            bdUtil.verificaBD();
            //Inserir criações aqui

        } catch (Exception e) {
            this.eErro = e;

        } finally {
            progresso.dismiss();
            bdUtil = null;

        }

        return null;

    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (eErro != null) {

            AlertDialog dialog = new AlertDialog.Builder(this.contexto)
                    .setTitle("StoreCheck")
                    .setMessage("Erro ao verificar o BD ! \n Erro:"+this.eErro.getMessage())
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            return;
                        }
                    }).create();

            dialog.show();
            //// TODO: 24/09/2016 matar aplicação 
        }

    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);


    }






}
