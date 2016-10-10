package badgears.storecheck.Modelos;

/**
 * Created by lucas on 08/10/16.
 */

import java.util.ArrayList;

public class MItensRelatorio {
    private int idItem;
    private int idCotaMaster;
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

    public MItensRelatorio(int iditem,int idmaster,ArrayList<String> SimNao){
        this.idItem = iditem;
        this.idCotaMaster = idmaster;
        this.SimNao = SimNao;
    }
}
