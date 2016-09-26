package badgears.storecheck.Controladores;

import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorCotacao extends AppCompatActivity {

    private MCotacao oCotacaoEditar = null;

    private EditText edNomeCotacao ;
    private EditText edDataCotacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotar);
        //// TODO: 24/09/2016 Criar Dao cotacao e funções para gravação
        this.edDataCotacao = (EditText) findViewById(R.id.etData);
        this.edNomeCotacao = (EditText) findViewById(R.id.etNomeCotacao);

        this.carregaCotacaoRecebida();
    }

    private void carregaCotacaoRecebida(){
        this.setCotacaoRecebida();
        this.carregaCotacaoParaEdicao();
    }

    private void setCotacaoRecebida(){
        this.oCotacaoEditar = (MCotacao) getIntent().getExtras().getParcelable("objCotacao");
    }

    private void carregaCotacaoParaEdicao(){
        this.edDataCotacao.setText(new SimpleDateFormat().format( new Date()));
        this.edNomeCotacao.setText(this.oCotacaoEditar.getNome());
    }
}
