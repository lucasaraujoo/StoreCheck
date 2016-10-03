package badgears.storecheck.Modelos;

/**
 * Created by lucas on 03/10/16.
 */

public class MCategoria {
    private int Id;
    private String Nome;

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


    public MCategoria(int id,String desc){
        this.Id = id;
        this.Nome = desc;

    }
}
