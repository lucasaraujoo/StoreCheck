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
    private ArrayList<String> Pr1 = new ArrayList<String>();
    private ArrayList<String> Pr2 = new ArrayList<String>();

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

    public ArrayList<String> getPreco1(){
        return Pr1;
    }

    public void setPreco1(ArrayList<String> pr){
        this.Pr1 = pr;
    }

    public ArrayList<String> getPreco2(){
        return Pr2;
    }

    public void setPreco2(ArrayList<String> pr2){
        this.Pr2 = pr2;
    }

    public static long getObjectSize(Object o) {
        return mitensRelatorio.getObjectSize(o);
    }

    public MItensRelatorio(int iditem,int idmaster,ArrayList<String> SimNao,ArrayList<Integer> QuantidadesSim,ArrayList<Integer>QuantidadesNao,
                           ArrayList<String> pr1,ArrayList<String> pr2){
        this.idItem = iditem;
        this.idCotaMaster = idmaster;
        this.SimNao = SimNao;
        this.QuantidadesNao = QuantidadesNao;
        this.QuantidadesSim = QuantidadesSim;
        this.Pr1 = pr1;
        this.Pr2 = pr2;
    }
}
