package badgears.storecheck.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lucas on 23/09/2016.
 */
public class MCotacao implements Parcelable, Comparable<MCotacao> {
    protected String Nome;
    protected int IDCliente;
    protected Date DataCotacao;
    protected int ID;
    //// TODO: 26/09/2016 criar classe dos itens da cotacao e criar objeto aqui

    protected MCotacao(Parcel in) {
        Nome = in.readString();
        IDCliente = in.readInt();
        long tmpDate = in.readLong();
        this.DataCotacao = tmpDate == -1 ? null : new Date(tmpDate);
        ID = in.readInt();
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

    public MCotacao() {
        this.DataCotacao = new Date();
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
    }
}

