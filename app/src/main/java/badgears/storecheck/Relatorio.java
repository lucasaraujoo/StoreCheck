package badgears.storecheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import jxl.write.WriteException;

public class Relatorio extends AppCompatActivity {
    public Button btnGerarRelatorio;
    public Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        btnGerarRelatorio = (Button) findViewById(R.id.btnGerarRelatorio);
        btnVoltar = (Button) findViewById(R.id.btnVoltarRelatorio);

        btnGerarRelatorio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Excel t = new Excel(Relatorio.this);
                try {
                    t.exportToExcel();
                    Toast.makeText(getApplicationContext(), "Relatorio gerado!", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Relatorio.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
