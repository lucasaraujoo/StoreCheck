package badgears.storecheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import badgears.storecheck.Modelos.MCotacao;

/**
 * Created by Lucas on 26/09/2016.
 */
public class AdapterCotacao extends BaseAdapter {

    private ArrayList<MCotacao> cotacoes;
    private Context mContexto;
    private MCotacao cotacao = null;
    private LayoutInflater inflater;

    public AdapterCotacao(Context contexto, ArrayList<MCotacao> lista){
        this.mContexto  = contexto;
        this.cotacoes   = lista;
        this.inflater   = (LayoutInflater) mContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.cotacoes != null ? this.cotacoes.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return this.cotacoes != null ? this.cotacoes.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolderItem viewHolder = null;

        if(view==null){

            view = this.inflater.inflate(R.layout.lista_listview, null);

            viewHolder = new ViewHolderItem();
            viewHolder.titulo = (TextView) view.findViewById(R.id.tvEspabelecimento);
            viewHolder.data = (TextView) view.findViewById(R.id.tvData);
            viewHolder.qtdItens = (TextView) view.findViewById(R.id.tvTotalItens);
            viewHolder.qtdItensEmEst = (TextView) view.findViewById(R.id.tvQtdItensSim);
            viewHolder.qtdItensSemEst = (TextView) view.findViewById(R.id.tvQtdItensNao);

            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolderItem) view.getTag();

        }


        cotacao = cotacoes.get(i);
        if (cotacao != null) {
            viewHolder.titulo.setText(cotacao.getNome());
            viewHolder.data.setText("Data "+new SimpleDateFormat("dd/MM/yyyy").format( cotacao.getData()));
            viewHolder.qtdItens.setText("Total de itens verificados: "+cotacao.getiTotItens());
            viewHolder.qtdItensEmEst.setText("Itens em Estoque: "+cotacao.getiEmEstoque());
            viewHolder.qtdItensSemEst.setText("Itens sem Estoque: "+cotacao.getiSemEstoque());
        }

        return view;

    }


    static class ViewHolderItem  {
        public TextView titulo;
        public TextView data;
        public TextView qtdItens;
        public TextView qtdItensEmEst;
        public TextView qtdItensSemEst;
    }
}
