package badgears.storecheck;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AjudaActivity extends AppCompatActivity {
    
    public Button BtnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        setContentView(R.layout.activity_ajuda);
        BtnVoltar = (Button) findViewById(R.id.btnVoltar);
        BtnVoltar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
             finish();
            }
        });

        Toast.makeText(getApplicationContext(), "Em construção", Toast.LENGTH_SHORT).show();
    }
}
