package badgears.storecheck;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import badgears.storecheck.Modelos.MCotacaoItem;

/**
 * Created by Lucas on 03/10/2016.
 */
public class AdapterPrecos extends BaseAdapter {
    private Context mcontext;
    private ArrayList<MCotacaoItem> listaItens ;

    LayoutInflater inflater;

    public AdapterPrecos(Context contexto, ArrayList<MCotacaoItem> ItensCotacao ) {
        this.mcontext = contexto;
        this.listaItens = ItensCotacao;
    }

    @Override
    public int getCount() {
        return listaItens.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {



        inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.precos_listview, viewGroup, false);


        TextView tvItem = (TextView) itemView.findViewById(R.id.textView2);
        final EditText edPreco1 = (EditText) itemView.findViewById(R.id.etValorConcorrente);
        final EditText edPreco2 = (EditText)  itemView.findViewById(R.id.etValorMeu);

        tvItem.setText(listaItens.get(position).getoProduto().getDescricao());
        edPreco1.setText(Double.toString( listaItens.get( position).getPreco1()));
        edPreco2.setText(Double.toString( listaItens.get( position).getPreco2()));



        edPreco1.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edPreco1.getText().toString().isEmpty()) {
                    listaItens.get(position).setPreco1(Double.parseDouble(edPreco1.getText().toString()));
                }
            }
        });

        edPreco2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!edPreco2.getText().toString().isEmpty()) {
                    listaItens.get(position).setPreco2(Double.parseDouble(edPreco2.getText().toString()));
                }
            }
        });



        return itemView;
    }
}
