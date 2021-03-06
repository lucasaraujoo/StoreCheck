package badgears.storecheck;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import badgears.storecheck.Controladores.ControladorColocarPrecos;
import badgears.storecheck.Dao.MDaoCotacao;
import badgears.storecheck.Dao.MDaoProduto;
import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.Modelos.MCotacaoItem;
import badgears.storecheck.Modelos.MProduto;


public class EscolherItensCotar extends AppCompatActivity {

    public badgears.storecheck.Dao.MDaoProduto Produtos = null;
    public ArrayList<MProduto> listaproduto = null;
    public MProduto produto;
    private MCotacao oCotacao = null;
    String[] produtos = new String[]{};
    public Button btnCancelar;
    public Button btnSalva;
    public ListView lista;
    ItemListView adapter;
    private boolean bRecuperando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_itens_cotar);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        btnCancelar = (Button) findViewById(R.id.btCancelar);
//        btnCancelar.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//              SairSemSalvar();
//            }
//        });
//
//        btnSalva = (Button) findViewById(R.id.btSalvar);
//        btnSalva.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Salvar();
//            }
//        });

        lista = (ListView) findViewById(R.id.listaProdutos);
        setCotacaoRecebida();
        adapter = new ItemListView(this, oCotacao.getItensCotacao());
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Apertou " + i, Toast.LENGTH_SHORT).show();
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Apertou e segurou " + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        bRecuperando =  (oCotacao.getItensCotacao().size() > 0);

        if(!bRecuperando)
            PegarProdutos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_confirmar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancelar:
                SairSemSalvar();
                break;
            case R.id.action_confirmar:
                Salvar();
                break;
        }
        return true;
    }

    private void setCotacaoRecebida(){
        this.oCotacao = (MCotacao) getIntent().getExtras().getParcelable("objCotacao");
    }

    public boolean VerificarItens(){
        boolean retorno = false;
        return retorno;
    }

    public void SairSemSalvar(){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Atenção");
        alert.setMessage("Você iniciou uma cotação e não concluiu, deseja continuar mais tarde?");
        alert.setButton(Dialog.BUTTON_POSITIVE,"Continuar mais tarde",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  Toast.makeText(getApplicationContext(), "Deixar na base para continuar depois", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setButton(Dialog.BUTTON_NEGATIVE,"Apagar cotação",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  Toast.makeText(getApplicationContext(), "Apagar da base de dados", Toast.LENGTH_SHORT).show();
                //apagar cotacao na base
                if (deletarCotacaoNaBase()) {
                    finish();
                }
            }
        });
        alert.setButton(Dialog.BUTTON_NEUTRAL,"Cancelar",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
            //    Toast.makeText(getApplicationContext(), "Não fazer nada", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

        alert.show();
    }

    private boolean deletarCotacaoNaBase(){
        MDaoCotacao oDao = new MDaoCotacao(this);
        boolean retorno = false;
        try{
            retorno = oDao.deletaCotacao(this.oCotacao.getID());
        }catch (Exception e){
            android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                    .setTitle(this.getString(R.string.app_name))
                    .setMessage("Erro ao deletar cotação na Base de Dados!")
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
        return retorno;
    }

    private void removerItensNaoCotaveis(){
        for (int i = 0 ; i < oCotacao.getItensCotacao().size() ; i++){
            if (oCotacao.getItensCotacao().get(i).getbCotar() == false){
                oCotacao.getItensCotacao().remove(i);
            }
        }
    }

    private void gravarItensDaCotacao(){
        MDaoCotacao oDao = new MDaoCotacao(this);

        try{

            oDao.gravaItensCotacao(oCotacao);

            //atualizar totalizadores no objeto
        }catch (Exception e){
          android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                  .setTitle(this.getString(R.string.app_name))
                  .setMessage("Erro ao gravar itens na Base de Dados!")
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

    public void Salvar(){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Atenção");
        alert.setMessage("Deseja fechar o Store Check ?");
        alert.setButton(Dialog.BUTTON_POSITIVE,"Sim, salvar",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //salvar itens da cotaçao
                if (!bRecuperando)
                    gravarItensDaCotacao();
                Intent intAddPrecos = new Intent(getBaseContext(), ControladorColocarPrecos.class);
                intAddPrecos.putExtra("objCotacao", oCotacao);
                startActivity(intAddPrecos);
               // Toast.makeText(getApplicationContext(), "Produtos salvos... \n Faltando gerar relatorio", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setButton(Dialog.BUTTON_NEGATIVE,"Não, cancelar",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Continuando Store Check...", Toast.LENGTH_SHORT).show();

            }
        });
        alert.show();
    }

    public void PegarProdutos (){
        MDaoProduto oDao = new MDaoProduto(this);
        produtos = new String[]{};


        try {
            listaproduto = oDao.getProdutos();
            produtos = Arrays.copyOf(produtos, produtos.length + listaproduto.size());
            for(int i=0;i < listaproduto.size();i++){
                //Toast.makeText(getApplicationContext(), "Total: " + listaproduto.size(), Toast.LENGTH_SHORT).show();

                produtos[i] =listaproduto.get(i).getDescricao().toString();

                oCotacao.getItensCotacao().add(new MCotacaoItem(listaproduto.get(i)));
                //TODO: Ver depois
            }

            adapter = new ItemListView(this, oCotacao.getItensCotacao());
            lista.setAdapter(adapter);

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            oDao.fechar();
        }
    }
}
