package badgears.storecheck.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lucas on 23/09/2016.
 */
public class MCotacao implements Parcelable, Comparable<MCotacao> {
    protected String Nome;
    protected int IDCliente;
    protected Date DataCotacao;
    protected int ID;

    protected ArrayList<MCotacaoItem> itensCotacao ;
    protected int iTotItens;
    protected int iEmEstoque;
    protected int iSemEstoque;

    public ArrayList<MCotacaoItem> getItensCotacao() {
        return itensCotacao;
    }

    public void setItensCotacao(ArrayList<MCotacaoItem> itensCotacao) {
        this.itensCotacao = itensCotacao;
    }


    protected MCotacao(Parcel in) {
        Nome = in.readString();
        IDCliente = in.readInt();
        long tmpDate = in.readLong();
        this.DataCotacao = tmpDate == -1 ? null : new Date(tmpDate);
        ID = in.readInt();
        itensCotacao = in.readArrayList(MCotacaoItem.class.getClassLoader());
        iTotItens = in.readInt();
        iEmEstoque = in.readInt();
        iSemEstoque = in.readInt();
    }

    public static final Creator<MCotacao> CREATOR = new Creator<MCotacao>() {
        @Override
        public MCotacao createFromParcel(Parcel in) {
            return new MCotacao(in);
        }

        @Override
        public MCotacao[] newArray(int size) {
            return new MCotacao[size];
        }
    };

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(int IDCliente) {
        this.IDCliente = IDCliente;
    }

    public Date getData() {
        return DataCotacao;
    }

    public void setData(Date data) {
        DataCotacao = data;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getiTotItens() {
        return iTotItens;
    }

    public void setiTotItens(int iTotItens) {
        this.iTotItens = iTotItens;
    }

    public int getiEmEstoque() {
        return iEmEstoque;
    }

    public void setiEmEstoque(int iEmEstoque) {
        this.iEmEstoque = iEmEstoque;
    }

    public int getiSemEstoque() {
        return iSemEstoque;
    }

    public void setiSemEstoque(int iSemEstoque) {
        this.iSemEstoque = iSemEstoque;
    }

    public MCotacao() {

        this.DataCotacao = new Date();
        this.itensCotacao = new ArrayList<MCotacaoItem>();
    }


    @Override
    public int compareTo(MCotacao mCotacao) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Nome);
        parcel.writeInt(IDCliente);
        parcel.writeLong(DataCotacao != null ? DataCotacao.getTime() : -1);
        parcel.writeInt(ID);
        parcel.writeList(itensCotacao);
        parcel.writeInt(iTotItens);
        parcel.writeInt(iEmEstoque);
        parcel.writeInt(iSemEstoque);
    }
}

