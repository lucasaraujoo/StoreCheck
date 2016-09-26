package badgears.storecheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    ItemListView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_itens_cotar);

        final ListView lista = (ListView) findViewById(R.id.listaProdutos);
        adapter = new ItemListView(this, sim,nao, produtos);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "presiono " + i, Toast.LENGTH_SHORT).show();
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "presiono LARGO " + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
