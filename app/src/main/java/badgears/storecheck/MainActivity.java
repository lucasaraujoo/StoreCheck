package badgears.storecheck;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import badgears.storecheck.Dao.MDaoCotacao;
import badgears.storecheck.Dao.MDaoProduto;
import badgears.storecheck.Dao.tskIniciarBD;
import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.Controladores.ControladorCotacao;
import badgears.storecheck.Controladores.taskGetListaCotacao;
import jxl.write.WriteException;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_COTACAO = 8943;

    private ListView lista = null;
    private AdapterCotacao adapterCotacao = null;
    private ArrayList<MCotacao> listaDeCotacoes = null;
    private MDaoProduto Produtos;
    private boolean buscarCotacoes = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.lista = (ListView) findViewById(R.id.lvCotacoes);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.arcmenu_android_example_layout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //criar cotacao NORMAL
                MCotacao objCotacao = new MCotacao();
                Intent intAddCotacao = new Intent(getBaseContext(), ControladorCotacao.class);
                intAddCotacao.putExtra("objCotacao", objCotacao);
                startActivityForResult(intAddCotacao, EDIT_COTACAO);

            }
        });


        this.VerificaBD();
        Produtos = new MDaoProduto(this);
        try {
            Produtos.criaProdutsSeNaoExistir();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setExtra();
        if (this.buscarCotacoes) {
            this.asyncGetListaDeCotacoes();
        }
        Excel t = new Excel(this);
        // Toast.makeText(MainActivity.this, "Criar relatorio", Toast.LENGTH_SHORT).show();
        //  CellBackgroundPatternTest c = new CellBackgroundPatternTest();

    }

    private void setExtra(){
        if (getIntent().hasExtra("BuscarCotacoes")) {
            this.buscarCotacoes = getIntent().getExtras().getBoolean("BuscarCotacoes", true);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }

    private void VerificaBD() {
        new tskIniciarBD(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AjudaActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_relatorios){
            Intent intent = new Intent(this, Relatorio.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_sobre){
            Sobre();
            return true;
        }
        if(id == R.id.action_deletar){
           Intent intent = new Intent(this, DeletarStoreCheck.class);
           startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_COTACAO){
            //// TODO: 24/09/2016 receber cotacao e inserir no list
        }
    }

    private void asyncGetListaDeCotacoes() {
        if (this.listaDeCotacoes != null) {
            this.listaDeCotacoes.clear();
        }
        this.listaDeCotacoes = null;
        new taskGetListaCotacao(this).execute();

    }
//Desativei verificação por que tava bugado
    public void setListaDeCotacoes(ArrayList<MCotacao> oLista){

        this.listaDeCotacoes = oLista;
        //this.adapterCotacao = new AdapterCotacao(this, oLista);
        //this.lista.setAdapter(this.adapterCotacao);
        //verificar se tem cotações na base
        if (this.listaDeCotacoes.size() > 0){
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Atenção");
            alert.setMessage("Você iniciou um Store Check e não concluiu, deseja continuar agora?");
            alert.setButton(Dialog.BUTTON_POSITIVE,"Continuar",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (carregaItensCotacao(listaDeCotacoes.get(0))){
                        //se carregou os itens da cotação
                        //chama a activity de escolher itens
                        Intent it = new Intent(MainActivity.this, EscolherItensCotar.class);
                        it.putExtra("objCotacao", listaDeCotacoes.get(0));
                        startActivity(it);
                    }else{
                        //se não carregou itens
                        //apaga a cotação da base
                        apagarCotacao(listaDeCotacoes.get(0).getID());
                        asyncGetListaDeCotacoes();
                    }

                }
            });
            alert.setButton(Dialog.BUTTON_NEGATIVE,"Apagar Store Check",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                   // Toast.makeText(getApplicationContext(), "Apagar da base de dados", Toast.LENGTH_SHORT).show();
                    apagarCotacao(listaDeCotacoes.get(0).getID());
                    asyncGetListaDeCotacoes();

                }
            });
            alert.show();
        }

    }

    private void apagarCotacao(int Id){
        MDaoCotacao oDao = new MDaoCotacao(this);
        try{

            oDao.deletaCotacao(Id);

        }catch (Exception e){
            android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                    .setTitle(this.getString(R.string.app_name))
                    .setMessage("Erro ao apagar Store Check!")
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

    public void Sobre(){
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                .setTitle(this.getString(R.string.app_name)+ " - Sobre")
                .setMessage("O que há de novo: \n -Enviar Store check por e-mail. \n -Deletar Store check(O ultimo ou por data)." +
                        "\n -Correção do teclado que ficava por cima dos preços. " +
                        "\n -Altera botão Salvar e cancelar ao escolher itens e por preço." +
                        "\n -Adiciona telefone o corrigir outras opções do relatorio."+
                        "\n -Adicionar novo icone ao app." +
                        "\n -Correção e liberar opção de deletar StoreCheck." +
                        "\n\n Versão atual: 1.0 \n\nDesenvolvido por: Tulio Calil")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                }).create();

        dialog.show();
    }

    private boolean carregaItensCotacao(MCotacao oCotacao){
        boolean retorno = false;
        MDaoCotacao oDao = new MDaoCotacao(this);
        try{

            oDao.getItensCotacao(oCotacao);
            retorno = true;
        }catch (Exception e){
            android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                    .setTitle(this.getString(R.string.app_name))
                    .setMessage("Erro ao carregar itens do Store Check!")
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
}
