package badgears.storecheck;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class EscolherItensCotar extends AppCompatActivity {

    String[] produtos = new String[]{
            "produto 1",
            "produto 2",
            "produto 3",
            "produto 4",
    };
    Boolean[] sim = new Boolean[]{
           false,
            false,
            false,
            false,
    };
    Boolean[] nao = new Boolean[]{
            false,
            false,
            false,
            false,
    };

    public Button btnCancelar;
    public Button btnSalva;

    ItemListView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_itens_cotar);

        btnCancelar = (Button) findViewById(R.id.btCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              SairSemSalvar();
            }
        });

        final ListView lista = (ListView) findViewById(R.id.listaProdutos);
        adapter = new ItemListView(this, sim,nao, produtos);
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
                Toast.makeText(getApplicationContext(), "Apertou e segur0ou " + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void SairSemSalvar(){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Anteção");
        alert.setMessage("Você iniciou uma cotação e não concluiu, deseja continuar mais tarde?");
        alert.setButton(Dialog.BUTTON_POSITIVE,"Continuar mais tarde",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Deixar na base para continuar depois", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setButton(Dialog.BUTTON_NEGATIVE,"Apagar cotação",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Apagar da base de dados", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setButton(Dialog.BUTTON_NEUTRAL,"Cancelar",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Não fazer nada", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

        alert.show();


    }
}
