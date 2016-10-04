package badgears.storecheck.Modelos;

import java.util.ArrayList;

/**
 * Created by lucas on 03/10/16.
 */

public class MCategoria {
    private int Id;
    private String Nome;
    private ArrayList<String> SimNao = new ArrayList<String>();

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }
    public String getDescricao() {
        return Nome;
    }

    public void setDescricao(String desc) {
        this.Nome = desc;
    }

    public ArrayList<String> getOpcoes(){
        return SimNao;
    }

    public void setOpcoes(ArrayList<String> opc){
        this.SimNao = opc;
    }

    public MCategoria(int id,String desc,ArrayList<String> opc){
        this.Id = id;
        this.Nome = desc;
        this.SimNao = opc;

    }
}
