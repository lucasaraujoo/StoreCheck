package badgears.storecheck;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import badgears.storecheck.Controladores.DatePickerFragment;
import jxl.write.WriteException;

public class Relatorio extends AppCompatActivity {
    public Button btnGerarRelatorio;
    public Button btnVoltar;
    public EditText etDataRelatorio;
    public Button btnPegarData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        btnGerarRelatorio = (Button) findViewById(R.id.btnGerarRelatorio);
        btnVoltar = (Button) findViewById(R.id.btnVoltarRelatorio);
        etDataRelatorio = (EditText) findViewById(R.id.etDataRelatorio);
        btnPegarData = (Button)findViewById(R.id.btnData);


        btnGerarRelatorio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Excel t = new Excel(Relatorio.this);
                if (VerificarData(etDataRelatorio.getText().toString())){
                    try {
                        t.exportToExcel(etDataRelatorio.getText().toString());
                        Relatorio();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
            }else{
                    Toast.makeText(getApplicationContext(), "Data invalida!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Relatorio.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnPegarData.setOnClickListener(new View.OnClickListener(){

            @Override
                    public void onClick(View view){
                showDatePickerDialog(view);

            }
        });

    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


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


    private void LerAquivo(String filename){
        //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ filename);
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath());
        File file = new File(directory, filename);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/vnd.ms-excel");
        Intent intent = Intent.createChooser(target, "Abrir " + filename);


        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Vocẽ não tem app para abrir o relatorio", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Consulte a parte de Ajuda na tela inicial para poder baixar um app", Toast.LENGTH_SHORT).show();
        }
        finally {

        }
    }

    public void Relatorio(){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Relatorio");
        alert.setMessage("Relatorio gerado, o que deseja fazer ?");
        alert.setButton(Dialog.BUTTON_POSITIVE,"Abrir arquivo",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                LerAquivo("StoreCheck.xls");
            }
        });
        alert.setButton(Dialog.BUTTON_NEGATIVE,"Enviar por e-mail",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath());
                File file = new File(directory, "StoreCheck_" + etDataRelatorio.getText().toString() + ".xls");
                sendEmail(file);
            }
        });
        alert.setButton(Dialog.BUTTON_NEUTRAL,"Concluir",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }

    protected void sendEmail(File file) {
        String[] TO = {"tuliocll@gmail.com"}; // Não vou usar emitente
        String[] CC = {"xyz@gmail.com"};       //Nem destinatario
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


       // emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
       // emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Store Check");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Segue em anexo o Store Check");

        try {
            startActivity(Intent.createChooser(emailIntent, "Escolha um aplicativo para enviar o e-mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),
                    "Você não tem app de envio de e-mail, consulte a parte de Ajuda na tela inicial", Toast.LENGTH_SHORT).show();
        }
    }


}
