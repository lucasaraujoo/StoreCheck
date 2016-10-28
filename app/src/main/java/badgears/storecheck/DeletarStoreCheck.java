package badgears.storecheck;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.SQLDataException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import badgears.storecheck.Controladores.ControladorCotacao;
import badgears.storecheck.Controladores.DatePickerFragment;
import badgears.storecheck.Dao.MDaoCotacao;

public class DeletarStoreCheck extends AppCompatActivity {

    public EditText dataDeletar;
    public Button btnDataEscolher;
    public Button btnDeletarData;
    public Button btnDeletarUltimo;

    static final int DATE_DIALOG_ID = 0;
    public MDaoCotacao OdaoDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletar_store_check);

        dataDeletar = (EditText) findViewById(R.id.edDataDeletar);
        btnDataEscolher = (Button) findViewById(R.id.btnDataDeletar);
        btnDeletarData = (Button) findViewById(R.id.btnDeletarPordata);
        btnDeletarUltimo = (Button) findViewById(R.id.btnDeletarPorUltimo);

        //Joga a data atual no campo de texto assim que inicia a tela.
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        dataDeletar.setText(dateString);

        DialogoDeletar("ATENÇÃO","Store Chek deletado não pode ser recuperado!","Ok,continuar");

        btnDataEscolher.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                showDialog(DATE_DIALOG_ID);

            }
        });

        btnDeletarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(VerificarData(dataDeletar.getText().toString())){
                    OdaoDeletar = new MDaoCotacao(DeletarStoreCheck.this);
                    if( OdaoDeletar.DeletarStoreCheckPorData(dataDeletar.getText().toString())){
                        DialogoDeletar("Deletar Store Check por data","Store check deletado com sucesso!","Ok");
                    }
                    else{
                        DialogoDeletar("Deletar Store Check por data","Não existe Store Check nessa data","Ok");
                    }
                    OdaoDeletar = null;
                }
            }
        });
        btnDeletarUltimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OdaoDeletar = new MDaoCotacao(DeletarStoreCheck.this);
               if(OdaoDeletar.DeletarUltimoSoreCheck()){
                   DialogoDeletar("Deletar Ultimo Store Check","Ultimo Store Check Deletado com sucesso!","Ok");
               }
                else{
                   DialogoDeletar("Deletar Ultimo Store Check","Não existe Storecheck para deletar","Ok");
               }
            }
        });



    }


    public void DialogoDeletar(String Titulo,String Mensagem,String BotaoPositivo){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle(Titulo);
        alert.setMessage(Mensagem);
        alert.setButton(Dialog.BUTTON_POSITIVE,BotaoPositivo,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        alert.show();
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
            dataDeletar.setText(data);

        }
    };

    public static boolean VerificarData(String data)
    {
        if(data!="" || data!=null) {
            final String DATE_FORMAT = "dd/MM/yyyy";
            try {
                DateFormat df = new SimpleDateFormat(DATE_FORMAT);
                df.setLenient(false);
                df.parse(data);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        else{
            return false;
        }
    }
}
