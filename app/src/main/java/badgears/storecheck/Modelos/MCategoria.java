package badgears.storecheck.Modelos;

import java.util.ArrayList;

/**
 * Created by lucas on 03/10/16.
 */

public class MCategoria {
    private int Id;
    private String Cliente;
    private String Nome;
    private ArrayList<MItensRelatorio> Itens;

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

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cli) {this.Cliente = cli;}

    public ArrayList<MItensRelatorio> getOpcoes(){
        return Itens;
    }

    public void setOpcoes(ArrayList<MItensRelatorio> opc){
        this.Itens = opc;
    }

    public MCategoria(int id,String desc,ArrayList<MItensRelatorio> itens,String Cliente){
        this.Id = id;
        this.Nome = desc;
        this.Itens = itens;
        this.Cliente = Cliente;
    }
}
