package badgears.storecheck.Controladores;

import badgears.storecheck.Dao.MDaoCliente;
import badgears.storecheck.Dao.MDaoCotacao;
import badgears.storecheck.EscolherItensCotar;
import badgears.storecheck.ItemListView;
import badgears.storecheck.Modelos.MCliente;
import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.R;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ControladorCotacao extends AppCompatActivity implements Button.OnClickListener {

    private MCotacao oCotacaoEditar = null;
    private MCliente oCliente       = null;

    private EditText edNomeCotacao ;
    private EditText edDataCotacao;
    private EditText edNomeCliente;
    private EditText edCidade;
    private EditText edTelefone;


    private Button botao;
    private Button btnSalvar;
    private Button btnCancelar;

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotar);
        //// TODO: 24/09/2016 Criar Dao cotacao e funções para gravação
        this.edDataCotacao  =   (EditText)  findViewById(R.id.etData);
        this.edNomeCotacao  =   (EditText)  findViewById(R.id.etNomeCotacao);
        this.edCidade       =   (EditText)  findViewById(R.id.etCidade);
        this.edNomeCliente  =   (EditText)  findViewById(R.id.edCliente);
        this.edTelefone     =   (EditText)  findViewById(R.id.edTelefone);

        botao = (Button) findViewById(R.id.btnPegarData);
        botao.setOnClickListener(this);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);



        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        String dateString = sdf.format(date);
        edDataCotacao.setText(dateString);

        this.carregaCotacaoRecebida();
    }

    private void carregaCotacaoRecebida(){
        this.setCotacaoRecebida();
        this.carregaCotacaoParaEdicao();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes,
                        dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String Mes = "";
            if(monthOfYear<9){
                Mes = "0" + (monthOfYear+1);
            }
            else{
                Mes = String.valueOf(monthOfYear+1);
            }
            String data = String.valueOf(dayOfMonth) + "/"
                    + Mes + "/" + String.valueOf(year);
            Toast.makeText(ControladorCotacao.this,
                    "DATA = " + data, Toast.LENGTH_SHORT)
                    .show();
            edDataCotacao.setText(data);
        }
    };

    @Override
    public void onClick(View v) {
        if (v == botao)
            showDialog(DATE_DIALOG_ID);

        if(v==btnSalvar){
            this.atualizaObjetoCliente();
            if (!this.salvaCliente()) return;
            try {
                this.atualizaObjetoCotacao();
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            if (this.gravaCotacao()) {
                //Escolher os itens para editar
                Intent it = new Intent(ControladorCotacao.this, EscolherItensCotar.class);
                it.putExtra("objCotacao", oCotacaoEditar);
                startActivity(it);
            }
        }
        if(v==btnCancelar){
            finish();
        }
    }

    private void setCotacaoRecebida(){
        this.oCotacaoEditar = (MCotacao) getIntent().getExtras().getParcelable("objCotacao");
        this.carregaCliente();
    }

    private void carregaCotacaoParaEdicao(){
        //this.edDataCotacao.setText(new SimpleDateFormat("dd/MM/yyyy").format( new Date()));
        this.edNomeCotacao.setText(this.oCotacaoEditar.getNome());
    }

    private void atualizaObjetoCliente(){
        this.oCliente.setNome(this.edNomeCotacao.getText().toString());
        this.oCliente.setCidade(this.edCidade.getText().toString());
        this.oCliente.setNome(this.edNomeCliente.getText().toString());
        this.oCliente.setTelefone(this.edTelefone.getText().toString());
    }

    private void atualizaObjetoCotacao() throws ParseException {
        this.oCotacaoEditar.setData(new SimpleDateFormat("dd/MM/yyyy").parse(this.edDataCotacao.getText().toString()));
        this.oCotacaoEditar.setNome(this.edNomeCotacao.getText().toString());
        this.oCotacaoEditar.setIDCliente(this.oCliente.getId());

    }

    private void carregaCliente(){
        //// TODO: 27/09/2016 Fazer função de buscar cliente no DaoCliente
        if (this.oCliente == null){
            this.oCliente = new MCliente();
        }
    }

    //Salvar cliente
    private Boolean salvaCliente(){
        boolean retorno = false;
        if (this.oCliente.getNome().trim().length() == 0){
            Toast.makeText(ControladorCotacao.this, "Informe o Nome do Cliente!", Toast.LENGTH_SHORT).show();
            this.edNomeCliente.requestFocus();
            return false;
        }

        MDaoCliente oDao = new MDaoCliente(this);
        try {
            //verifica se já existe
            int IDCliente = oDao.getIDCliente(this.oCliente);
            if (IDCliente == 0) {
                retorno = oDao.gravaCliente(this.oCliente);
            }else{
                this.oCliente.setId(IDCliente);
                retorno = true;
            }
        }catch (Exception e){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(this.getString(R.string.app_name))
                    .setMessage("Erro ao manipular cliente na Base de Dados!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            return;
                        }
                    }).create();

            dialog.show();
        }finally {
            oDao.fechar();
            oDao = null;
        }
        return retorno;
    }

    private boolean gravaCotacao(){

        Boolean retorno = false;
        if (this.oCotacaoEditar.getNome().trim().length() == 0){
            Toast.makeText(ControladorCotacao.this, "Informe o Nome da Cotação!", Toast.LENGTH_SHORT).show();
            this.edNomeCotacao.requestFocus();
            return false;
        }

        MDaoCotacao oDaoCotacao = new MDaoCotacao(this);
        try{
            retorno = oDaoCotacao.gravaCotacao(this.oCotacaoEditar);
        }catch (Exception e){
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(this.getString(R.string.app_name))
                    .setMessage("Erro ao salvar Cotação na Base da Dados!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            return;
                        }
                    }).create();

            dialog.show();
        }finally {
            oDaoCotacao.fechar();
            oDaoCotacao = null;
        }
        return retorno;
    }


}
