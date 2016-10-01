package badgears.storecheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by lucas on 21/09/16.
 */

public class ItemListView extends BaseAdapter {

    // Declare Variables
    Context context;
    Boolean[] sim;
    Boolean[] nao;
    String[] Produtos;
    LayoutInflater inflater;

    public ItemListView(Context context, Boolean[] sim, Boolean[] nao, String[] Produtos ) {
        this.context = context;
        this.sim = sim;
        this.nao = nao;
        this.Produtos = Produtos;
    }

    @Override
    public int getCount() {
        return Produtos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        RadioButton rbSim;
        RadioButton rbNao;
        TextView txtProduto;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.produtos_listview, parent, false);

        // Locate the TextViews in listview_item.xml
        rbSim = (RadioButton) itemView.findViewById(R.id.rbSim);
        rbNao = (RadioButton) itemView.findViewById(R.id.rbNao);
        txtProduto = (TextView) itemView.findViewById(R.id.tvNomeProduto);

        // Capture position and set to the TextViews
       // rbSim.setChecked(sim[position]);
        //rbNao.setChecked(nao[position]);
        txtProduto.setText(Produtos[position]);

        return itemView;
    }
}
