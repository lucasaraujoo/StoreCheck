package badgears.storecheck;

/**
 * Created by lucas on 21/09/16.
 */

public class ItemListView {
    private String Estabelecimento;
    private int ItensEstoque;
    private int ItensSemEstoque;
    private int TotalItens;

    public ItemListView(){

    }

    public ItemListView(String Esta, int ComEstoq, int SemEstoq, int Total){
        this.Estabelecimento = Esta;
        this.ItensEstoque = ComEstoq;
        this.ItensSemEstoque = SemEstoq;
        this.TotalItens = Total;
    }

    public String getEstabalecimento(){
        return Estabelecimento;
    }
    public void setEstabelecimento(String Esta){
        this.Estabelecimento = Esta;
    }
    public int getItensEmEstoque(){
        return ItensEstoque;
    }
    public void setItensEmEstoque(int EmEstoq){
        this.ItensEstoque = EmEstoq;
    }
    public int getItensSemEstoque(){
        return ItensSemEstoque;
    }
    public void setItensSemEstoqe(){

    }
}
