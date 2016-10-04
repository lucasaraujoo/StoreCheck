package badgears.storecheck.Controladores;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import badgears.storecheck.AdapterPrecos;
import badgears.storecheck.Dao.MDaoCotacao;
import badgears.storecheck.ItemListView;
import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.Modelos.MCotacaoItem;
import badgears.storecheck.R;

public class ControladorColocarPrecos extends AppCompatActivity {

    private MCotacao oCotacao = null;
    private ListView lista;
    private ArrayList<MCotacaoItem> listaProdCotar;
    private AdapterPrecos adapterPrecos ;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocar_precos);

        lista = (ListView) findViewById(R.id.ListaProdutosPrecos);
        btnSalvar = (Button) findViewById(R.id.btSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePrecos();
            }
        });

        listaProdCotar = new ArrayList<MCotacaoItem>();
        setCotacaoRecebida();

        carregarListaDeProdutosACotar();


        adapterPrecos = new AdapterPrecos(this, listaProdCotar);
        lista.setAdapter(adapterPrecos);


    }

    private void setCotacaoRecebida(){
        this.oCotacao = (MCotacao) getIntent().getExtras().getParcelable("objCotacao");

    }

    private void carregarListaDeProdutosACotar(){
        for (int i = 0 ; i < oCotacao.getItensCotacao().size(); i++){
            if (oCotacao.getItensCotacao().get(i).getbCotar() ){
                this.listaProdCotar.add(oCotacao.getItensCotacao().get(i));
            }
        }
    }

    private void updatePrecos(){
        MDaoCotacao oDao = new MDaoCotacao(this);
        try{

            oDao.updatePrecos(listaProdCotar);
        }catch (Exception e){
            android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                    .setTitle(this.getString(R.string.app_name))
                    .setMessage("Erro ao gravar Precos na Base de Dados!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            return;
                        }
                    }).create();

            dialog.show();
        }finally{
            oDao.fechar();
            oDao = null;
        }
    }
}
