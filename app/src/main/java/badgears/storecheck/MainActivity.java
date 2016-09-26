package badgears.storecheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import badgears.storecheck.Dao.tskIniciarBD;
import badgears.storecheck.Modelos.MCotacao;
import badgears.storecheck.Controladores.ControladorCotacao;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_COTACAO = 8943;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_arc_menu_1);
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

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab_arc_menu_2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cria cotação biscoitos
                Toast.makeText(getApplicationContext(), "Biscoitos", Toast.LENGTH_SHORT).show();

            }
        });

        this.VerificaBD();
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
}
