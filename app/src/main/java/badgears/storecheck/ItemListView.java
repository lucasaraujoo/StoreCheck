package badgears.storecheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import badgears.storecheck.Modelos.MCotacaoItem;

/**
 * Created by lucas on 21/09/16.
 */

public class ItemListView extends BaseAdapter {

    // Declare Variables
    Context mcontext;
    Boolean[] sim;
    Boolean[] nao;
    String[] Produtos;
    LayoutInflater inflater;
    ArrayList<MCotacaoItem> listaItensCotacao ;

    public ItemListView(Context contexto, ArrayList<MCotacaoItem> ItensCotacao ) {
        this.mcontext = contexto;
        this.listaItensCotacao = ItensCotacao;
    }

    @Override
    public int getCount() {
        return listaItensCotacao.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        // Declare Variables
        final RadioButton rbSim;
        final RadioButton rbNao;
        final RadioGroup rgCotar;
        TextView txtProduto;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View itemView = inflater.inflate(R.layout.produtos_listview, parent, false);

        // Locate the TextViews in listview_item.xml
        rbSim = (RadioButton) itemView.findViewById(R.id.rbSim);
        rbNao = (RadioButton) itemView.findViewById(R.id.rbNao);
        rgCotar = (RadioGroup) itemView.findViewById(R.id.radiogroup);
        txtProduto = (TextView) itemView.findViewById(R.id.tvNomeProduto);

        // Capture position and set to the TextViews
       // rbSim.setChecked(sim[position]);
        //rbNao.setChecked(nao[position]);
        txtProduto.setText(listaItensCotacao.get(position).getoProduto().getDescricao());
        rbSim.setChecked( listaItensCotacao.get(position).getbCotar() == false);
        rbNao.setChecked( listaItensCotacao.get(position).getbCotar() == false);

        rgCotar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                listaItensCotacao.get(position).setbCotar(i == rbSim.getId());
            }
        });
        // Step - 3 set the Tag position
        rbSim.setTag(position);
//Step - 4 Set the value of the item
        rbSim.setChecked(listaItensCotacao.get(position).getbCotar());

        return itemView;
    }
}
