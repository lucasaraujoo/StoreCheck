package badgears.storecheck.Controladores;

import badgears.storecheck.EscolherItensCotar;
import badgears.storecheck.ItemListView;
import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ControladorCotacao extends AppCompatActivity implements Button.OnClickListener {

    private MCotacao oCotacaoEditar = null;

    private EditText edNomeCotacao ;
    private EditText edDataCotacao;


    private Button botao;
    private Button btnSalvar;
    private Button btnCancelar;

    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotar);
        //// TODO: 24/09/2016 Criar Dao cotacao e funções para gravação
        this.edDataCotacao = (EditText) findViewById(R.id.etData);
        this.edNomeCotacao = (EditText) findViewById(R.id.etNomeCotacao);

        botao = (Button) findViewById(R.id.btnPegarData);
        botao.setOnClickListener(this);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);



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
            String data = String.valueOf(dayOfMonth) + " /"
                    + String.valueOf(monthOfYear+1) + " /" + String.valueOf(year);
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
            Intent it = new Intent(ControladorCotacao.this, EscolherItensCotar.class);
            startActivity(it);
        }
        if(v==btnCancelar){
            finish();
        }
    }

    private void setCotacaoRecebida(){
        this.oCotacaoEditar = (MCotacao) getIntent().getExtras().getParcelable("objCotacao");
    }

    private void carregaCotacaoParaEdicao(){
        //this.edDataCotacao.setText(new SimpleDateFormat().format( new Date()));
        this.edNomeCotacao.setText(this.oCotacaoEditar.getNome());
    }
}
