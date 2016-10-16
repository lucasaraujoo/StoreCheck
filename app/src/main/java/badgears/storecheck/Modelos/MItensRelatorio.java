package badgears.storecheck.Modelos;

/**
 * Created by lucas on 08/10/16.
 */

import java.util.ArrayList;

public class MItensRelatorio {
    private static MItensRelatorio mitensRelatorio;
    private int idItem;
    private int idCotaMaster;
    private ArrayList<Integer> QuantidadesSim = new ArrayList<Integer>();
    private ArrayList<Integer> QuantidadesNao = new ArrayList<Integer>();
    private ArrayList<String> SimNao = new ArrayList<String>();

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int iditem) {
        this.idItem = iditem;
    }

    public int getIdMaster() {
        return idCotaMaster;
    }

    public void setIdMaster(int id) {
        this.idCotaMaster = id;
    }

    public ArrayList<String> getOpcoes(){
        return SimNao;
    }

    public void setOpcoes(ArrayList<String> opc){
        this.SimNao = opc;
    }

    public ArrayList<Integer> getQuantidadesSim(){
        return QuantidadesSim;
    }

    public void setQuantidadesSim(ArrayList<Integer> qtd){
        this.QuantidadesSim = qtd;
    }

    public ArrayList<Integer> getQuantidadesNao(){
        return QuantidadesNao;
    }

    public void setQuantidadesNao(ArrayList<Integer> qtd){
        this.QuantidadesNao = qtd;
    }

    public static long getObjectSize(Object o) {
        return mitensRelatorio.getObjectSize(o);
    }

    public MItensRelatorio(int iditem,int idmaster,ArrayList<String> SimNao,ArrayList<Integer> QuantidadesSim,ArrayList<Integer>QuantidadesNao){
        this.idItem = iditem;
        this.idCotaMaster = idmaster;
        this.SimNao = SimNao;
        this.QuantidadesNao = QuantidadesNao;
        this.QuantidadesSim = QuantidadesSim;
    }
}
